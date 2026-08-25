package org.firstinspires.ftc.teamcode.pedropathing;

import com.bylazar.configurables.annotations.Configurable;
import com.pedropathing.ftc.drivetrains.Mecanum;
import com.pedropathing.ftc.drivetrains.MecanumConstants;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;
import com.qualcomm.robotcore.util.ElapsedTime;

public class EncoderMecanumDrivetrain extends Mecanum {
    public static class Constants extends MecanumConstants {
        public DcMotor.RunMode runMode = null;
        public PIDFCoefficients motorPIDFCoefficients = null;
        public double motorPowerScaling = 1.0;
        public double maxAcceleration = 1000000.0; // power per second

        @Override
        public void defaults() {
            super.defaults();
            runMode = DcMotor.RunMode.RUN_WITHOUT_ENCODER;
            motorPIDFCoefficients = new PIDFCoefficients(0, 0, 0, 0);
            motorPowerScaling = 1.0;
            maxAcceleration = 1000000.0;
        }

        public Constants setRunMode(DcMotor.RunMode runMode) {
            this.runMode = runMode;
            return this;
        }

        public Constants setMotorPIDFCoefficients(PIDFCoefficients motorPIDFCoefficients) {
            this.motorPIDFCoefficients = motorPIDFCoefficients;
            return this;
        }

        public Constants setMotorPowerScaling(double motorPowerScaling) {
            this.motorPowerScaling = motorPowerScaling;
            return this;
        }

        public Constants setMaxAcceleration(double maxAcceleration) {
            this.maxAcceleration = maxAcceleration;
            return this;
        }
    }

    public Constants constants;

    private final ElapsedTime deltaTime = new ElapsedTime();

    private double[] prevMotorDrive = null;

    public EncoderMecanumDrivetrain(HardwareMap hardwareMap, Constants constants) {
        super(hardwareMap, constants);
        this.constants = constants;

        for (DcMotorEx motor : getMotors()) {
            motor.setPIDFCoefficients(constants.runMode, constants.motorPIDFCoefficients);
            motor.setMode(constants.runMode);
        }

        deltaTime.reset();
    }

    @Override
    public void updateConstants() {
        super.updateConstants();
        for (DcMotorEx motor : getMotors()) {
            motor.setPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER, constants.motorPIDFCoefficients);
            motor.setMode(constants.runMode);
        }
    }

    @Override
    public void runDrive(double[] drivePowers) {
        double deltaTimeNow = deltaTime.seconds();
        deltaTime.reset();

        double maxAllowedPowerChange = constants.maxAcceleration * deltaTimeNow;

        if (prevMotorDrive == null) {
            prevMotorDrive = drivePowers;
        }

        drivePowers = drivePowers.clone();
        for (int i = 0; i < drivePowers.length; ++i) {
            drivePowers[i] *= constants.motorPowerScaling;

            // Clamp power delta, but allow stopping/small braking
            // This is to allow for faster stopping and predictive braking
            if (Math.abs(drivePowers[i]) > 0.2
             && Math.abs(drivePowers[i] - prevMotorDrive[i]) > maxAllowedPowerChange) {
                drivePowers[i] = prevMotorDrive[i] + Math.signum(drivePowers[i] - prevMotorDrive[i]) * maxAllowedPowerChange;
            }
        }

        prevMotorDrive = drivePowers;
        super.runDrive(drivePowers);
    }
}
