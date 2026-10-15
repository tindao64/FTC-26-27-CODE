package org.firstinspires.ftc.teamcode;

import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.PathChain;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import org.firstinspires.ftc.teamcode.pedropathing.FollowerLinearOpMode;
@Autonomous(name = "Square Path Test")
public class SquareTest extends FollowerLinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        initFollowerOpMode();
        follower.setStartingPose(new Pose(72, 72, Math.toRadians(90)));

        PathChain MainChain = follower.pathBuilder()
                .addPath(
                        new BezierLine(
                                new Pose(72.000, 72.000),
                                new Pose(108.000, 72.000)
                        )
                )
                .setTangentHeadingInterpolation()
                .addPath(
                        new BezierLine(
                                new Pose(108.000, 72.000),
                                new Pose(108.000, 108.000)
                        )
                )
                .setTangentHeadingInterpolation()
                .addPath(
                        new BezierLine(
                                new Pose(108.000, 108.000),
                                new Pose(72.000, 108.000)
                        )
                )
                .setTangentHeadingInterpolation()
                .addPath(
                        new BezierLine(
                                new Pose(72.000, 108.000),
                                new Pose(72.000, 72.000)
                        )
                )
                .setTangentHeadingInterpolation()
                .build();

        waitForStart();
        follower.followPath(MainChain);

        while (follower.isBusy() && opModeIsActive()) {
            updateFollower();
        }
    }
}
