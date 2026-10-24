package org.firstinspires.ftc.teamcode.New2026;

import com.qualcomm.hardware.limelightvision.LLResultTypes;
import com.qualcomm.hardware.limelightvision.Limelight3A;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.navigation.Pose3D;
import org.firstinspires.ftc.teamcode.Utility.Actuators.LimeLight;

@TeleOp(name = "Limelight AprilTag Test", group = "Vision")
public class CamTest extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {

        LimeLight aprilTag =
                new LimeLight(hardwareMap);


        aprilTag.start();
        telemetry.update();

        waitForStart();

            while (opModeIsActive()) {

                Pose3D botPose = aprilTag.getBotPoseOnField();

                aprilTag.update();

                if (botPose != null) {
                    telemetry.addLine("--- Robot Field Pose ---");
                    telemetry.addData("Field X (inches)", "%.2f", botPose.getPosition().x * 39.3701);
                    telemetry.addData("Field Y (inches)", "%.2f", botPose.getPosition().y * 39.3701);
                    telemetry.addData("Field Yaw (deg)", "%.2f", botPose.getOrientation().getYaw());
                }
                    telemetry.addData(
                            "Detected IDs",
                            aprilTag.getDetectedTagIds()
                    );

                    for (LLResultTypes.FiducialResult tag
                            : aprilTag.getDetectedTags()) {

                        int id = tag.getFiducialId();

                        telemetry.addData("Tag ID", id);
                        telemetry.addData("Tag " + id + " Distance (in)", "%.2f", aprilTag.getDistanceInches(id));
                        telemetry.addData("Tag " + id + " TX (deg)", "%.2f", aprilTag.getTx(id));
                        telemetry.addData("Tag " + id + " TY (deg)", "%.2f", aprilTag.getTy(id));
                    }
                telemetry.update();
            }

            aprilTag.stop();

    }
}