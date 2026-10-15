package org.firstinspires.ftc.teamcode.pedropathing;

import com.qualcomm.hardware.lynx.LynxModule;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import java.util.List;

/**
 * A little hacky wrapper class around Tuning to do bulk reading
 */
@TeleOp(name = "Pedro Pathing Tuning", group = "Tuning")
public class TuningOpmode extends BulkReadingLinearOpMode {
    private final Tuning tuning = new Tuning();

    @Override
    public void runOpMode() {
        setupBulkReading();

        // Transfer our hardware
        tuning.gamepad1 = gamepad1;
        tuning.gamepad2 = gamepad2;
        tuning.telemetry = telemetry;
        tuning.hardwareMap = hardwareMap;

        tuning.init();
        while (opModeInInit()) {
            updateBulkReadCache();
            tuning.init_loop();
            telemetry.update();
        }
        // to check if you pressed START instead of STOP
        if (opModeIsActive()) {
            updateBulkReadCache();
            tuning.start();
            while (opModeIsActive()) {
                updateBulkReadCache();
                tuning.loop();
                telemetry.update();
            }
        }
        tuning.stop();
    }
}
