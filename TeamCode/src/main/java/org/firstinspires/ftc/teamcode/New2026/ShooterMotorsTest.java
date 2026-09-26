package org.firstinspires.ftc.teamcode.New2026;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.CurrentUnit;
import org.firstinspires.ftc.teamcode.Hardware.Sensors.Battery;
import org.firstinspires.ftc.teamcode.Mechanisms.Shooter.Shooter;

@Config
@Autonomous(name = "aaaaaaa")
public class ShooterMotorsTest extends LinearOpMode {
    public static double targetVelocity = 250; // (RPM)
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

