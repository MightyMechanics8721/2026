package org.firstinspires.ftc.teamcode.Mechanisms.HardStop;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.Utility.Actuators.ServoAdvanced;

public class Hardstop {
    private final ServoAdvanced servo;
    public double open = 1;
    public double close = 1;
    public Hardstop(HardwareMap hardwareMap){
        servo = new ServoAdvanced(hardwareMap.get(Servo.class, "hs"));
    }

    /** @param pos 0-1 */
    public void setHardStop(double pos){
        servo.setPosition(pos);
    }
    public void openHardStop(){
        servo.setPosition(open);
    }
    public void closeHardStop(){
        servo.setPosition(close);
    }

}
