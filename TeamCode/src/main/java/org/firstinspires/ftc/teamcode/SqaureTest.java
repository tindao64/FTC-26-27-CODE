package org.firstinspires.ftc.teamcode;

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.PathChain;

import org.firstinspires.ftc.teamcode.pedropathing.FollowerLinearOpMode;
public class SqaureTest extends FollowerLinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
            initFollowerOpMode();
        PathChain MainChain;
            MainChain = follower.pathBuilder()
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
        follower.followPath(pathChain);

        while (follower.isBusy() && opModeIsActive()) {
            follower.update();
        }
}
}
