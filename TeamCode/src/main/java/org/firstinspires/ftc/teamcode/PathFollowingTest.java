package org.firstinspires.ftc.teamcode;

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.BezierCurve;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.PathChain;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import org.firstinspires.ftc.teamcode.pedropathing.FollowerLinearOpMode;
import org.firstinspires.ftc.teamcode.teamspecific.HardwareManager;

import java.lang.Math;

@Autonomous(name = "Path Following Test")
public class PathFollowingTest extends FollowerLinearOpMode {
    @Override
    public void runOpMode() {
        //Must call for paths
        initFollowerOpMode();

        Pose startingPose = new Pose(36, 60, Math.toRadians(90));
        follower.setStartingPose(startingPose);

        PathChain pathChain = follower.pathBuilder()
                .addPath(
                        new BezierCurve(
                                new Pose(36.000, 60.000),
                                new Pose(29.422, 73.520),
                                new Pose(5.787, 69.561),
                                new Pose(21.502, 106.992),
                                new Pose(36.000, 84.000)
                        )
                )
                .setTangentHeadingInterpolation()
                .addPath(
                        new BezierCurve(
                                new Pose(36.000, 84.000),
                                new Pose(49.074, 107.339),
                                new Pose(68.439, 68.919),
                                new Pose(43.711, 73.775),
                                new Pose(36.000, 60.000)
                        )
                )
                .setTangentHeadingInterpolation()
                .setReversed()
                .build();

        telemetry.addLine("Initialized, waiting for start.");
        telemetry.update();

        waitForStart();

        follower.followPath(pathChain);

        while (follower.isBusy() && opModeIsActive()) {
            follower.update();
        }
    }
}
