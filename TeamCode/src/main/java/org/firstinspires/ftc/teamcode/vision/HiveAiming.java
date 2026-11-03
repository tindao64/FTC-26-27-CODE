package org.firstinspires.ftc.teamcode.vision;

import static org.firstinspires.ftc.teamcode.Utilities.add;
import static org.firstinspires.ftc.teamcode.Utilities.sub;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Pose2D;
import org.firstinspires.ftc.robotcore.external.navigation.Pose3D;
import org.firstinspires.ftc.robotcore.external.navigation.Position;
import org.firstinspires.ftc.robotcore.external.navigation.YawPitchRollAngles;
import org.firstinspires.ftc.teamcode.teamspecific.Hardware;

public class HiveAiming {
    private Hardware hardware;
    public HiveAiming(Hardware hardware) {
        this.hardware = hardware;
    }

    // NOT absolute value, a normalized direction of movement
    public Pose2D GetMoveIntent(Pose3D HivePose,Pose3D RobotPose)
    {
        YawPitchRollAngles orientation = RobotPose.getOrientation();
       float ang = hardware.launchAngle() + (float)orientation.getPitch(AngleUnit.RADIANS);
       float speed = hardware.launchPower();
       float x,y,z; //x forward, y up, z side to side   local(robot) space
       x = (float)Math.cos(ang) * speed;
       y = (float)Math.sin(ang) * speed;
       z = 0; // assuming launcher shoots perfectly straight (ball isn't strafing)
       float yaw = (float)orientation.getYaw(AngleUnit.RADIANS);
       //Rotation around the Y axis with yaw = theta
       float[][] rotmatrix = {
               {(float)Math.cos(yaw),0f,(float)Math.sin(yaw)},
               {           0f       ,1f         ,0f         },
               {(float)Math.sin(yaw),0f,(float)Math.cos(yaw)}
       };
       //Rotate plane of launch in local(robot) space to global(field) space
        float tmpx,tmpy,tmpz;
        tmpx = x;
        tmpy = y;
        tmpz = z;

        x = rotmatrix[0][0] * tmpx + rotmatrix[0][1] * tmpy + rotmatrix[0][2] * tmpz;
        y = rotmatrix[1][0] * tmpx + rotmatrix[1][1] * tmpy + rotmatrix[1][2] * tmpz;
        z = rotmatrix[2][0] * tmpx + rotmatrix[2][1] * tmpy + rotmatrix[2][2] * tmpz;
        Position v = new Position(DistanceUnit.METER,x,y,z,0);
        //Binary search for closest point on ballistic curve
        float bottom = 0f;
        float middle = 2.5f; //seconds
        float top = 5f; // seconds
        int iterations = 10;
        Position prediction;
        for (int i = 0; i < iterations; i++)
        {
            prediction = PredictPose(add(RobotPose.getPosition(),hardware.getLauncherMountingPose().getPosition()),v,middle);
            Position difference = sub(prediction,HivePose.getPosition());
            difference = new Position(
                            DistanceUnit.METER,
                            Math.abs(difference.x),
                            Math.abs(difference.y),
                            Math.abs(difference.z),
                            0
                    );
            //Get distance of difference
            float xydistance = (float)Math.sqrt(Math.pow(difference.x,2) + Math.pow(difference.y, 2));
            float distance = (float)Math.sqrt(Math.pow(xydistance,2) + Math.pow(difference.z, 2));
            if (distance > middle)
            {
                bottom = middle;
            }
            else {
                top = middle;
            }
            middle = (top + bottom)/2f;

        }
        float epsilon = 0.001f;

        return new Pose2D(DistanceUnit.METER,0f,0f, AngleUnit.RADIANS,0);
    }
    public Position PredictPose(Position StartingPose,Position StartingVelocity, float Time /*seconds*/)
    {
    return new Position(DistanceUnit.METER,0f,0f,0f,0);
    }

}
