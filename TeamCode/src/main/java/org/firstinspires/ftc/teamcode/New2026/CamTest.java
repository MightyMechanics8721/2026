//package org.firstinspires.ftc.teamcode.New2026;
//
//import com.qualcomm.hardware.limelightvision.LLResultTypes;
//import com.qualcomm.hardware.limelightvision.Limelight3A;
//
//import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
//import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
//
//import org.firstinspires.ftc.teamcode.Utility.Actuators.LimeLight;
//
//@TeleOp(name = "Limelight AprilTag Test", group = "Vision")
//public class CamTest extends LinearOpMode {
//
//    @Override
//    public void runOpMode() throws InterruptedException {
//
//        Limelight3A limelight =
//                hardwareMap.get(Limelight3A.class, "limelight");
//
//        LimeLight aprilTag =
//                new LimeLight(limelight, 0);
//
//
//        aprilTag.start();
//
//        telemetry.update();
//
//        waitForStart();
//
//        try {
//            while (opModeIsActive()) {
//
//                aprilTag.update();
//
//                if (!aprilTag.hasTarget()) {
//                    telemetry.addLine("No AprilTags detected");
//                } else {
//                    telemetry.addData(
//                            "Detected IDs",
//                            aprilTag.getDetectedTagIds()
//                    );
//
//                    for (LLResultTypes.FiducialResult tag
//                            : aprilTag.getDetectedTags()) {
//
//                        int id = tag.getFiducialId();
//
//                        telemetry.addData(
//                                "Tag ID",
//                                id
//                        );
//
//                        telemetry.addData(
//                                "Tag " + id + " Distance (in)",
//                                "%.2f",
//                                aprilTag.getDistanceInches(id)
//                        );
//
//                        telemetry.addData(
//                                "Tag " + id + " TX (deg)",
//                                "%.2f",
//                                aprilTag.getTx(id)
//                        );
//
//                        telemetry.addData(
//                                "Tag " + id + " TY (deg)",
//                                "%.2f",
//                                aprilTag.getTy(id)
//                        );
//                    }
//                }
//
//                telemetry.update();
//
//                sleep(20);
//            }
//        } finally {
//            aprilTag.stop();
//        }
//    }
//}