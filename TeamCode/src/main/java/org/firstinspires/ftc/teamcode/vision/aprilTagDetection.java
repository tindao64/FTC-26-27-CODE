package org.firstinspires.ftc.teamcode.vision;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;

import org.firstinspires.ftc.teamcode.teamspecific.HardwareManager;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;

import java.util.List;

public class aprilTagDetection {
    Vision vision = HardwareManager.INSTANCE.createVision(hardwareMap);
    public List<AprilTagDetection> getTags() { return vision.tagDetector.getDetections(); }
    public AprilTagDetection getTag(int id) {
        for (AprilTagDetection detection : getTags())
            if (detection.id == id)
                return detection;
        return null;
    }
    public void AprilTagOrientation(){}


}
