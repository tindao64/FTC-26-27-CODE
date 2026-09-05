package org.firstinspires.ftc.teamcode.teamspecific;

import com.pedropathing.follower.Follower;
import com.qualcomm.robotcore.hardware.HardwareMap;
import org.firstinspires.ftc.teamcode.vision.Vision;

public abstract class Hardware {
    // Creates a follower for this robot
    public abstract Follower createFollower(HardwareMap hardwareMap);

    // Returns a fully tuned Vision containing all needed processors
    public abstract Vision createVision(HardwareMap hardwareMap);
}
