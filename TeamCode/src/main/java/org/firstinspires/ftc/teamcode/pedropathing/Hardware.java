package org.firstinspires.ftc.teamcode.pedropathing;

import com.pedropathing.follower.Follower;
import com.qualcomm.robotcore.hardware.HardwareMap;

public abstract class Hardware {
    // Creates a follower for this robot
    public abstract Follower createFollower(HardwareMap hardwareMap);
}
