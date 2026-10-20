package org.firstinspires.ftc.teamcode.vision;

import android.graphics.Canvas;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.robotcore.internal.camera.calibration.CameraCalibration;
import org.firstinspires.ftc.vision.VisionProcessor;
import org.firstinspires.ftc.vision.apriltag.AprilTagClusterMetadata;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
import org.firstinspires.ftc.vision.apriltag.AprilTagLibrary;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;
import org.opencv.core.Mat;

import java.util.ArrayList;

public class AprilTagAnnotater implements VisionProcessor {
    public AprilTagProcessor AprilTag;

    public float decimation = 2; // Higher => Better Performance & Worse Quality
    @Override
    public void init(int width, int height, CameraCalibration calibration) {
        AprilTag = new AprilTagProcessor.Builder()
                .setLensIntrinsics(calibration.focalLengthX, calibration.focalLengthY, calibration.principalPointX,calibration.principalPointY)
                .build();
        AprilTag.setDecimation(decimation);
    }

    @Override
    public Object processFrame(Mat frame, long captureTimeNanos) {
        double nanostofps = 1E-9;
        return new FrameInfo((ArrayList<AprilTagDetection>)AprilTag.processFrame(frame,captureTimeNanos),(int)(captureTimeNanos * nanostofps)/* Divide to get FPS*/);
    }

    @Override
    public void onDrawFrame(Canvas canvas, int onscreenWidth, int onscreenHeight, float scaleBmpPxToCanvasPx, float scaleCanvasDensity, Object userContext) {

    }

    public class FrameInfo{
        public ArrayList<AprilTagDetection> detections;
        public int fps;
        public FrameInfo(ArrayList<AprilTagDetection> detections, int fps)
        {
            this.detections = detections;
            this.fps = fps;
        }
    }
}
