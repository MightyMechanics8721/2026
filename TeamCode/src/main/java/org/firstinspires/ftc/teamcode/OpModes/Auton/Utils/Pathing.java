package org.firstinspires.ftc.teamcode.OpModes.Auton.Utils;

import org.firstinspires.ftc.teamcode.Mechanisms.Drivetrain.Geometry.Path;

public class Pathing {
    private final int side;
    public final double botWidth = 388.7 / 25.4 ; // mm -> in
    public final double botLength = 425.175 / 25.4 ; // mm -> in
    private final double[][] shootPreloadD = {{-12, 64}, {-12, 60}, {64, 12}};
    private final double[][] intakeD = {{64, 12}, {66, 66}};
    private final double[][] shootIntakeD = {{66, 66}, {64, 12}};
    private final double[][] parkD = {{64, 12}, {48, 64}};

    public Pathing(boolean isRed){
        if(isRed){
            side = 1;
        } else{
            side = -1;
        }
    }
    public Path preload = new Path(sideCalculate(shootPreloadD), 0);
    public Path intake = new Path(sideCalculate(intakeD), -90);
    public Path shootIntake = new Path(sideCalculate(shootIntakeD), 0);
    public Path park = new Path(sideCalculate(parkD), 0);

    private double[][] sideCalculate(double[][] x){
        for(int i = 0; i < x.length; i++){
            x[i][1] = x[i][1]*side;
        }
        return x;
    }
}
