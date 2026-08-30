package org.firstinspires.ftc.teamcode.pedropathing;

import com.qualcomm.hardware.lynx.LynxModule;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import java.util.List;

/**
 * A little hacky wrapper class around Tuning to do bulk reading
 */
@TeleOp(name = "Pedro Pathing Tuning", group = "Tuning")
public class TuningOpmode extends OpMode {
    private final Tuning tuning = new Tuning();

    private List<LynxModule> allHubs;

    private void updateBulkReadCache() {
        for (LynxModule hub : allHubs) {
            hub.clearBulkCache();
        }
    }

    @Override
    public void init() {
        // Transfer our hardware
        tuning.gamepad1 = gamepad1;
        tuning.gamepad2 = gamepad2;
        tuning.telemetry = telemetry;
        tuning.hardwareMap = hardwareMap;
        //Encoders now run a lot faster
        // Set up bulk reading
        allHubs = hardwareMap.getAll(LynxModule.class);
        for (LynxModule hub : allHubs) {
            hub.setBulkCachingMode(LynxModule.BulkCachingMode.MANUAL);
        }
        updateBulkReadCache();

        tuning.init();
    }

    @Override
    public void init_loop() {
        updateBulkReadCache();
        tuning.init_loop();
    }

    @Override
    public void start() {
        updateBulkReadCache();
        tuning.start();
    }

    @Override
    public void loop() {
        updateBulkReadCache();
        tuning.loop();
    }

    @Override
    public void stop() {
        tuning.stop();
    }
}
