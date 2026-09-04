package org.firstinspires.ftc.teamcode.teamspecific;

import android.graphics.Color;
import android.util.Size;
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
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.vision.Vision;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.opencv.ColorBlobLocatorProcessor;
import org.firstinspires.ftc.vision.opencv.ColorRange;
import org.firstinspires.ftc.vision.opencv.ColorSpace;
import org.opencv.core.Scalar;

class Team27964Hardware extends Hardware {
    static {
        HardwareManager.INSTANCE.registerHardware("Team 27964 Bot", Team27964Hardware::new);
    }
    public FollowerConstants followerConstants = new FollowerConstants()
            .mass(9.5)
            .headingPIDFCoefficients(new PIDFCoefficients(1.3, 0.01, 0.1, 0.03))
            .forwardZeroPowerAcceleration(-35.8469744119)
            .lateralZeroPowerAcceleration(-36.2191936259)
            .translationalPIDFCoefficients(new PIDFCoefficients(0.1, 0.0001, 0.01, 0.01));

    public PathConstraints pathConstraints = new PathConstraints(0.99, 200, 0.75, 1);

    public MecanumConstants driveConstants = new MecanumConstants()
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
                .mecanumDrivetrain(driveConstants)
                .twoWheelLocalizer(localizerConstants)
                .build();
    }

    public static final Size CAMERA_SIZE = new Size(320, 240);
    private VisionPortal.Builder createVisionPortalBuilder(HardwareMap hardwareMap) {
        return new VisionPortal.Builder()
                .setCamera(hardwareMap.get(WebcamName.class, "Webcam 1"))
                .setCameraResolution(CAMERA_SIZE)
                .setStreamFormat(VisionPortal.StreamFormat.MJPEG);
    }

    private ColorBlobLocatorProcessor createBlobProcessor() {
        ColorBlobLocatorProcessor colorBlobProcessor = new ColorBlobLocatorProcessor.Builder()
                .setTargetColorRange(
                        new ColorRange(
                                ColorSpace.HSV,
                                new Scalar(25, 100, 100),   // HSV Low
                                new Scalar(35, 255, 255)    // HSV High
                        )
                )
                .setContourMode(ColorBlobLocatorProcessor.ContourMode.EXTERNAL_ONLY)
                .setDrawContours(true)
                .setBoxFitColor(0)
                .setCircleFitColor(Color.rgb(0, 255, 0))
                .setBlurSize(5)
                .setDilateSize(15)
                .setErodeSize(15)
                .setMorphOperationType(ColorBlobLocatorProcessor.MorphOperationType.CLOSING)
                .build();

        // TODO!!: Lazy application of blob filters only on request. Benchmark?

        // Removes small or enormous blobs
        colorBlobProcessor.addFilter(new ColorBlobLocatorProcessor.BlobFilter(
                ColorBlobLocatorProcessor.BlobCriteria.BY_CONTOUR_AREA,
                50,
                20000
        ));

        // Removes non-circular blobs
        colorBlobProcessor.addFilter(new ColorBlobLocatorProcessor.BlobFilter(
                ColorBlobLocatorProcessor.BlobCriteria.BY_CIRCULARITY,
                0.6,
                1.0
        ));

        return colorBlobProcessor;
    }

    @Override
    public Vision createVision(HardwareMap hardwareMap) {
        ColorBlobLocatorProcessor colorBlobProcessor = createBlobProcessor();
        VisionPortal visionPortal = createVisionPortalBuilder(hardwareMap)
                .addProcessors(colorBlobProcessor)
                .build();

        return new Vision(visionPortal, colorBlobProcessor, CAMERA_SIZE);
    }
}
