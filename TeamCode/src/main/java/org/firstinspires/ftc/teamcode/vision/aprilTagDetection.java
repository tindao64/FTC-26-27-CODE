package org.firstinspires.ftc.teamcode.vision;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;
import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.telemetry;

import org.firstinspires.ftc.teamcode.teamspecific.HardwareManager;
import org.firstinspires.ftc.vision.apriltag.AprilTagClusterDetection;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
import org.firstinspires.ftc.vision.apriltag.AprilTagSingleDetection;

import java.util.List;

public class aprilTagDetection {
    Vision vision = HardwareManager.INSTANCE.createVision(hardwareMap);
    boolean TagIsUp;

    public List<AprilTagDetection> getTags() { return vision.tagDetector.getDetections(); }
    public AprilTagDetection getTag(int id) {
        for (AprilTagDetection detection : currentDetections) {
            if (detection instanceof AprilTagSingleDetection) {
                AprilTagSingleDetection singleDet = (AprilTagSingleDetection) detection;
                if (singleDet.metadata != null) {
                    // Notice this telemetry is using singleDet to get metadata and id.
                    telemetry.addLine(String.format("\n==== (ID %d) %s", singleDet.id, singleDet.metadata.name));
                    // Notice we can use the raw detection variable or our new singleDet
                    // variable to get Pose information because the ftcPose is
                    // inherited from the superclass and so it lives in both places.
                    telemetry.addLine(String.format("XYZ %6.1f %6.1f %6.1f  (inch)", detection.ftcPose.x, detection.ftcPose.y, detection.ftcPose.z));
                    telemetry.addLine(String.format("PRY %6.1f %6.1f %6.1f  (deg)", detection.ftcPose.pitch, detection.ftcPose.roll, detection.ftcPose.yaw));
                    telemetry.addLine(String.format("RBE %6.1f %6.1f %6.1f  (inch, deg, deg)", detection.ftcPose.range, detection.ftcPose.bearing, detection.ftcPose.elevation));
                    if(detection.ftcPose.roll>=-90&&90<=detection.ftcPose.roll){TagIsUp = true;}
                    else{TagIsUp = false;}
                }
            } else {
                AprilTagClusterDetection clusterDet = (AprilTagClusterDetection) detection;
                if (clusterDet.metadata != null) {
                    telemetry.addLine(String.format("\n==== (ID %d) %s", clusterDet.percentClusterFound, clusterDet.metadata.name));
                    telemetry.addLine(String.format("XYZ %6.1f %6.1f %6.1f  (inch)", detection.ftcPose.x, detection.ftcPose.y, detection.ftcPose.z));
                    telemetry.addLine(String.format("PRY %6.1f %6.1f %6.1f  (deg)", detection.ftcPose.pitch, detection.ftcPose.roll, detection.ftcPose.yaw));
                    telemetry.addLine(String.format("RBE %6.1f %6.1f %6.1f  (inch, deg, deg)", detection.ftcPose.range, detection.ftcPose.bearing, detection.ftcPose.elevation));
                    if(detection.ftcPose.roll>=-90&&90<=detection.ftcPose.roll){TagIsUp = true;}
                    else{TagIsUp = false;}
                }
            }
        }
        return null;
    }
    public boolean AprilTagUp(){
        return TagIsUp;
        }
    }
