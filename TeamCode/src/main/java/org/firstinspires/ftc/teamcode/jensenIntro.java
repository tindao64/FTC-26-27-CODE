package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

public class jensenIntro extends LinearOpMode{


    DcMotor lbMotor,lfMotor,rbMotor,rfMotor;
    Servo servo1;
    CRServo servo2;
    public void runOpMode() throws InterruptedException {


        servo1 = hardwareMap.get(Servo.class, "hardwaremapname");
        servo2 = hardwareMap.get(CRServo.class,"67");
        lbMotor = hardwareMap.get(DcMotor.class,  "lbMotor");
        lfMotor = hardwareMap.get(DcMotor.class,"lfMotor");
        rbMotor = hardwareMap.get(DcMotor.class, "rbMotor");
        rfMotor = hardwareMap.get(DcMotor.class, "rfMotor");

        telemetry.addData("67",67);
        telemetry.update();


    }
}

