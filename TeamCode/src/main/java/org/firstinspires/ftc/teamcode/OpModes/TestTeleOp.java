package org.firstinspires.ftc.teamcode.OpModes;
import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.firstinspires.ftc.teamcode.New2026.Robot.Robot;
import java.util.HashMap;
import java.util.Map;

@TeleOp(name="Testing")
@Config
public class TestTeleOp extends LinearOpMode {
    public static double shooterSpeed = 235;
    public static double transferSpeed = 0.6  ;
    private Map<String, Action> run = new HashMap<>();
    private FtcDashboard dashboard;

    @Override
    public void runOpMode() {
        dashboard = FtcDashboard.getInstance();
        Robot robot = new Robot(hardwareMap);
        TelemetryPacket packet = new TelemetryPacket();

        packet.put("Velocity", 0);
        packet.put("Target Velocity", shooterSpeed);

        waitForStart();

        while (opModeIsActive()) {
            //TODO ----- DRIVETRAIN ------

            run.put("DRIVETRAIN", robot.manualDrive(gamepad1.left_stick_y, gamepad1.left_stick_x, gamepad1.right_stick_x));

            //TODO ----- SHOOTER -----

            if (gamepad1.right_trigger > 0.15) {
                run.put("SHOOTER", robot.setShooterMotors(shooterSpeed));
            } else {
                run.put("SHOOTER", robot.stopShooterMotors());
            }


            //TODO ----- TRANSFER -----

            if (gamepad1.left_trigger > 0.15) {
                run.put("TRANSFER", robot.setTransfer(transferSpeed));
            } else {
                run.put("TRANSFER", robot.setTransfer(0.0));
            }

            //TODO ----- TELEMETRY -----

            packet.put("Velocity", robot.getShooterSpeed());
            packet.put("Target Velocity", shooterSpeed);
            dashboard.sendTelemetryPacket(packet);
            telemetry.update();


            HashMap<String, Action> newActions = new HashMap<>();
            for (Map.Entry<String, Action> entry : run.entrySet()) {
                if (entry.getValue().run(packet)) {
                    newActions.put(entry.getKey(), entry.getValue());
                }
            }
            run = newActions;
        }
    }
}
