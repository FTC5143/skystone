package org.firstinspires.ftc.teamcode.robot.components.debug;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.robot.robots.Robot;
import org.firstinspires.ftc.teamcode.robot.components.Component;

// Elevator lifts the stone and extender up
// Extender extends over the tower, and the grabber releases the stone

public class Lift extends Component {
    
    //// MOTORS ////
    private DcMotor lift_l;
    private DcMotor lift_r;

    private int level;

    private static final int BLOCK_HEIGHT = 50; //In encoder counts
    private static final int LIFT_OFFSET = 0;
    private static final int MAX_LEVEL = 60;
    private static final int MIN_LEVEL = -60;


    {
        name = "Lift";
    }

    public Lift(Robot robot)
    {
        super(robot);
    }

    @Override
    public void registerHardware (HardwareMap hwmap)
    {
        super.registerHardware(hwmap);

        //// MOTORS ////
        lift_l     = hwmap.get(DcMotor.class, "lift_l");
        lift_r     = hwmap.get(DcMotor.class, "lift_r");

    }

    @Override
    public void startup() {
        super.startup();

        lift_l.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        lift_r.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        lift_r.setDirection(DcMotorSimple.Direction.REVERSE);

        elevate(0);
        lift_l.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        lift_r.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        lift_l.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        lift_r.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        set_power(1);


    }

    public void shutdown() {
        set_power(0);
        lift_l.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        lift_r.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

    }

    @Override
    protected void updateTelemetry(Telemetry telemetry) {
        super.updateTelemetry(telemetry);

        telemetry.addData("LL SPEED",TELEMETRY_DECIMAL.format(lift_l.getPower()));
        telemetry.addData("RL SPEED",TELEMETRY_DECIMAL.format(lift_r.getPower()));

        telemetry.addData("LL TURNS",TELEMETRY_DECIMAL.format(lift_l.getCurrentPosition()));
        telemetry.addData("RL TURNS",TELEMETRY_DECIMAL.format(lift_r.getCurrentPosition()));


        telemetry.addData("LL TARGET",TELEMETRY_DECIMAL.format(lift_l.getTargetPosition()));
        telemetry.addData("RL TARGET",TELEMETRY_DECIMAL.format(lift_r.getTargetPosition()));
    }

    public void set_power(double speed) {
        lift_l.setPower(speed);
        lift_r.setPower(speed);
    }

    private void set_target_position(int pos) {
        lift_l.setTargetPosition(pos);
        lift_r.setTargetPosition(pos);
    }

    public void elevate(int amt) {
        level = Math.max(Math.min(level+amt, MAX_LEVEL), MIN_LEVEL);
        set_target_position((level*BLOCK_HEIGHT)+LIFT_OFFSET);
    }

}
