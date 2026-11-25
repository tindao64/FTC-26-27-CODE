package org.firstinspires.ftc.teamcode;

import com.pedropathing.geometry.Pose;
import com.pedropathing.math.Vector;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Pose3D;
import org.firstinspires.ftc.robotcore.external.navigation.Position;
import org.firstinspires.ftc.robotcore.external.navigation.YawPitchRollAngles;

public class Utilities {
    public static Pose3D add(Pose3D a, Pose3D b)
    {
        return new Pose3D(add(a.getPosition(),b.getPosition()),add(a.getOrientation(),b.getOrientation()));
    }
    public static Position add(Position a, Position b)
    {
        Position unitadjustedb = b.toUnit(a.unit);
        return new Position(a.unit,a.x + unitadjustedb.x,a.y + unitadjustedb.y,a.z + unitadjustedb.z,Math.max(a.acquisitionTime,b.acquisitionTime));
    }
    public static YawPitchRollAngles add(YawPitchRollAngles a, YawPitchRollAngles b)
    {
        return new YawPitchRollAngles(AngleUnit.DEGREES,a.getYaw(AngleUnit.DEGREES) + b.getYaw(AngleUnit.DEGREES),a.getPitch(AngleUnit.DEGREES) + b.getPitch(AngleUnit.DEGREES),a.getRoll(AngleUnit.DEGREES) + b.getRoll(AngleUnit.DEGREES),Math.max(a.getAcquisitionTime(),b.getAcquisitionTime()));
    }
    public static Pose3D sub(Pose3D a, Pose3D b)
    {
        return new Pose3D(sub(a.getPosition(),b.getPosition()),sub(a.getOrientation(),b.getOrientation()));
    }
    public static Position sub(Position a, Position b)
    {
        Position unitadjustedb = b.toUnit(a.unit);
        return new Position(a.unit,a.x - unitadjustedb.x,a.y - unitadjustedb.y,a.z - unitadjustedb.z,Math.max(a.acquisitionTime,b.acquisitionTime));
    }
    public static YawPitchRollAngles sub(YawPitchRollAngles a, YawPitchRollAngles b)
    {
        return new YawPitchRollAngles(AngleUnit.DEGREES,a.getYaw(AngleUnit.DEGREES) - b.getYaw(AngleUnit.DEGREES),a.getPitch(AngleUnit.DEGREES) - b.getPitch(AngleUnit.DEGREES),a.getRoll(AngleUnit.DEGREES) - b.getRoll(AngleUnit.DEGREES),Math.max(a.getAcquisitionTime(),b.getAcquisitionTime()));
    }
    public static Pose sub(Pose a, Pose b)
    {
        return new Pose(a.getX() - b.getX(), a.getY() - b.getY(), a.getHeading() - b.getHeading());
    }

    public static Pose add(Pose a, Pose b)
    {
        return new Pose(a.getX() + b.getX(), a.getY() + b.getY(), a.getHeading() + b.getHeading());
    }
    public static Vector PoseToVector(Pose p)
    {
        return new Vector(p.getX(),p.getY());
    }
    public static Pose VectorToPose(Vector v)
    {
        return new Pose(v.getXComponent(),v.getYComponent());
    }

    public static Pose Pose3DToPose(Pose3D pose)
    {
        Position pos = pose.getPosition();
        return new Pose(pos.x,pos.y,pose.getOrientation().getYaw());
    }
}
