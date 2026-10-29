package org.firstinspires.ftc.teamcode.vision;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import org.firstinspires.ftc.robotcore.internal.camera.calibration.CameraCalibration;
import org.firstinspires.ftc.vision.VisionProcessor;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;
import org.opencv.core.Mat;

import java.util.ArrayList;

public class AprilTagAnnotater implements VisionProcessor {
    public AprilTagProcessor AprilTag;
    public CameraCalibration calib;
    public float decimation = 2; // Higher => Better Performance & Worse Quality
    private final Paint BluePaint = new Paint();
    private final Paint RedPaint = new Paint();

    @Override
    public void init(int width, int height, CameraCalibration calibration) {
        calib = calibration;
        AprilTag = new AprilTagProcessor.Builder()
                .setLensIntrinsics(calibration.focalLengthX, calibration.focalLengthY, calibration.principalPointX,calibration.principalPointY)
                .build();
        AprilTag.init(width,height,calibration);
        AprilTag.setDecimation(decimation);
BluePaint.setColor(Color.BLUE);
RedPaint.setColor(Color.RED);

    }


    @Override
    public Object processFrame(Mat frame, long captureTimeNanos) {
        if (frame == null) {
            return null;
        }

        double nanostofps = 1E-9; // 1/10^9 (nanoseconds in a second)
        AprilTag.processFrame(frame,captureTimeNanos);
        return new FrameInfo(AprilTag.getDetections(),(int)(captureTimeNanos * nanostofps)/* Divide to get FPS*/);
    }

    @Override
    public void onDrawFrame(Canvas canvas, int onscreenWidth, int onscreenHeight, float scaleBmpPxToCanvasPx, float scaleCanvasDensity, Object userContext) {
    /*    if (userContext == null) {
            return;
        }

        FrameInfo info = (FrameInfo)userContext;
        canvas.drawText(String.valueOf(info.fps),0,0,new Paint());
        for(AprilTagDetection detection : info.detections)
        {
        if(detection instanceof AprilTagClusterDetection)
        {

            canvas.drawCircle((float)((AprilTagClusterDetection) detection).rawPose.x,(float)detection.rawPose.y,50,RedPaint);
        }

        }
        //Draw line for hive tip state
        List<String> names = info.detections.stream().map(x -> x instanceof AprilTagClusterDetection ? ((AprilTagClusterDetection)x).metadata.name : "").collect(Collectors.toList());
        //Red Alliance
        if(names.contains("RED SCORING") && names.contains("RED AUDIENCE"))
        {
            int index_a = names.indexOf("RED SCORING");
            int index_b = names.indexOf("RED AUDIENCE");
            canvas.drawLine((float)info.detections.get(index_a).rawPose.x,(float)info.detections.get(index_a).rawPose.y,(float)info.detections.get(index_b).rawPose.x,(float)info.detections.get(index_b).rawPose.y,RedPaint);
        }
        //Blue Alliance
        if(names.contains("BLUE SCORING") && names.contains("BLUE AUDIENCE"))
        {

            int index_a = names.indexOf("BLUE SCORING");
            int index_b = names.indexOf("BLUE AUDIENCE");
            canvas.drawLine((float)info.detections.get(index_a).rawPose.x,(float)info.detections.get(index_a).rawPose.y,(float)info.detections.get(index_b).rawPose.x,(float)info.detections.get(index_b).rawPose.y,BluePaint);
        }*/
    }

    public static class FrameInfo{
        public ArrayList<AprilTagDetection> detections;
        public int fps;
        public FrameInfo(ArrayList<AprilTagDetection> detections, int fps)
        {
            this.detections = detections;
            this.fps = fps;
        }
    }
}
