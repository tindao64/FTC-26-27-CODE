package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;

public class FireControlTester extends OpMode {
    FireSystemTool fireSystemTool = new FireSystemTool();

    @Override
    public void init() {
        fireSystemTool.InitializeMotors(hardwareMap);
    }

    @Override
    public void loop() {
        // map lb and rb to flywheel power control
        boolean increaseSpeed = gamepad1.right_bumper;
        boolean decreaseSpeed = gamepad1.left_bumper;

        // map rt for fire
        boolean fire = gamepad1.right_trigger_pressed;
        // map lt for intake
        boolean intake = gamepad1.left_trigger_pressed;

        fireSystemTool.ControlFireSystem(increaseSpeed, decreaseSpeed, fire, intake);

    }
}
