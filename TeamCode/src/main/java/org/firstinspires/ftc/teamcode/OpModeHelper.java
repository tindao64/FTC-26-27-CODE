package org.firstinspires.ftc.teamcode;

import com.bylazar.camerastream.PanelsCameraStream;
import com.bylazar.gamepad.PanelsGamepad;
import com.bylazar.telemetry.JoinedTelemetry;
import com.bylazar.telemetry.PanelsTelemetry;
import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.Pose;
import com.qualcomm.hardware.lynx.LynxModule;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;
import org.firstinspires.ftc.teamcode.teamspecific.Hardware;
import org.firstinspires.ftc.teamcode.teamspecific.HardwareManager;
import org.firstinspires.ftc.teamcode.vision.Vision;

import java.util.List;

/**
 * This is a helper class that contains some useful functions for all
 * OpModes, like using the Panels functions or using Pedro Pathing.
 */
public class OpModeHelper {
    /**
     * A helper function to create a Follower object from a hardware
     * map. This also handles misc. things to set up the follower
     *
     * @param hardwareMap Your hardware map
     * @return The newly created follower
     */
    public static Follower createFollower(HardwareMap hardwareMap) {
        Follower follower = HardwareManager.INSTANCE.createFollower(hardwareMap);

        // reset position hack
        follower.setStartingPose(new Pose());
        follower.update();
        follower.setPose(new Pose());

        return follower;
    }

    /**
     * Creates a Vision object for you, and sets up the Panels
     * camera feed automatically. Be sure to call `destroyVision`
     * at the very end, i.e. in stop() or at the end of runOpMode()
     * @param hardwareMap your hardware map
     * @return the created vision
     */
    public static Vision createVision(HardwareMap hardwareMap) {
        Vision vision = HardwareManager.INSTANCE.createVision(hardwareMap);

        PanelsCameraStream.INSTANCE.startStream(vision.visionPortal, 5);
        return vision;
    }

    /**
     * Cleans up all the resources associated with a Vision.
     * Be sure to always call this at the end of your OpMode.
     * @param vision your Vision object
     */
    public static void destroyVision(Vision vision) {
        vision.visionPortal.stopLiveView();
        PanelsCameraStream.INSTANCE.stopStream();
        vision.visionPortal.stopStreaming();
    }

    /**
     * A helper function to set up combined panels gamepads for your
     * OpMode.
     * <p>
     * Panels has a "Gamepad" widget that allows you to have virtual
     * gamepads on your computer, but you need to set it up first.
     * @param opMode your OpMode
     */
    public static void combinePanelsGamepads(OpMode opMode) {
        // fix "non-atomic operation" warning?
        opMode.gamepad1 = PanelsGamepad.INSTANCE.getFirstManager().asCombinedFTCGamepad(opMode.gamepad1);
        opMode.gamepad2 = PanelsGamepad.INSTANCE.getSecondManager().asCombinedFTCGamepad(opMode.gamepad2);
    }

    /**
     * A helper function to have telemetry on both Panels and on the
     * Driver Station.
     * <p>
     * This is a drop-in replacement for the original telemetry,
     * so you just call this once at the top and everything works
     * seamlessly.
     * @param opMode your OpMode
     */
    public static void combinePanelsTelemetry(OpMode opMode) {
        opMode.telemetry = new JoinedTelemetry(PanelsTelemetry.INSTANCE.getFtcTelemetry(), opMode.telemetry);
    }


    /**
     * sets up all panels things
     */
    public static void setupPanels(OpMode opMode) {
        combinePanelsTelemetry(opMode);
        combinePanelsGamepads(opMode);
    }

    /**
     * A class that manages bulk reading, presenting an easy
     * interface
     * <p>
     * Basically, bulk reading is a "cache" of sensor data, that
     * holds on to previous values to avoid re-querying the hardware
     * sensor. This speeds up time, but you need to "flush" the cache
     * every loop iteration so that new values are read instead of
     * re-using old values.
     * <p>
     * In addition, bulk reading also reads all the sensors'
     * data at once, instead of one by one, using only one
     * "LynxCommand" instead of one "LynxCommand" for each sensor,
     * speeding up input. (LynxCommands are basically just commands
     * for the control/expansion hubs, they take a fixed amount of
     * time each, so using only one for all sensors is a lot faster
     * than using one for every sensor).
     */
    public static class BulkReadingHelper {
        private List<LynxModule> hubs;

        /**
         * Sets up MANUAL bulk reading for this OpMode
         * @param hardwareMap your hardware map
         */
        public void initBulkReading(HardwareMap hardwareMap) {
            hubs = hardwareMap.getAll(LynxModule.class);

            // Runs through all the hubs and sets their bulk reading
            // mode to MANUAL
            for (LynxModule hub : hubs) {
                hub.setBulkCachingMode(LynxModule.BulkCachingMode.MANUAL);
            }

            // Updates the bulk reading cache initially, to get
            // fresh values
            flushBulkReadCache();
        }

        /**
         * Flushes the bulk reading cache
         * <p>
         * Run this ONCE (not more, not less) every loop iteration
         * for optimal performance. This clears the saved sensor
         * data to re-query fresh values.
         */
        public void flushBulkReadCache() {
            for (LynxModule hub : hubs) {
                hub.clearBulkCache();
            }
        }
    }
}

/*
Example OpMode:


import static org.firstinspires.ftc.teamcode.OpModeHelper.*

public class MyOpMode extends LinearOpMode {
    @Override
    public void runOpMode() {
        setupPanels(this);

        BulkReadingHelper bulkReader = new BulkReadingHelper();
        bulkReader.initBulkReading(hardwareMap);

        Follower follower = createFollower(hardwareMap);
        Vision vision = createVision(hardwareMap);

        waitForStart();

        while (opModeIsActive()) {
            bulkReader.flushBulkReadCache();
            follower.update();
        }

        destroyVision(vision);
    }
}

*/
