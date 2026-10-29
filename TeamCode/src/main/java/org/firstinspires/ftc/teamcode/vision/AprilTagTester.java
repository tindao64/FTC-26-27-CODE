package org.firstinspires.ftc.teamcode.vision;


import com.bylazar.camerastream.PanelsCameraStream;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.hardware.camera.CameraName;
import org.firstinspires.ftc.vision.VisionPortal;

@TeleOp
public class AprilTagTester extends OpMode {
    @Override
    public void init() {
        AprilTagAnnotater annotater = new AprilTagAnnotater();
        VisionPortal portal = VisionPortal.easyCreateWithDefaults(hardwareMap.get(CameraName.class,"Webcam 1"), annotater);
        PanelsCameraStream.INSTANCE.startStream(portal, 30);
    }

    @Override
    public void loop() {

    }
}
