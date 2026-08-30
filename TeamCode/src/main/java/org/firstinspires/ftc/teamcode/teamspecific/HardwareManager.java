package org.firstinspires.ftc.teamcode.teamspecific;

import com.pedropathing.follower.Follower;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import org.firstinspires.ftc.robotcore.external.Telemetry;

import java.util.Objects;
import java.util.TreeMap;
import java.util.function.Supplier;

public enum HardwareManager {
    INSTANCE;
    private final TreeMap<String, Supplier<Hardware>> hardwareTypes = new TreeMap<>();
    private Hardware hardware = null;

    public boolean hasSelectedHardware() {
        return hardware != null;
    }

    @Autonomous(name="Select Team")
    public static class SelectionOpMode extends LinearOpMode {
        @Override
        public void runOpMode() {
            INSTANCE.selectHardwareGamepad(gamepad1, telemetry, () -> this.opModeInInit() || this.opModeIsActive());
        }
    }

    public void registerHardware(String name, Supplier<Hardware> factory) {
        if (hardwareTypes.containsKey(name)) {
            throw new IllegalStateException("Hardware already registered: " + name);
        }

        hardwareTypes.put(name, factory);
    }

    public void selectHardwareGamepad(Gamepad gamepad, Telemetry telemetry, Supplier<Boolean> isActive) {
        assert !hardwareTypes.isEmpty();
        String entry = hardwareTypes.firstKey();

        while (isActive.get()) {
            // Print menu
            telemetry.clearAll();
            telemetry.addLine("Select a hardware:");
            telemetry.addLine("Use dpad to navigate, right bumper to select");

            for (String e : hardwareTypes.keySet()) {
                boolean selected = Objects.equals(e, entry);

                String line = (selected ? "> " : "  ") + e;
                telemetry.addLine(line);
            }

            telemetry.update();

            // Gamepad selection
            if (gamepad.dpadDownWasPressed()) {
                String next = hardwareTypes.higherKey(entry);
                if (next != null) {
                    entry = next;
                }
            }

            if (gamepad.dpadUpWasPressed()) {
                String prev = hardwareTypes.lowerKey(entry);
                if (prev != null) {
                    entry = prev;
                }
            }

            if (gamepad.rightBumperWasPressed()) {
                assert hardwareTypes.containsKey(entry);
                hardware = hardwareTypes.get(entry).get();
                assert hardware != null;
                break;
            }
        }

        telemetry.clearAll();
    }

    public Follower createFollower(HardwareMap hardwareMap) {
        if (!hasSelectedHardware()) {
            throw new IllegalStateException("Hardware not selected!");
        }

        return hardware.createFollower(hardwareMap);
    }
}
