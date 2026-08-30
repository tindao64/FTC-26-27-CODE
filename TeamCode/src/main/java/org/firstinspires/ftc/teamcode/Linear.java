package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.Servo;

public class Linear extends LinearOpMode {
    CRServo crservo;
    Servo servo;
    @Override
    public void runOpMode() throws InterruptedException {
        HardwareMap hardwareMap = this.hardwareMap
        waitForStart();

    }
}
