package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class FireSystemTool {

    private DcMotor flywheelMotor;
    private DcMotor intakeMotor;

    public void InitializeMotors(HardwareMap hardwareMap) {
        flywheelMotor = hardwareMap.get(DcMotor.class, "FlywheelMotor");
        flywheelMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        intakeMotor = hardwareMap.get(DcMotor.class, "IntakeMotor");
        intakeMotor.setDirection(DcMotorSimple.Direction.FORWARD);
    }

    public void ControlFireSystem(boolean increaseSpeed, boolean decreaseSpeed, boolean fire, boolean intake) {

        double increment = 0.2, launchPower = 0.6;
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
        if (intake) {
            intakeMotor.setPower(1);
        }
        else {
            intakeMotor.setPower(0);
        }
    }
}
