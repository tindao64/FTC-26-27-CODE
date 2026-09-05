package org.firstinspires.ftc.teamcode.vision;

import com.bylazar.camerastream.PanelsCameraStream;
import com.bylazar.telemetry.JoinedTelemetry;
import com.bylazar.telemetry.PanelsTelemetry;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.firstinspires.ftc.teamcode.teamspecific.HardwareManager;
import org.firstinspires.ftc.vision.opencv.Circle;
import org.firstinspires.ftc.vision.opencv.ColorBlobLocatorProcessor;
import org.opencv.core.Point;

import java.util.List;

@TeleOp(name = "Vision Tuning", group = "Tuning")
public class VisionTuning extends LinearOpMode {
    @Override
    public void runOpMode() {
        // Set up combined telemetry, to also show on panels
        telemetry = new JoinedTelemetry(PanelsTelemetry.INSTANCE.getFtcTelemetry(), telemetry);

        // This is the Vision that contains all the computer vision things
        Vision vision = HardwareManager.INSTANCE.createVision(hardwareMap);

        // Set up the Panels camera feed
        PanelsCameraStream.INSTANCE.startStream(vision.visionPortal, 5);

        // Loop while active
        while (opModeInInit() || opModeIsActive()) {
            // Get the blobs from the processor
            List<ColorBlobLocatorProcessor.Blob> blobList = vision.colorBlobProcessor.getBlobs();

            // Print the blobs on telemetry
            telemetry.clearAll();
            for (ColorBlobLocatorProcessor.Blob blob : blobList) {
                Circle blobCircle = blob.getCircle();

                // Normalize the center coordinates to a range of 0.0-1.0,
                // with (0.0, 0.0) on the top left, and x increasing right and y increasing down
                Point center = vision.normalizeCoordinates(blob.getCircle().getCenter());

                telemetry.addLine("Blob: centerX=" + center.x + "\tcenterY=" + center.y + "\tradius=" + blobCircle.getRadius());
            }

            telemetry.update();
            sleep(250);
        }

        // clean up
        vision.visionPortal.resumeLiveView();
        PanelsCameraStream.INSTANCE.stopStream();
    }
}
