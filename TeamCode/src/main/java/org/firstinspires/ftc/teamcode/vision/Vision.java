package org.firstinspires.ftc.teamcode.vision;

import android.util.Size;
import com.bylazar.camerastream.PanelsCameraStream;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.opencv.ColorBlobLocatorProcessor;
import org.opencv.core.Point;

public class Vision {
    /**
     * The VisionPortal.
     *
     * This is the manager of all the vision processors below, asynchronously feeding them
     * camera frames. Use this to manage the camera's state, and to start/stop
     * "streaming" the camera feed to the processors. This by itself does no processing,
     * all actual computer vision happens in processors.
     *
     * There is no manual "update" required with the portal or its processors, as there is
     * with a Follower. Everything happens asynchronously in a separate thread (i.e. on
     * its own, don't worry about it).
     */
    public final VisionPortal visionPortal;

    /**
     * Here are the individual vision processors. Query these to get results.
     */

    /**
     * The ColorBlobLocatorProcessor finds blobs of a certain color, in this case, it
     * will find circular blobs of yellow on the camera stream, which are likely balls.
     */
    public final ColorBlobLocatorProcessor colorBlobProcessor;

    /**
     * Normalizes pixel-based coordinates to a 0.0-1.0 based coordinate system.
     *
     * This is to decouple the camera resolution from the user code, such that if one changes
     * the other does not break.
     *
     * @param point The input point, scaled from 0 to the camera size in pixels
     * @return The normalized point, scaled from 0.0 to 1.0
     */
    public Point normalizeCoordinates(Point point) {
        return new Point(point.x / cameraSize.getWidth(), point.y / cameraSize.getHeight());
    }








    /**********************************************
     * Implementation
     * Not very important unless you're adding another processor.
     *********************************************/
    private final Size cameraSize;
    public Vision(VisionPortal visionPortal, ColorBlobLocatorProcessor colorBlobProcessor, Size cameraSize) {
        this.visionPortal = visionPortal;
        this.colorBlobProcessor = colorBlobProcessor;

        this.cameraSize = cameraSize;
    }
}
