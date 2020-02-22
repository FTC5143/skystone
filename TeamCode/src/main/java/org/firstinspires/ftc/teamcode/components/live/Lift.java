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
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.robots.Robot;
import org.firstinspires.ftc.teamcode.components.Component;

import static org.firstinspires.ftc.teamcode.components.live.LiftConfig.*;

// Elevator lifts the stone and extender up
// Extender extends over the tower, and the grabber releases the stone


@Config
class LiftConfig {

    public static int BLOCK_HEIGHT = 72; //In encoder counts
    public static int LIFT_OFFSET = 0;
    public static int MAX_LEVEL = 39;
    public static int MIN_LEVEL = 0;

    public static double GRABBER_CLOSED = 1;
    public static double GRABBER_OPEN = 0.33;

    public static double PID_P = 15;
    public static double PID_I = 0;
    public static double PID_D = 3;

    public static double CAPSTONE_UP = 0.9;
    public static double CAPSTONE_DOWN = 0.7;

    public static double EXTENSION_OUT = 0.20;
    public static double EXTENSION_IN = 0.7698;

    public static int LIFT_DOWN_OVERSHOOT = 100;

}

public class Lift extends Component {

    //// MOTORS ////
    public DcMotorEx lift_l;
    public DcMotorEx lift_r;

    private Servo ext;
    private Servo grb_t;
    private Servo grb_g;
    private Servo capstone;

    //// SENSORS ////

    public RevTouchSensor block_detector;


    public int level;

    private int grabber_turn = 0;

    private boolean starting_move = false;

    public int lift_l_target = 0;
    public int lift_r_target = 0;

    public int lift_l_offset = 0;
    public int lift_r_offset = 0;

    static double ext_pos = 0;
    static double ext_pos_cache = 0;

    static double grab_pos = 0;
    static double grab_pos_cache = 0;

    static double turn_pos = 0;
    static double turn_pos_cache = 0;

    static double cap_pos = 0;
    static double cap_pos_cache = 0;



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

        capstone = hwmap.get(Servo.class, "capstone");

        grb_t   = hwmap.get(Servo.class, "grb_t");
        grb_g   = hwmap.get(Servo.class, "grb_g");

        //// SENSORS ////
        block_detector = hwmap.get(RevTouchSensor.class, "block_detector");

    }

    @Override
    public void update(OpMode opmode) {
        super.update(opmode);

        if (starting_move) {

            if (level == 0) {
                if(robot.bulk_data_1.getDigitalInputState(3) && robot.bulk_data_1.getDigitalInputState(5)) {
                    lift_l.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                    lift_r.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                    set_power(-1);
                }
            } else {
                lift_l.setTargetPosition(lift_l_target+lift_l_offset);
                lift_r.setTargetPosition(lift_r_target+lift_r_offset);
                lift_l.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                lift_r.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                set_power(1);
            }

            starting_move = false;
        }

        if (level == 0 && (lift_l.getPower() == -1 || lift_r.getPower() == -1)) {
            if ((!robot.bulk_data_1.getDigitalInputState(5)) && (lift_l.getPower() != 0)) {
                lift_l_offset = robot.bulk_data_2.getMotorCurrentPosition(lift_l);
                lift_l.setPower(0);
            }
            if ((!robot.bulk_data_1.getDigitalInputState(3)) && (lift_r.getPower() != 0)) {
                lift_r_offset = robot.bulk_data_2.getMotorCurrentPosition(lift_r);
                lift_r.setPower(0);
            }
        }


        if(ext_pos != ext_pos_cache) {
            ext.setPosition(ext_pos);
            ext_pos = ext_pos_cache;
        }

        if(grab_pos != grab_pos_cache) {
            grb_g.setPosition(grab_pos);
            grab_pos = grab_pos_cache;
        }

        if(turn_pos != turn_pos_cache) {
            grb_t.setPosition(turn_pos);
            turn_pos = turn_pos_cache;
        }

        if(cap_pos != cap_pos_cache) {
            capstone.setPosition(cap_pos);
            cap_pos = cap_pos_cache;
        }

    }

    @Override
    public void startup() {
        super.startup();

        lift_l.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        lift_r.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        PIDCoefficients pid_coeffs = new PIDCoefficients(PID_P, PID_I, PID_D);

        lift_l.setPIDCoefficients(DcMotor.RunMode.RUN_TO_POSITION, pid_coeffs);
        lift_r.setPIDCoefficients(DcMotor.RunMode.RUN_TO_POSITION, pid_coeffs);

        lift_r.setDirection(DcMotorSimple.Direction.REVERSE);

        lift_l.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        lift_r.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        lift_l.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        lift_r.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        uncap();
        release();
        retract();
        turn(-100);

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

        telemetry.addData("LL TARGET",TELEMETRY_DECIMAL.format(lift_l_target));
        telemetry.addData("RL TARGET",TELEMETRY_DECIMAL.format(lift_r_target));

        telemetry.addData("LL OFFSET", TELEMETRY_DECIMAL.format(lift_l_offset));
        telemetry.addData("RL OFFSET", TELEMETRY_DECIMAL.format(lift_r_offset));

        telemetry.addData("LIFT BUSY",robot.bulk_data_2.isMotorAtTargetPosition(lift_l)+" "+robot.bulk_data_2.isMotorAtTargetPosition(lift_r));

        telemetry.addData("LIFT RUNNING", running_lift());

        telemetry.addData("EXT POS",TELEMETRY_DECIMAL.format(ext.getPosition()));

        telemetry.addData("GT POS",TELEMETRY_DECIMAL.format(grb_t.getPosition()));
        telemetry.addData("GG POS",TELEMETRY_DECIMAL.format(grb_g.getPosition()));

        telemetry.addData("LEVEL", level);

        telemetry.addData("BD", robot.bulk_data_1.getDigitalInputState(1));

    }

    public void set_power(double speed) {
        lift_l.setPower(speed);
        lift_r.setPower(speed);
    }

    public boolean running_lift() {
        return lift_l.getPower() != 0 || lift_r.getPower() != 0;
    }

    private void set_target_position(int pos) {
        lift_l_target = pos;
        lift_r_target = pos;
    }

    public void elevate(int amt) {
        elevate_to(level + amt);
    }

    public void elevate_to(int target) {
        level = Math.max(Math.min(target, MAX_LEVEL), MIN_LEVEL);
        set_target_position((level * BLOCK_HEIGHT) + LIFT_OFFSET);
        starting_move = true;
    }

    public void min_lift() {
        elevate(MIN_LEVEL - level);
    }

    public void max_lift() {
        elevate(MAX_LEVEL - level);
    }

    public void elevate_without_stops(int amt) {
        level = level + amt;
        set_target_position((level * BLOCK_HEIGHT) + LIFT_OFFSET);
        starting_move = true;
    }

    public void retract() {
        ext_pos = EXTENSION_IN;
    }

    public void extend() {
        ext_pos = EXTENSION_OUT;
    }

    public void grab() {
        grab_pos = GRABBER_CLOSED;
    }

    public void release() {
        grab_pos = GRABBER_OPEN;
    }

    public void uncap() {
        cap_pos = CAPSTONE_UP;
    }

    public void cap() {
        cap_pos = CAPSTONE_DOWN;
    }

    public void turn(int direction) {
        grabber_turn = Range.clip(grabber_turn+direction, 0, 2);
        turn_pos = (0.995-(grabber_turn*0.33333));
    }

    public boolean extended() {
        return ext_pos == EXTENSION_OUT;
    }
}