package org.firstinspires.ftc.teamcode.OpModes;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.firstinspires.ftc.teamcode.New2026.Robot.Robot;
import java.util.HashMap;
import java.util.Map;

@TeleOp(name="Testing")
public class TestTeleOp extends LinearOpMode {
    private Map<String, Action> runningActions = new HashMap<>();
    private FtcDashboard dashboard;

    @Override
    public void runOpMode() {
        dashboard = FtcDashboard.getInstance();
        Robot robot = new Robot(hardwareMap);

        waitForStart();

        while (opModeIsActive()) {
            TelemetryPacket packet = new TelemetryPacket();

            /**
             * DRIVETRAIN
             * FIX: Store the action into the map so your loop below executes it.
             */
            runningActions.put("DRIVETRAIN", robot.manualDrive(gamepad1.left_stick_x, gamepad1.left_stick_y, gamepad1.right_stick_x));

            /**
             * TRANSFER
             * FIX: Store the action into the map based on condition.
             */
            if (gamepad1.left_trigger > 0.15) {
                runningActions.put("TRANSFER", robot.setTransfer(1.0, 1.0));
            } else {
                runningActions.put("TRANSFER", robot.setTransfer(0.0, 0.0));
            }

            /**
             * SHOOTER
             * FIX: Store the action into the map based on condition.
             */
            if (gamepad1.right_trigger > 0.15) {
                runningActions.put("SHOOTER", robot.setShooterMotors(250));
            } else {
                runningActions.put("SHOOTER", robot.setShooterMotors(0));
            }

            HashMap<String, Action> newActions = new HashMap<>();
            for (Map.Entry<String, Action> entry : runningActions.entrySet()) {
                if (entry.getValue().run(packet)) {
                    newActions.put(entry.getKey(), entry.getValue());
                }
            }
            runningActions = newActions;
            dashboard.sendTelemetryPacket(packet);
            telemetry.update();
        }
    }
}
