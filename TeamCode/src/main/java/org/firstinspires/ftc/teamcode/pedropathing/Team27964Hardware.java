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
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

class Team27964Hardware extends Hardware {
    static {
        HardwareManager.INSTANCE.registerHardware("Team 27964 Bot", Team27964Hardware::new);
    }
    public FollowerConstants followerConstants = new FollowerConstants()
            .mass(9.5)
            .headingPIDFCoefficients(new PIDFCoefficients(1.3, 0.05, 0.1, 0.0))
            .forwardZeroPowerAcceleration(-35.8469744119)
            .lateralZeroPowerAcceleration(-36.2191936259)
            .translationalPIDFCoefficients(new PIDFCoefficients(0.1, 0.0001, 0.01, 0.0));

    public PathConstraints pathConstraints = new PathConstraints(0.99, 100, 0.75, 1);

    public EncoderMecanumDrivetrain.Constants driveConstants = (EncoderMecanumDrivetrain.Constants) new EncoderMecanumDrivetrain.Constants()
            .setRunMode(DcMotor.RunMode.RUN_USING_ENCODER)
            .setMotorPIDFCoefficients(new com.qualcomm.robotcore.hardware.PIDFCoefficients(6.0, 6.0, 2.0, 0.0))
            .setMotorPowerScaling(1.0)
            .setMaxAcceleration(1.0)
            .maxPower(1.0)
            .rightFrontMotorName("motorFR")
            .rightRearMotorName("motorBR")
            .leftFrontMotorName("motorFL")
            .leftRearMotorName("motorBL")
            .leftFrontMotorDirection(DcMotorSimple.Direction.REVERSE)
            .leftRearMotorDirection(DcMotorSimple.Direction.REVERSE)
            .xVelocity(30.557338823259926)
            .yVelocity(25.005798396918625);

    public TwoWheelConstants localizerConstants = new TwoWheelConstants()
            .forwardEncoder_HardwareMapName("motorBL")
            .strafeEncoder_HardwareMapName("motorFL")
            .IMU_HardwareMapName("imu")
            .IMU_Orientation(
                    new RevHubOrientationOnRobot(
                            RevHubOrientationOnRobot.LogoFacingDirection.UP,
                            RevHubOrientationOnRobot.UsbFacingDirection.RIGHT
                    )
            )
            .forwardEncoderDirection(Encoder.FORWARD)
            .strafeEncoderDirection(Encoder.FORWARD)
            .forwardPodY(2.713749533404666)
            .strafePodX(-11.011891248590807)
            .forwardTicksToInches(0.00112711963)
            .strafeTicksToInches(0.00112711963);

    @Override
    public Follower createFollower(HardwareMap hardwareMap) {
        return new FollowerBuilder(followerConstants, hardwareMap)
                .pathConstraints(pathConstraints)
                .setDrivetrain(new EncoderMecanumDrivetrain(hardwareMap, driveConstants))
                .twoWheelLocalizer(localizerConstants)
                .build();
    }
}
