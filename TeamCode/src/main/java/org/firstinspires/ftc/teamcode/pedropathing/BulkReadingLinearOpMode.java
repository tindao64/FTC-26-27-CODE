package org.firstinspires.ftc.teamcode.pedroPathing;

import com.qualcomm.hardware.lynx.LynxModule;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

public abstract class BulkReadingLinearOpMode extends LinearOpMode{

    protect public void BulkReadingOpMode(){
        // Transfer our hardware
        //Encoders now run a lot faster
        // Set up bulk reading
        allHubs = hardwareMap.getAll(LynxModule.class);
        for (LynxModule hub : allHubs) {
            hub.setBulkCachingMode(LynxModule.BulkCachingMode.MANUAL);
        }
        updateBulkReadCache();

        tuning.init();
    }
