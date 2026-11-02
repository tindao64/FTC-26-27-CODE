package org.firstinspires.ftc.teamcode.vision;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Pose2D;
import org.firstinspires.ftc.robotcore.external.navigation.Pose3D;
import org.firstinspires.ftc.teamcode.teamspecific.Hardware;

public class HiveAiming {
    private Hardware hardware;
    public HiveAiming(Hardware hardware) {
        this.hardware = hardware;
    }

    // NOT absolute value, a normalized direction of movement
    public Pose2D GetMoveIntent(Pose3D HivePose)
    {
       float ang = hardware.launchAngle();
       float speed = hardware.launchPower();
       float x,y;
       x = (float)Math.cos(ang);
       y = (float)Math.sin(ang);
       x *= speed;
       y *= speed;
       return new Pose2D(DistanceUnit.METER,0f,0f, AngleUnit.RADIANS,0);
    }

}
