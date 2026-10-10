package org.firstinspires.ftc.teamcode.Mechanisms.Shooter;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@Config
@Autonomous(name = "TestShooterMotors")
public class ShooterMotorsTest extends LinearOpMode {
    public static double targetVelocity = 250; // Rad per Sec 2,387.32 RPM
    FtcDashboard dashboard;
    ShooterMotors shooter;
    double currentVelocity;
    @Override
    public void runOpMode() {
        dashboard = FtcDashboard.getInstance();
        shooter = new ShooterMotors(hardwareMap);
        TelemetryPacket packet = new TelemetryPacket();
        packet.put("Velocity", 0.0);
        packet.put("Target Velocity", targetVelocity);
        dashboard.sendTelemetryPacket(packet);

        waitForStart();

        while (opModeIsActive()) {
            shooter.setVelocityShooter(targetVelocity);
            currentVelocity = shooter.getVelocity();
            packet.put("Velocity", currentVelocity);
            packet.put("Target Velocity", targetVelocity);
            dashboard.sendTelemetryPacket(packet);

        }


    }


}

