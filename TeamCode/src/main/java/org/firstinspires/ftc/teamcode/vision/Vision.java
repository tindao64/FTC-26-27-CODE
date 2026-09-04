package org.firstinspires.ftc.teamcode.vision;

import android.util.Size;
import com.bylazar.camerastream.PanelsCameraStream;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.opencv.ColorBlobLocatorProcessor;
import org.opencv.core.Point;

public class Vision {
    public final VisionPortal visionPortal;
    public final ColorBlobLocatorProcessor colorBlobProcessor;

    private final Size cameraSize;

    public Vision(VisionPortal visionPortal, ColorBlobLocatorProcessor colorBlobProcessor, Size cameraSize) {
        this.visionPortal = visionPortal;
        this.colorBlobProcessor = colorBlobProcessor;

        this.cameraSize = cameraSize;
    }

    /**
     * Normalizes pixel-based coordinates to a 0.0-1.0 based coordinate system
     * @param point The input point, scaled from 0 to the camera size in pixels
     * @return The normalized point, scaled from 0.0 to 1.0
     */
    public Point normalizeCoordinates(Point point) {
        return new Point(point.x / cameraSize.getWidth(), point.y / cameraSize.getHeight());
    }
}
