package org.firstinspires.ftc.teamcode.components.live;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.hardware.rev.RevTouchSensor;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.PIDCoefficients;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.robots.Robot;
import org.firstinspires.ftc.teamcode.components.Component;

import static org.firstinspires.ftc.teamcode.components.live.Lift.LiftConfig.*;

// Elevator lifts the stone and extender up
// Extender extends over the tower, and the grabber releases the stone

public class Lift extends Component {

    //// MOTORS ////
    private DcMotorEx lift_l;
    private DcMotorEx lift_r;

    private Servo ext;
    private DcMotorEx ext_encoder;

    private double ext_target;
    private double ext_old_pos;
    private double ext_error;
    private boolean ext_running;

    private Servo grb_t;
    private Servo grb_g;

    //// SENSORS ////

    RevTouchSensor block_detector;


    public int level;

    private double cached_power = 0;

    private boolean cached_grab = false;

    @Config
    static class LiftConfig {
        static int BLOCK_HEIGHT = 640; //In encoder counts
        static int LIFT_OFFSET = 0;
        static int MAX_LEVEL = 10;
        static int MIN_LEVEL = 0;

        static double GRABBER_CLOSED = 1;
        static double GRABBER_OPEN = 0.25;

        static double PID_P = 5;
        static double PID_I = 1;
        static double PID_D = 0;

        static final PIDCoefficients PID_COEFFS = new PIDCoefficients(PID_P, PID_I, PID_D);

        static double EXT_MIN_ACCURACY = 30;

    }

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
        ext     = hwmap.get(Servo.class, "ext");
        ext_encoder = hwmap.get(DcMotorEx.class, "right_spinner");

        grb_t   = hwmap.get(Servo.class, "grb_t");
        grb_g   = hwmap.get(Servo.class, "grb_g");

        //// SENSORS ////
        block_detector = hwmap.get(RevTouchSensor.class, "block_detector");

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

        if (!cached_grab && block_detector.isPressed()) {
            grab();
        }

        if (ext_running) {
            double position = ext_encoder.getCurrentPosition();

            if (Math.abs(position-ext_target) < EXT_MIN_ACCURACY) {
                ext_running = false;
                extend(0);
            } else {
                ext_error = ((ext_target - position)/(Math.abs(ext_target-ext_old_pos)))*2;
                extend(ext_error);
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

        ext_encoder.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        ext_encoder.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

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
    public void updateTelemetry(Telemetry telemetry) {
        super.updateTelemetry(telemetry);

        telemetry.addData("LL TURNS",TELEMETRY_DECIMAL.format(robot.bulk_data_2.getMotorCurrentPosition(lift_l)));
        telemetry.addData("RL TURNS",TELEMETRY_DECIMAL.format(robot.bulk_data_2.getMotorCurrentPosition(lift_r)));

        telemetry.addData("LL TARGET",TELEMETRY_DECIMAL.format(lift_l.getTargetPosition()));
        telemetry.addData("RL TARGET",TELEMETRY_DECIMAL.format(lift_r.getTargetPosition()));

        telemetry.addData("LIFT BUSY",lift_l.isBusy()+" "+lift_r.isBusy());

        telemetry.addData("EXT DIR",TELEMETRY_DECIMAL.format(ext.getPosition()));
        telemetry.addData("EXT POS",TELEMETRY_DECIMAL.format(robot.bulk_data_2.getMotorCurrentPosition(ext_encoder)));
        telemetry.addData("EXT OLD POS",ext_old_pos);
        telemetry.addData("EXT TARGET", ext_target);
        telemetry.addData("EXT ERROR", ext_error);

        telemetry.addData("GT POS",TELEMETRY_DECIMAL.format(grb_t.getPosition()));
        telemetry.addData("GG POS",TELEMETRY_DECIMAL.format(grb_g.getPosition()));

        telemetry.addData("LEVEL", level);

        telemetry.addData("BD", block_detector.isPressed());

        telemetry.addData("C GRAB", cached_grab);
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

    public void extend(double power) {

        power = Math.max(-1, Math.min(power, 1));

        ext.setPosition((((power)/2)+0.5));
    }

    public void extend_out() {
        if (ext_target != -5200 || !ext_running) {
            ext_target = -5200;
            ext_running = true;
            ext_old_pos = ext_encoder.getCurrentPosition();
        }
    }

    public void retract_in() {
        if (ext_target != 0 || !ext_running) {
            ext_target = 0;
            ext_running = true;
            ext_old_pos = ext_encoder.getCurrentPosition();
        }
    }

    public void grab() {
        grb_g.setPosition(GRABBER_CLOSED);
        cached_grab = true;
    }

    public void release() {
        grb_g.setPosition(GRABBER_OPEN);
        cached_grab = false;
    }

    public void turn(double pos) {
        grb_t.setPosition(pos);
    }
}
