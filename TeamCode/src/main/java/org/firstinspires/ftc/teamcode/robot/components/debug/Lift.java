package org.firstinspires.ftc.teamcode.robot.components.debug;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.PIDCoefficients;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.robot.robots.Robot;
import org.firstinspires.ftc.teamcode.robot.components.Component;

// Elevator lifts the stone and extender up
// Extender extends over the tower, and the grabber releases the stone

public class Lift extends Component {

    //// MOTORS ////
    private DcMotorEx lift_l;
    private DcMotorEx lift_r;

    private Servo ext_l;
    private Servo ext_r;

    private Servo grb_t;
    private Servo grb_g;

    public int level;

    private double cached_power = 0;

    private static final int BLOCK_HEIGHT = 400; //In encoder counts
    private static final int LIFT_OFFSET = 0;
    private static final int MAX_LEVEL = 17;
    private static final int MIN_LEVEL = 0;

    private static final double GRABBER_CLOSED = 1;
    private static final double GRABBER_OPEN = 0.25;

    static final PIDCoefficients PID_COEFFS = new PIDCoefficients(5, 1, 0);

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
        lift_l     = hwmap.get(DcMotorEx.class, "lift_l");
        lift_r     = hwmap.get(DcMotorEx.class, "lift_r");

        //// SERVOS ////
        ext_l     = hwmap.get(Servo.class, "ext_l");
        ext_r     = hwmap.get(Servo.class, "ext_r");

        grb_t   = hwmap.get(Servo.class, "grb_t");
        grb_g   = hwmap.get(Servo.class, "grb_g");

    }

    @Override
    public void update(OpMode opmode) {
        super.update(opmode);

        if (cached_power != 0) {
            if (!lift_l.isBusy() && !lift_r.isBusy()) {
                set_power(0);
                lift_l.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                lift_r.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            }
        }
    }

    @Override
    public void startup() {
        super.startup();

        lift_l.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        lift_r.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);


        lift_l.setPIDCoefficients(DcMotor.RunMode.RUN_TO_POSITION, PID_COEFFS);
        lift_r.setPIDCoefficients(DcMotor.RunMode.RUN_TO_POSITION, PID_COEFFS);

        lift_r.setDirection(DcMotorSimple.Direction.REVERSE);

        lift_l.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        lift_r.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        lift_l.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        lift_r.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        elevate(0);

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

        telemetry.addData("LIFT BUSY",lift_l.isBusy()+" "+lift_r.isBusy());

        telemetry.addData("LE POS",TELEMETRY_DECIMAL.format(ext_l.getPosition()));
        telemetry.addData("RE POS",TELEMETRY_DECIMAL.format(ext_r.getPosition()));

        telemetry.addData("GT POS",TELEMETRY_DECIMAL.format(grb_t.getPosition()));
        telemetry.addData("GG POS",TELEMETRY_DECIMAL.format(grb_g.getPosition()));

        telemetry.addData("LEVEL", level);
    }

    public void set_power(double speed) {
        lift_l.setPower(speed);
        lift_r.setPower(speed);
        cached_power = speed;
    }

    private void set_target_position(int pos) {
        lift_l.setTargetPosition(pos);
        lift_r.setTargetPosition(pos);
    }

    public void elevate(int amt) {
        level = Math.max(Math.min(level+amt, MAX_LEVEL), MIN_LEVEL);
        set_target_position((level*BLOCK_HEIGHT)+LIFT_OFFSET);

        lift_l.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        lift_r.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        set_power(1);
    }

    public void min_lift() {
        elevate(MIN_LEVEL-level);
    }

    public void max_lift() {
        elevate(MAX_LEVEL-level);
    }


    public void elevate_without_stops(int amt) {
        level = level+amt;
        set_target_position((level*BLOCK_HEIGHT)+LIFT_OFFSET);

        lift_l.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        lift_r.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        set_power(1);
    }

    public void extend(int dir) {
        ext_l.setPosition(dir == 0 ? 0.5 : (dir == 1 ? 1 : 0));
        ext_r.setPosition(dir == 0 ? 0.5 : (dir == 1 ? 0 : 1));
    }

    public void grab() {
        grb_g.setPosition(GRABBER_CLOSED);
    }

    public void release() {
        grb_g.setPosition(GRABBER_OPEN);
    }

    public void turn(double pos) {
        grb_t.setPosition(pos);
    }
}
