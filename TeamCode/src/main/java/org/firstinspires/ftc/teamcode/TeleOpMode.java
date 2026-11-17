package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.firstinspires.ftc.teamcode.pedropathing.FollowerLinearOpMode;

@TeleOp()
public class TeleOpMode extends FollowerLinearOpMode {

    @Override
    public void runOpMode() {
        initFollowerOpMode();
        follower.startTeleopDrive();

        waitForStart();

        while (opModeIsActive()) {
            follower.setTeleOpDrive(-gamepad1.left_stick_y, -gamepad1.left_stick_x, -gamepad1.right_stick_x);

            updateFollower();
        }
    }
}
