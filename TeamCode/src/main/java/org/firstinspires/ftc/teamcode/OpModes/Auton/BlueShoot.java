package org.firstinspires.ftc.teamcode.OpModes.Auton;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.New2026.Robot.Robot;
import org.firstinspires.ftc.teamcode.OpModes.Auton.Utils.Pathing;

public class BlueShoot extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        Robot robot = new Robot(hardwareMap);
        Pathing path = new Pathing(false);

        robot.drivetrain.setInitialPose(-12, 72-path.botLength/2, 90);

        waitForStart();

        robot.drivetrain.setInitialPose(-12, 72-path.botLength/2, 90);

        while(opModeIsActive()){

            robot.drivetrain.followPathTimed(path.preload, 2, 2, 1.5, 10000, true);
            robot.intakePathTimed(path.intake, 3, 2, 2, 10000, true, 1);
            robot.shootPath(path.shootIntake, 3, 2,2, 10000, true, robot.shooterVelocity, robot.shootTime);
            robot.drivetrain.followPathTimed(path.park, 3, 2, 2, 10000, true)
        }
    }
}
