package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;

public class MotorTester extends OpMode {
    FlywheelTool flywheelTool = new FlywheelTool();
    MovementTool movementTool = new MovementTool();

    @Override
    public void init() {
        flywheelTool.InitializeMotor(hardwareMap);
        movementTool.setMotorMode(hardwareMap);
    }

    @Override
    public void loop() {
        movementTool.driveControl(gamepad1);
        flywheelTool.ControlFlywheel(gamepad1);
    }
}
