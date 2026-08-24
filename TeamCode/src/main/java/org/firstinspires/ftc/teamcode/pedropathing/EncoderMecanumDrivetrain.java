package org.firstinspires.ftc.teamcode.pedropathing;

import com.bylazar.configurables.annotations.Configurable;
import com.pedropathing.ftc.drivetrains.Mecanum;
import com.pedropathing.ftc.drivetrains.MecanumConstants;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;

public class EncoderMecanumDrivetrain extends Mecanum {
    public static class Constants extends MecanumConstants {
        public DcMotor.RunMode runMode = null;
        public PIDFCoefficients motorPIDFCoefficients = null;
        public double motorPowerScaling = 1.0;

        @Override
        public void defaults() {
            super.defaults();
            runMode = DcMotor.RunMode.RUN_WITHOUT_ENCODER;
            motorPIDFCoefficients = new PIDFCoefficients(0, 0, 0, 0);
            motorPowerScaling = 1.0;
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
    }

    public Constants constants;

    public EncoderMecanumDrivetrain(HardwareMap hardwareMap, Constants constants) {
        super(hardwareMap, constants);
        this.constants = constants;

        for (DcMotorEx motor : getMotors()) {
            motor.setPIDFCoefficients(constants.runMode, constants.motorPIDFCoefficients);
            motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        }
    }

    @Override
    public void updateConstants() {
        super.updateConstants();
        for (DcMotorEx motor : getMotors()) {
            motor.setPIDFCoefficients(constants.runMode, constants.motorPIDFCoefficients);
            motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        }
    }

    @Override
    public void runDrive(double[] drivePowers) {
        drivePowers = drivePowers.clone();
        for (int i = 0; i < drivePowers.length; ++i) {
            drivePowers[i] *= constants.motorPowerScaling;
        }
        super.runDrive(drivePowers);
    }
}
