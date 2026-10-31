package org.firstinspires.ftc.teamcode.New2026.Robot;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.ParallelAction;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.SleepAction;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Mechanisms.Drivetrain.Drivetrain;
import org.firstinspires.ftc.teamcode.Mechanisms.Drivetrain.Geometry.Path;
import org.firstinspires.ftc.teamcode.Mechanisms.HardStop.Hardstop;
import org.firstinspires.ftc.teamcode.Mechanisms.Shooter.ShooterMotors;
import org.firstinspires.ftc.teamcode.Mechanisms.Transfer.Transfer;
import org.firstinspires.ftc.teamcode.Utility.Sensors.Battery;


public class Robot {

    private final ShooterMotors shooterMotors;
    private final Transfer transfer;

    public final Drivetrain drivetrain;
    private final Hardstop hardstop;
    public final double transferSpeed = 0.7;
    public final double shootTime = 1;
    public final double shooterVelocity = 235;
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
    public Action setTransfer(double p1) {
        return new Action() {
            @Override
            public boolean run(@NonNull TelemetryPacket packet) {
                transfer.setTransferPower(p1, p1);
                return false;
            }
        };
    }
    public Action stopTransfer(){
        return new Action() {
            @Override
            public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            transfer.setTransferPower(0,0);
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

    public Action openHardStop(){
        return new Action() {
            @Override
            public boolean run(@NonNull TelemetryPacket telemetryPacket) {
                hardstop.openHardStop();
                return false;
            }
        };
    }
    public Action closeHardStop(){
        return new Action() {
            @Override
            public boolean run(@NonNull TelemetryPacket telemetryPacket) {
                hardstop.closeHardStop();
                return false;
            }
        };
    }


    public double getShooterSpeed(){
        return shooterMotors.getVelocity();
    }

    // TODO ------ AUTON ------

    public Action intakePathTimed(Path path,
                                  double seconds,
                                  double distanceThreshold,
                                  double angleThreshold,
                                  double maxSpeed, boolean useStoppingDistance, double intakePower){
        return new Action() {
            private ElapsedTime timer = null;
            @Override
            public boolean run(@NonNull TelemetryPacket telemetryPacket) {
                if(timer == null){
                    timer = new ElapsedTime();
                }
                if(timer.seconds() > seconds){
                    setTransfer(intakePower);
                    drivetrain.stopMotors();
                    return false;
                }
                setTransfer(intakePower);
                drivetrain.followPathFunction(path, maxSpeed, distanceThreshold, angleThreshold, useStoppingDistance);
                return true;
            }
        };
    }

    public SequentialAction shootPath(Path path,
                                  double seconds,
                                  double distanceThreshold,
                                  double angleThreshold,
                                  double maxSpeed, boolean useStoppingDistance, double shooterVelocity, double shootTime){
        return new SequentialAction(
                new ParallelAction(
                closeHardStop(),
                drivetrain.followPathTimed(path, seconds, distanceThreshold, angleThreshold, maxSpeed, useStoppingDistance),
                setShooterMotors(shooterVelocity)),
                shoot(shootTime),
                stopShooterMotors());
    }
    public SequentialAction shoot(double time){
       return new SequentialAction(openHardStop(),
               new SleepAction(0.05),
               setTransfer(transferSpeed),
               new SleepAction(time),
               stopTransfer());
    }
}
