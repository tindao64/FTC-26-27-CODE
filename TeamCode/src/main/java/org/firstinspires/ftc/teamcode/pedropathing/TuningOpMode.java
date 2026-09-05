package org.firstinspires.ftc.teamcode.pedropathing;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import static org.firstinspires.ftc.teamcode.OpModeHelper.*;

/**
 * A little hacky wrapper class around Tuning to do bulk reading
 */
@TeleOp(name = "Pedro Pathing Tuning", group = "Tuning")
public class TuningOpMode extends LinearOpMode {
    private final Tuning tuning = new Tuning();

    @Override
    public void runOpMode() {
        combinePanelsTelemetry(this);
        BulkReadingHelper bulkReader = new BulkReadingHelper();
        bulkReader.initBulkReading(hardwareMap);

        // Transfer our hardware
        tuning.gamepad1 = gamepad1;
        tuning.gamepad2 = gamepad2;
        tuning.telemetry = telemetry;
        tuning.hardwareMap = hardwareMap;

        tuning.init();
        while (opModeInInit()) {
            bulkReader.flushBulkReadCache();
            tuning.init_loop();
            telemetry.update();
        }
        // to check if you pressed START instead of STOP
        if (opModeIsActive()) {
            bulkReader.flushBulkReadCache();
            tuning.start();
            while (opModeIsActive()) {
                bulkReader.flushBulkReadCache();
                tuning.loop();
                telemetry.update();
            }
        }
        tuning.stop();
    }
}
