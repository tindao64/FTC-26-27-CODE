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
       Pose3D offset = hardware.getLauncherMountingPose();
       Pose3D launcherpose = add(offset,RobotPose);
       float x,y,z; //x forward, y up, z side to side   local(robot) space
        x = 0; // assuming launcher shoots perfectly straight (ball isn't strafing)
       y = (float)Math.sin(ang) * speed;
       z = (float)Math.cos(ang) * speed;
        Position robotposition = RobotPose.getPosition();
       Position goalposition = HivePose.getPosition();
       //Calculate coordinates of goal on a 2D plane to make calc in PredictPose easier
        float planex = (float)Math.sqrt(Math.pow(goalposition.x, 2) + Math.pow(goalposition.z,2));
        float planey = (float)goalposition.y;
        float initialheight = (float)launcherpose.getPosition().toUnit(DistanceUnit.METER).y;

        /*
        TODO: get closest point on ballistic curve with calc(Going to be/is in HiveAimingMath branch)
        float pointx = ......
        float pointy = ......
         */

        float pointx = 0f;
        float pointy = 0f;
        //Get vector from robot -> goal on xz plane
        Position robottohive = sub(goalposition,robotposition);
        //Get angle theta from z axis to goal - robot vector on xz plane
        float theta = (float)Math.atan2(z,x);

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
        float epsilon = 0.001f;

        return new Pose2D(DistanceUnit.METER,0f,0f, AngleUnit.RADIANS,0);
    }
    public Position PredictPose(double initialHeight,double startingSpeed, double theta, double time /*seconds*/)
    {
        double xPara = startingSpeed * Math.sin(theta) * time;
        double yPara = initialHeight + startingSpeed * Math.cos(theta) * time - 0.5f*(9.8f)*time*time;
        
        
        
        
    return new Position(DistanceUnit.METER,0f,0f,0f,0);
    }
    
    
}
