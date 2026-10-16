package org.firstinspires.ftc.teamcode;

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
}
