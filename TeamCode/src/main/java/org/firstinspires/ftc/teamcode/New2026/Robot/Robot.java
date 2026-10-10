package org.firstinspires.ftc.teamcode.New2026.Robot;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.Mechanisms.Drivetrain.Drivetrain;
import org.firstinspires.ftc.teamcode.Mechanisms.Drivetrain.Geometry.Path;
import org.firstinspires.ftc.teamcode.Mechanisms.Shooter.ShooterMotors;
import org.firstinspires.ftc.teamcode.Mechanisms.Transfer.Transfer;
import org.firstinspires.ftc.teamcode.Utility.Sensors.Battery;


public class Robot {

    private final ShooterMotors shooterMotors;
    private final Transfer transfer;
    private Drivetrain drivetrain;
    Battery battery;
    public Robot(HardwareMap hardwareMap) {
        Battery.initialize(hardwareMap);
        battery = Battery.getInstance();
        shooterMotors = new ShooterMotors(hardwareMap);
        transfer = new Transfer(hardwareMap);
        drivetrain = new Drivetrain(hardwareMap);
    }



    public Action manualDrive(
            double lx, double ly, double ry
    ) {
        return new Action() {
            @Override
            public boolean run(@NonNull TelemetryPacket packet) {
                drivetrain.manualControl(lx, ly, ry);

                return false;
            }
        };

    }
    public Action setTransfer(
            double p1, double p2
    ) {
        return new Action() {
            @Override
            public boolean run(@NonNull TelemetryPacket packet) {
                transfer.setTransferPower(p1, p2);
                return false;
            }
        };

    }
    public Action setShooterMotors( double p){
        return new Action() {
            @Override
            public boolean run(@NonNull TelemetryPacket telemetryPacket) {
                shooterMotors.setVelocityShooter(p);
                return false;
            }
        };
    }

}
