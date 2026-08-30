package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class IterativeOpMode extends OpMode {
    DcMotor motorFL,motorFR,motorBR,motorBL;
    @Override
    public void init() {
        telemetry.addData("OpMode Initialized: ",67);
    }

    @Override
    public void loop() {
        telemetry.addData("OpMode Started: ",67);
    }
}

