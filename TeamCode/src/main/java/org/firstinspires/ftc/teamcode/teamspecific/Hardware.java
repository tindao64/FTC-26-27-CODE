package org.firstinspires.ftc.teamcode.teamspecific;

import com.pedropathing.follower.Follower;
import com.qualcomm.robotcore.hardware.HardwareMap;
import org.firstinspires.ftc.vision.VisionPortal;

public abstract class Hardware {
    // Creates a follower for this robot
    public abstract Follower createFollower(HardwareMap hardwareMap);

    // Return a vision portal builder with the webcam set up
    public abstract VisionPortal.Builder createVisionPortalBuilder(HardwareMap hardwareMap);
}
