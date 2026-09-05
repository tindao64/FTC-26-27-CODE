package org.firstinspires.ftc.teamcode.pedropathing;

import com.pedropathing.follower.Follower;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import static org.firstinspires.ftc.teamcode.OpModeHelper.*;

public abstract class FollowerLinearOpMode extends LinearOpMode {
    public Follower follower;
    private final BulkReadingHelper bulkReader = new BulkReadingHelper();

    /**
     * MUST call this in init()
     */
    protected final void initFollowerOpMode() {
         combinePanelsTelemetry(this);
         bulkReader.initBulkReading(hardwareMap);
         follower = createFollower(hardwareMap);
    }

    protected final void updateFollower() {
        bulkReader.flushBulkReadCache();
        follower.update();
    }
}

