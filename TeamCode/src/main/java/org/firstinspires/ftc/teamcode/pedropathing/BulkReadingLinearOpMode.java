package org.firstinspires.ftc.teamcode.pedropathing;

import com.qualcomm.hardware.lynx.LynxModule;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import java.util.List;

public abstract class BulkReadingLinearOpMode extends LinearOpMode {
    private List<LynxModule> allHubs;

    protected void setupBulkReading() {
        allHubs = hardwareMap.getAll(LynxModule.class);

        for (LynxModule hub : allHubs) {
            hub.setBulkCachingMode(LynxModule.BulkCachingMode.MANUAL);
        }
        updateBulkReadCache();
    }

    protected void updateBulkReadCache() {
        for (LynxModule hub : allHubs) {
            hub.clearBulkCache();
        }
    }
}
