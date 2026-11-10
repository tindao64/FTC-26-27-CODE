package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class MovementTool {

    private DcMotor motorFL, motorFR, motorBL, motorBR;

    public void setMotorMode(HardwareMap hardwareMap) {
        motorFL = hardwareMap.get(DcMotor.class, "MotorFL");
        motorFR = hardwareMap.get(DcMotor.class, "MotorFR");
        motorBL = hardwareMap.get(DcMotor.class, "MotorBL");
        motorBR = hardwareMap.get(DcMotor.class, "MotorBR");

        motorFR.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        motorFL.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        motorBR.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        motorBL.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    public void driveControl(Gamepad gamepad) {

        // x-axis is strafe control
        // y-axis is tank style control, up/down both for forward/back, different directions for turning
        double lx = gamepad.left_stick_x;
        double ly = -gamepad.left_stick_y;

        double rx = gamepad.right_stick_x;
        double ry = -gamepad.right_stick_y;

        double frontLeftPower = ly + lx;
        double frontRightPower = ry - rx;
        double backLeftPower = ly - lx;
        double backRightPower = ry + rx;

        // cap power so that it doesn't exceed 100% output or 1.0
        double max = Math.max(Math.abs(frontLeftPower), Math.abs(frontRightPower));
        max = Math.max(Math.abs(max), Math.abs(backLeftPower));
        max = Math.max(Math.abs(max), Math.abs(backRightPower));

        if (max >= 1.0) {
            frontLeftPower /= max;
            frontRightPower /= max;
            backLeftPower /= max;
            backRightPower /= max;
        }

        // send final power to motors
        motorFL.setPower(frontLeftPower);
        motorFR.setPower(frontRightPower);
        motorBL.setPower(backLeftPower);
        motorBR.setPower(backRightPower);
    }

}
