package org.firstinspires.ftc.teamcode;

import com.bylazar.configurables.annotations.Configurable;
import com.bylazar.configurables.annotations.IgnoreConfigurable;
import com.bylazar.telemetry.PanelsTelemetry;
import com.bylazar.telemetry.TelemetryManager;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

@Configurable
@TeleOp(name = "Motor PIDF Tuning", group = "Tuning")
public class MotorPIDFTuning extends OpMode {
    static final String MOTOR = "motorFR";
    TelemetryManager telemetryM = PanelsTelemetry.INSTANCE.getTelemetry();
    DcMotorEx motor;

    public static PIDFCoefficients coefficients = new PIDFCoefficients(0.0, 0.0, 0.0, 0.0);

    public static double targetVelocity = 0.0;

    @Override
    public void init() {
        motor =  hardwareMap.get(DcMotorEx.class, MOTOR);

        motor.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.FLOAT);
        motor.setPIDFCoefficients(DcMotorEx.RunMode.RUN_USING_ENCODER, coefficients);
        motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    @Override
    public void loop() {
        motor.setPIDFCoefficients(DcMotorEx.RunMode.RUN_USING_ENCODER, coefficients);

        if (gamepad1.dpadUpWasPressed()) {
            targetVelocity += 30;
        }

        if (gamepad1.dpadDownWasPressed()) {
            targetVelocity -= 30;
        }

        motor.setVelocity(targetVelocity * 6, AngleUnit.DEGREES);

        telemetryM.addData("Target Velocity", targetVelocity);

        double currentVelocity = motor.getVelocity(AngleUnit.DEGREES) / 6;
        telemetryM.addData("Current Velocity", currentVelocity);
        telemetryM.addData("Error", targetVelocity - currentVelocity);

        telemetryM.update();
        telemetry.update();
    }
}
