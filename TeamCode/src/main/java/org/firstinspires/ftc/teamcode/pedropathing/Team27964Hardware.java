package org.firstinspires.ftc.teamcode.pedropathing;

import com.pedropathing.control.PIDFCoefficients;
import com.pedropathing.follower.Follower;
import com.pedropathing.follower.FollowerConstants;
import com.pedropathing.ftc.FollowerBuilder;
import com.pedropathing.ftc.drivetrains.MecanumConstants;
import com.pedropathing.ftc.localization.Encoder;
import com.pedropathing.ftc.localization.constants.TwoWheelConstants;
import com.pedropathing.paths.PathConstraints;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

class Team27964Hardware extends Hardware {
    static {
        HardwareManager.INSTANCE.registerHardware("Team 27964 Bot", Team27964Hardware::new);
    }
    public FollowerConstants followerConstants = new FollowerConstants()
            .mass(9.5)
            .headingPIDFCoefficients(new PIDFCoefficients(3, 0.1, 0.2, 0.05))
            .forwardZeroPowerAcceleration(-37.4507960114)
            .lateralZeroPowerAcceleration(-43.0960090531)
            .translationalPIDFCoefficients(new PIDFCoefficients(0.1, 0.0001, 0.005, 0.02));

    public PathConstraints pathConstraints = new PathConstraints(0.99, 100, 1, 1);

    public MecanumConstants driveConstants = new MecanumConstants()
            .maxPower(1)
            .rightFrontMotorName("motorFR")
            .rightRearMotorName("motorBR")
            .leftFrontMotorName("motorFL")
            .leftRearMotorName("motorBL")
            .leftFrontMotorDirection(DcMotorSimple.Direction.REVERSE)
            .leftRearMotorDirection(DcMotorSimple.Direction.REVERSE)
            .xVelocity(32.2327889433)
            .yVelocity(23.2810426079);

    public TwoWheelConstants localizerConstants = new TwoWheelConstants()
            .forwardEncoder_HardwareMapName("forwardEncoder")
            .strafeEncoder_HardwareMapName("strafeEncoder")
            .IMU_HardwareMapName("imu")
            .IMU_Orientation(
                    new RevHubOrientationOnRobot(
                            RevHubOrientationOnRobot.LogoFacingDirection.UP,
                            RevHubOrientationOnRobot.UsbFacingDirection.RIGHT
                    )
            )
            .forwardEncoderDirection(Encoder.REVERSE)
            .strafeEncoderDirection(Encoder.REVERSE)
            .forwardPodY(-2.962232987691003)
            .strafePodX(-9.362612399582233)
            .forwardTicksToInches(0.00114746364)
            .strafeTicksToInches(0.00114746364);

    @Override
    public Follower createFollower(HardwareMap hardwareMap) {
        return new FollowerBuilder(followerConstants, hardwareMap)
                .pathConstraints(pathConstraints)
                .mecanumDrivetrain(driveConstants)
                .twoWheelLocalizer(localizerConstants)
                .build();
    }
}
