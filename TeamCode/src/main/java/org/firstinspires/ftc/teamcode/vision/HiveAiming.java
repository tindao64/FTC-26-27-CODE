package org.firstinspires.ftc.teamcode.vision;

import androidx.annotation.NonNull;
import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.Pose;
import com.pedropathing.math.Vector;
import org.firstinspires.ftc.robotcore.external.navigation.*;
import org.firstinspires.ftc.teamcode.GameState;
import org.firstinspires.ftc.teamcode.teamspecific.Hardware;
import org.firstinspires.ftc.vision.apriltag.AprilTagClusterDetection;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;

import java.util.ArrayList;

import static org.firstinspires.ftc.teamcode.Utilities.*;

//region BackupMath
/*
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


        TODO: get closest point on ballistic curve with calc(Going to be/is in HiveAimingMath branch)
        float pointx = ......
        float pointy = ......


    float pointx = 0f;
    float pointy = 0f;
    Get vector from robot -> goal on xz plane
    Position robottohive = sub(goalposition,robotposition);
    Get angle theta from z axis to goal - robot vector on xz plane
    float theta = (float)Math.atan2(z,x);

    float yaw = (float)orientation.getYaw(AngleUnit.RADIANS);
    Rotation around the Y axis with yaw = theta
    float[][] rotmatrix = {
            {(float)Math.cos(yaw),0f,(float)Math.sin(yaw)},
            {           0f       ,1f         ,0f         },
            {(float)Math.sin(yaw),0f,(float)Math.cos(yaw)}
    };
    Rotate plane of launch in local(robot) space to global(field) space

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
        public Position PredictPose(float initialHeight, double startingSpeed, double theta, double targetX, double targetY, float time seconds) {
            double optimalTime = 0;

             Solve for cubic coefficients
            double A = 48.02;
            double B = -14.7 * startingSpeed * Math.cos(theta);
            double C = Math.pow(startingSpeed, 2) - (9.8) * (initialHeight - targetY);
            double D = startingSpeed * ((initialHeight - targetY)*Math.cos(theta) - startingSpeed * Math.sin(theta));

            double[] tValues = solveCubic(A, B, C, D);

            Check for complex or negative roots
            if (tValues.length == 1 && tValues[0] > 0) {
                optimalTime = tValues[0];
                double xPara = startingSpeed * Math.sin(theta) * optimalTime;
                double yPara = initialHeight + startingSpeed * Math.cos(theta) * optimalTime - (4.9)*Math.pow(optimalTime, 2);

               xPara is the x position, yPara is the y position, and tValues[0] is the time on the graph/the time it takes to get there.
                return new Position(DistanceUnit.METER, xPara, yPara, 0, 0);
            }
            return null;
        }


        Solve of Cubic
        static double[] solveCubic(double A, double B, double C, double D) {
            double b = B / A, c = C / A, d = D / A;
            depressed cubic u^3 + p u + q = 0, with t = u - b/3
            double p = c - b * b / 3.0;
            double q = 2.0 * b * b * b / 27.0 - b * c / 3.0 + d;
            double shift = -b / 3.0;
            double disc = q * q / 4.0 + p * p * p / 27.0;

            if (disc > 0) { ne real root
                double s = Math.sqrt(disc);
                return new double[] { Math.cbrt(-q / 2 + s) + Math.cbrt(-q / 2 - s) + shift };
            } else {        three real roots (some may repeat)
                double r = 2 * Math.sqrt(-p / 3.0);
                double arg = (3 * q / (2 * p)) * Math.sqrt(-3.0 / p);
                arg = Math.max(-1, Math.min(1, arg)); guard rounding error
                double phi = Math.acos(arg) / 3.0;
                return new double[] {
                        r * Math.cos(phi) + shift,
                        r * Math.cos(phi - 2 * Math.PI / 3) + shift,
                        r * Math.cos(phi - 4 * Math.PI / 3) + shift
                };
            }
        }
       */
//endregion
public class HiveAiming {

    public enum GoalSide {
        Audience,
        Team
    }

    private Hardware hardware;
    private Vision vision;
    private Follower follower;
    double arclength = 0.3926f /*22.5 degrees because 22.5*2 = 45 for estimate*/; // radians either direction from the goal

    public HiveAiming(Hardware hardware, Vision vision, Follower follower) {
        this.hardware = hardware;
        this.vision = vision;
        this.follower = follower;
    }

    // Get goal pose in field space(global space)
    public Pose3D getGoalPose() throws Exception {
        GoalSide side = null;
        String goalname = GameState.teamColor == GameState.TeamColor.RED ? "RED" : "BLUE";
        ArrayList<AprilTagDetection> detections = vision.aprilTagProcessor.getDetections();
        AprilTagDetection detection = null;
        for (AprilTagDetection d : detections) {
            //Is goal, not single tag and either detection is null or it is more scorable.
            if (d instanceof AprilTagClusterDetection && ((AprilTagClusterDetection) d).metadata.name.contains(goalname)) {
                if (d.ftcPose.elevation > 43.95f) {
                    detection = d;
                } else {
                    side = getRobotPose3D().getPosition().y > 70.75f ? GoalSide.Audience : GoalSide.Team;
                }
            }
        }
        Pose3D robotpose3d = getRobotPose3D();
        Pose3D pose;
        if (detection == null && side != null) {
            pose = new Pose3D(new Position(DistanceUnit.INCH, 70.75f + (GameState.teamColor == GameState.TeamColor.RED ? -24.73f : 24.73f), 70.75f + (side == GoalSide.Audience ? -8.58f : 8.58f), 0, 0), new YawPitchRollAngles(AngleUnit.DEGREES, side == GoalSide.Team ? 0f : 180f, 30f, 0f, 0));
        } else {
            throw new Exception("Shit happened: HiveAiming side doesnt exist and no april tag was found");
        }
        pose = add(hardware.normalizeCameraPose(detection.robotPose), robotpose3d);
        return pose;
    }

    @NonNull
    private Pose3D getRobotPose3D() {
        Pose robotpose = follower.getPose();
        return new Pose3D(new Position(DistanceUnit.INCH, robotpose.getX(), 0, robotpose.getY(), 0), new YawPitchRollAngles(AngleUnit.RADIANS, robotpose.getHeading(), 0, 0, 0));
    }

    //return the target position of the robot in order to score
    private Pose getShootingPose() throws Exception {
        Pose GoalPose = Pose3DToPose(getGoalPose());
        Vector circlecenter = PoseToVector(GoalPose);
        Vector robotpose = PoseToVector(Pose3DToPose(getRobotPose3D()));
        Vector goal2robot = robotpose.minus(circlecenter);
        goal2robot = goal2robot.normalize();
        double angle = Math.atan2(goal2robot.getYComponent(), goal2robot.getXComponent());
        // 0 - 360 to -180 to 180
        
        /*angle %= 360;
        if(angle > 180)
        {
            angle -= 360;
        }*/
        double goalHeading = GoalPose.getHeading();
        double goalThresh = goalHeading + Math.PI;
        goalThresh %= Math.PI * 2;
        if (Math.abs(angle - goalHeading) < arclength) {
            Pose p = VectorToPose(circlecenter.plus(goal2robot));
            p.setHeading(angle + Math.PI);
            return p;
        } else {
            double clampedangle = angle < goalThresh ? arclength : -arclength;
            double y = Math.sin(clampedangle);
            double x = Math.cos(clampedangle);
            Vector v = new Vector(x, y);
            Pose p = VectorToPose(circlecenter.plus(v));
            double direction = clampedangle + Math.PI;
            p.setHeading(direction);
            return p;
        }

    }


}
