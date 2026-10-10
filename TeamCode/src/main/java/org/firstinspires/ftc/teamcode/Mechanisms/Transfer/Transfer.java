package org.firstinspires.ftc.teamcode.Mechanisms.Transfer;

import org.firstinspires.ftc.teamcode.Utility.Actuators.DcMotorAdvanced;
import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

@Config
public class Transfer {
    public final DcMotorAdvanced intake;
    public final DcMotorAdvanced indexer;

    /**
     * Constructs a new Shooter mechanism and initializes its motor controller.
     *
     * @param hardwareMap the FTC HardwareMap used to retrieve motor hardware
     */
    public Transfer(HardwareMap hardwareMap) {
        this.intake = new DcMotorAdvanced(
                hardwareMap.get(DcMotorEx.class, "i1")
        );
        this.indexer = new DcMotorAdvanced(
                hardwareMap.get(DcMotorEx.class, "i2")
        );
        this.intake.setDirection(DcMotorSimple.Direction.FORWARD);
        this.indexer.setDirection(DcMotorSimple.Direction.REVERSE);
    }
    public void setTransferPower(double p1, double p2){
        setIntakePower(p1);
        setIndexerPower(p2);
    }
    public void setIntakePower(double power){
        intake.setPower(power);
    }
    public void setIndexerPower(double power){
        indexer.setPower(power);
    }



}