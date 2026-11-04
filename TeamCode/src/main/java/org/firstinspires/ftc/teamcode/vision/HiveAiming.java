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
    public Position PredictPose(float initialHeight, double startingSpeed, double theta, double targetX, double targetY, float time /*seconds*/) {
        double optimalTime = 0;

        // Solve for cubic coefficients
        double A = 48.02;
        double B = -14.7 * startingSpeed * Math.cos(theta);
        double C = Math.pow(startingSpeed, 2) - (9.8) * (initialHeight - targetY);
        double D = startingSpeed * ((initialHeight - targetY)*Math.cos(theta) - s * Math.sin(theta));

        double[] tValues = solveCubic(A, B, C, D);

        // Check for complex or negative roots
        if (tValues.length == 1 && tValues[0] > 0) {
            optimalTime = tValues[0];
            double xPara = startingSpeed * Math.sin(theta) * optimalTime;
            double yPara = initialHeight + startingSpeed * Math.cos(theta) * optimalTime - (4.9)*Math.pow(optimalTime, 2);

            // xPara is the x position, yPara is the y position, and tValues[0] is the time on the graph/the time it takes to get there.
            return new Position(DistanceUnit.METER, xPara, yPara, 0, 0);
        }
    }


    // Solve of Cubic
    static double[] solveCubic(double A, double B, double C, double D) {
        double b = B / A, c = C / A, d = D / A;
        // depressed cubic u^3 + p u + q = 0, with t = u - b/3
        double p = c - b * b / 3.0;
        double q = 2.0 * b * b * b / 27.0 - b * c / 3.0 + d;
        double shift = -b / 3.0;
        double disc = q * q / 4.0 + p * p * p / 27.0;

        if (disc > 0) { // one real root
            double s = Math.sqrt(disc);
            return new double[] { Math.cbrt(-q / 2 + s) + Math.cbrt(-q / 2 - s) + shift };
        } else {        // three real roots (some may repeat)
            double r = 2 * Math.sqrt(-p / 3.0);
            double arg = (3 * q / (2 * p)) * Math.sqrt(-3.0 / p);
            arg = Math.max(-1, Math.min(1, arg)); // guard rounding error
            double phi = Math.acos(arg) / 3.0;
            return new double[] {
                r * Math.cos(phi) + shift,
                r * Math.cos(phi - 2 * Math.PI / 3) + shift,
                r * Math.cos(phi - 4 * Math.PI / 3) + shift
            };
        }
    }


}
