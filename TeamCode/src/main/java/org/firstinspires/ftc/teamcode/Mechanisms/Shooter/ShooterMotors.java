package org.firstinspires.ftc.teamcode.Mechanisms.Shooter;
import org.firstinspires.ftc.teamcode.Utility.Actuators.DcMotorAdvanced;
import org.firstinspires.ftc.teamcode.Utility.Sensors.Encoder;
import org.firstinspires.ftc.teamcode.Utility.Utils.Controllers.Constants.FFConstants;
import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import org.firstinspires.ftc.teamcode.Utility.Utils.Controllers.Constants.PIDConstants;
import org.firstinspires.ftc.teamcode.Utility.Utils.Controllers.FeedForward;
import org.firstinspires.ftc.teamcode.Utility.Utils.Controllers.PID;

@Config
public class ShooterMotors {
    private final DcMotorAdvanced shooterMotor1;
    private final DcMotorAdvanced shooterMotor2;
    public final PID velocityPidController;
    public final FeedForward velocityFeedForwardController;
    public static FFConstants ffConstants = new FFConstants(0, 0.00215, 0);
    public static PIDConstants pidConstants = new PIDConstants(0.0325, 0, 0);
    private final Encoder encoder;

    /**
     * Constructs a new Shooter mechanism and initializes its motor controller.
     * @param hardwareMap the FTC HardwareMap used to retrieve motor hardware
     */
    public ShooterMotors(HardwareMap hardwareMap) {
        this.shooterMotor1 = new DcMotorAdvanced(
                hardwareMap.get(DcMotorEx.class, "f1")
        );


        this.shooterMotor2 = new DcMotorAdvanced(
                hardwareMap.get(DcMotorEx.class, "f2")
        );
        this.velocityPidController = new PID(
                pidConstants,
                PID.functionType.LINEAR
        );
        this.velocityFeedForwardController
                = new FeedForward(ffConstants);

        this.shooterMotor1.setDirection(DcMotorSimple.Direction.REVERSE);
        this.shooterMotor2.setDirection(DcMotorSimple.Direction.FORWARD);

        shooterMotor1.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        shooterMotor2.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);

        encoder = new Encoder(hardwareMap.get(DcMotorEx.class, "lfm"), 28);

    }

    /**
     * Returns the current shooter flywheel velocity.
     * @return the current velocity of the shooter flywheel (rad/sec)
     */
    public double getVelocity() {
        return Math.abs(encoder.getVelocity());
    }

    /** @param velocity rad/sec */
    public void setVelocityShooter(double velocity){
        double power = velocityPidController.calculate(velocity, getVelocity())
                + velocityFeedForwardController.calculate(velocity, 0);
        shooterMotor1.setPower(power);
        shooterMotor2.setPower(power);
    }

    public void stopShooter(){
        shooterMotor1.setPower(0);
        shooterMotor2.setPower(0);
    }



}