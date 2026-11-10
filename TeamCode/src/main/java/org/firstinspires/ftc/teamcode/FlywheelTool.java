package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class FlywheelTool {

    private DcMotor flywheelMotor;
    private DcMotor intakeMotor;

    public void InitializeMotor(HardwareMap hardwareMap) {
        flywheelMotor = hardwareMap.get(DcMotor.class, "FlywheelMotor");
        flywheelMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        intakeMotor = hardwareMap.get(DcMotor.class, "IntakeMotor");
        intakeMotor.setDirection(DcMotorSimple.Direction.FORWARD);
    }

    public void ControlFlywheel(Gamepad gamepad) {

        double increment = 0.2, launchPower = 0.6;

        // map lb and rb to flywheel power control
        boolean increaseSpeed = gamepad.right_bumper;
        boolean decreaseSpeed = gamepad.left_bumper;

        // map rt for fire
        boolean fire = gamepad.right_trigger_pressed;
        // map lt for intake
        double intake = gamepad.left_trigger;

        if (increaseSpeed) {
            launchPower += increment;
        }
        if (decreaseSpeed) {
            launchPower -= increment;
        }

        if (fire) {
            flywheelMotor.setPower(Math.max(0, Math.min(1, launchPower)));
        }
        else {
            flywheelMotor.setPower(0);
        }
        intakeMotor.setPower(Math.max(0, Math.min(1, intake)));
    }
}
