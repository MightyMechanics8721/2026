package org.firstinspires.ftc.teamcode.New2026.Robot;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.Mechanisms.Drivetrain.Drivetrain;
import org.firstinspires.ftc.teamcode.Mechanisms.HardStop.Hardstop;
import org.firstinspires.ftc.teamcode.Mechanisms.Shooter.ShooterMotors;
import org.firstinspires.ftc.teamcode.Mechanisms.Transfer.Transfer;
import org.firstinspires.ftc.teamcode.Utility.Sensors.Battery;


public class Robot {

    private final ShooterMotors shooterMotors;
    private final Transfer transfer;
    private final Drivetrain drivetrain;
    private final Hardstop hardstop;
    Battery battery;
    public Robot(HardwareMap hardwareMap) {
        Battery.initialize(hardwareMap);
        battery = Battery.getInstance();
        shooterMotors = new ShooterMotors(hardwareMap);
        transfer = new Transfer(hardwareMap);
        drivetrain = new Drivetrain(hardwareMap);
        hardstop = new Hardstop(hardwareMap);
    }

    public Action manualDrive(
            double lx, double ly, double rx
    ) {
        return new Action() {
            @Override
            public boolean run(@NonNull TelemetryPacket packet) {
                drivetrain.manualControl(lx, ly, rx);

                return false;
            }
        };

    }
    public Action setTransfer(
            double p1
    ) {
        return new Action() {
            @Override
            public boolean run(@NonNull TelemetryPacket packet) {
                transfer.setTransferPower(p1, p1);
                return false;
            }
        };

    }
    public Action setShooterMotors( double p){
        return new Action() {
            @Override
            public boolean run(@NonNull TelemetryPacket telemetryPacket) {
                hardstop.setHardStop(hardstop.open);
                shooterMotors.setVelocityShooter(p);
                return false;
            }
        };
    }
    public Action stopShooterMotors(){
        return new Action() {
            @Override
            public boolean run(@NonNull TelemetryPacket telemetryPacket) {
                hardstop.setHardStop(hardstop.close);
                shooterMotors.stopShooter();
                return false;
            }
        };
    }

    public Action setHardStop(double p){
        return new Action() {
            @Override
            public boolean run(@NonNull TelemetryPacket telemetryPacket) {
                hardstop.setHardStop(p);
                return false;
            }
        };
    }


    public double getShooterSpeed(){
        return shooterMotors.getVelocity();
    }
}
