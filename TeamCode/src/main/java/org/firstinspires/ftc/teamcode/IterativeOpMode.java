package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class IterativeOpMode extends OpMode {
    DcMotor motorFL,motorFR,motorBR,motorBL;
    @Override
    public void init() {
        HardwareMap hardwareNap = this.hardwareMap;
        motorFL = hardware.get(DCMotor.class,"MotorFL");
        motorFR = hardware.get(DCMotor.class,"MotorFR");
        motorBR = hardware.get(DCMotor.class,"MotorBR");
        motorBL = hardware.get(DCMotor.class,"MotorBL");
    }

    @Override
    public void loop() {

        mecanumDriveControl(gamepad1);
    }
        public void mecanumDriveControl(Gamepad gamepad) {
            double x = gamepad.left_stick_x;//Input for horizontal movement
            double y = -gamepad.left_stick_y;//Input for vertical movement
            double turn = gamepad.right_stick_x;//Input for turn
            if (gamepad.left_trigger > 0.5){
                x *= 0.4;
                y *= 0.4;
                turn *= 0.4;
            }
            double theta = Math.atan2(x, y);//Find ratio of x/y
            double power = Math.sqrt(x*x + y*y);//Power dependant on how hard you push the x and y joystick.
            mecanumDriveMove(Math.toDegrees(theta), power, turn);
        }

        /**
         * Drive in a given direction and spin with a given speed
         * @param angle The angle to drive, in degrees. 0 is up, 90 is right
         * @param power The drive power. From 0.0 to 1.0. This controls both rotational and translational power
         * @param turn The amount of spin, from -1.0 (CCW full speed) to 1.0 (CW full speed)
         */
        public void mecanumDriveMove(double angle, double power, double turn) {
            double theta = Math.toRadians(angle);//Ratio in degrees
            double strafe = Math.sin(theta);//X power
            double drive = Math.cos(theta);//Y power

            mecanumDriveMove(drive, turn, strafe, power);
        }
        /**
         *
         * @param drive Forward Motion
         * @param turn  Rotational Motion
         * @param strafe Horizontal Motion
         * @param power Power
         */
        public void mecanumDriveMove(double drive, double turn, double strafe, double power) {
            // Calculations for Mecanum Drive
            double topLeftPower = power * (drive + strafe) + turn;
            double topRightPower = power * (drive - strafe) - turn;
            double bottomLeftPower = power * (drive - strafe) + turn;
            double bottomRightPower = power * (drive + strafe) - turn;

            // Normalize big values
            // If any is greater than 1.0 or less than -1.0, preserve direction
            double max = Math.max(
                    Math.max(Math.abs(topLeftPower), Math.abs(topRightPower)),
                    Math.max(Math.abs(bottomLeftPower), Math.abs(bottomRightPower))
            );
            if (max > 1.0) {
                topLeftPower /= max;
                topRightPower /= max;
                bottomLeftPower /= max;
                bottomRightPower /= max;
            }

            motorTL.setPower(topLeftPower * DRIVETRAIN_SPEED);
            motorTR.setPower(topRightPower * DRIVETRAIN_SPEED) ;
            motorBL.setPower(bottomLeftPower * DRIVETRAIN_SPEED );
            motorBR.setPower(bottomRightPower * DRIVETRAIN_SPEED );
        }

        public void brake() {
            motorTL.setPower(0);
            motorTR.setPower(0);
            motorBL.setPower(0);
            motorBR.setPower(0);
        }

    }
