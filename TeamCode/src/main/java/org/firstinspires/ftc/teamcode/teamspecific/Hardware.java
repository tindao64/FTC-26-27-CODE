package org.firstinspires.ftc.teamcode.teamspecific;


import static org.firstinspires.ftc.teamcode.Utilities.sub;

import com.pedropathing.follower.Follower;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.navigation.Pose3D;
import org.firstinspires.ftc.teamcode.vision.Vision;

public abstract class Hardware {
    // Creates a follower for this robot
    public abstract Follower createFollower(HardwareMap hardwareMap);

    // Returns a fully tuned Vision containing all needed processors
    public abstract Vision createVision(HardwareMap hardwareMap);

    //Offset of robot -> camera
    public abstract Pose3D getCameraMountingPose();
    //Normalize a pose relative to the camera's position
    public Pose3D normalizeCameraPose(Pose3D p) {return sub(p,getCameraMountingPose());}
}
