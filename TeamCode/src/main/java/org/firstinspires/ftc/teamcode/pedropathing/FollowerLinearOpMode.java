package org.firstinspires.ftc.teamcode.pedropathing;

import com.bylazar.telemetry.JoinedTelemetry;
import com.bylazar.telemetry.PanelsTelemetry;
import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.Pose;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import org.firstinspires.ftc.teamcode.teamspecific.HardwareManager;

public abstract class FollowerLinearOpMode extends LinearOpMode {
    public Follower follower;

    /**
     * MUST call this in init()
     */
    protected final void initFollowerOpMode() {
         // Setup telemetry
         telemetry = new JoinedTelemetry(telemetry, PanelsTelemetry.INSTANCE.getFtcTelemetry());

         follower = HardwareManager.INSTANCE.createFollower(hardwareMap);

         // reset position hack
         follower.setStartingPose(new Pose());
         follower.update();
         follower.setPose(new Pose());
    }
}

