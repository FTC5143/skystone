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

import static org.firstinspires.ftc.teamcode.components.live.Lift.LiftConfig.*;

// Elevator lifts the stone and extender up
// Extender extends over the tower, and the grabber releases the stone

public class Lift extends Component {

    //// MOTORS ////
    private DcMotorEx lift_l;
    private DcMotorEx lift_r;

    private Servo ext;      // Servo that runs the linkage horizontal slide extension
    private Servo grb_t;    // Servo that turns the stone gripper
    private Servo grb_g;    // Servo that grips the stone
    private Servo capstone; // Servo that drops the capstone on the stone

    //// SENSORS ////
    public RevTouchSensor block_detector; // Used to detect when a stone has entered the intake

    public int level;

    private double cached_power = 0;

    private int grabber_turn = 0;

    private boolean starting_move = false;
    private int lift_l_target = 0;
    private int lift_r_target = 0;

    // Variables for servo position caches. Servo values are cahced and written on the next loop update. This is to prevent the control thread figthing for lock access with the update thread
    static double ext_pos = 0;
    static double ext_pos_cache = 0;

    static double grab_pos = 0;
    static double grab_pos_cache = 0;

    static double turn_pos = 0;
    static double turn_pos_cache = 0;

    static double cap_pos = 0;
    static double cap_pos_cache = 0;

    @Config
    static class LiftConfig {

        static int BLOCK_HEIGHT = 640; // How many encoder counts we move up for each level
        static int LIFT_OFFSET = 0;
      
        static int MAX_LEVEL = 43; // The maximum level of the lift, we cannot lift higher than this
        static int MIN_LEVEL = 0; // The minimum level of the lift, we cannot retract lower than this

        static double GRABBER_CLOSED = 1;
        static double GRABBER_OPEN = 0.33;

        // The PID coeffs for the lift motors
        static double PID_P = 5;
        static double PID_I = 1;
        static double PID_D = 0;
        static final PIDCoefficients PID_COEFFS = new PIDCoefficients(PID_P, PID_I, PID_D);

        // The up and down positions of the capstone servo; up means the capstone is dropped
        static double CAPSTONE_UP = 0.9;
        static double CAPSTONE_DOWN = 0.7;

        // The in and out positions of the extension servo; out means the extension is fully extended
        static double EXTENSION_OUT = 0.20;
        static double EXTENSION_IN = 0.7518;

        // How close we need to be to the target position to cut power to the lift
        static double LIFT_ENCODER_TOLERANCE = 5;

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

        capstone = hwmap.get(Servo.class, "capstone");

        grb_t   = hwmap.get(Servo.class, "grb_t");
        grb_g   = hwmap.get(Servo.class, "grb_g");

        //// SENSORS ////
        block_detector = hwmap.get(RevTouchSensor.class, "block_detector");

    }

    @Override
    public void update(OpMode opmode) {
        super.update(opmode);

        if (starting_move == true) {
            // When we are starting a move, set the new target positions of the motors. We have to do it like this because we only want writes in our update loop
            lift_l.setTargetPosition(lift_l_target);
            lift_r.setTargetPosition(lift_r_target);
            
            // Set the motors to run to position mode, and begin running the lift
            lift_l.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            lift_r.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            set_power(1);

            // Set this to false so we don't begin a move again until we are starting the next one
            starting_move = false;
        }

        // If the lift is running, we should be constantly checking if it has reached its target position
        if (cached_power != 0) {
            // If both motors are within the position tolerance, stop the lift
            if (Math.abs(robot.bulk_data_2.getMotorCurrentPosition(lift_l) - lift_l_target) <= LIFT_ENCODER_TOLERANCE && Math.abs(robot.bulk_data_2.getMotorCurrentPosition(lift_r) - lift_r_target) <= LIFT_ENCODER_TOLERANCE) {
                set_power(0);

                lift_l.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                lift_r.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

            }
        }

        // Servo caching stuff, we need this so we only update servos within the write loop. I should really just write a new servo class that handles this to be honest
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


        lift_l.setPIDCoefficients(DcMotor.RunMode.RUN_TO_POSITION, PID_COEFFS);
        lift_r.setPIDCoefficients(DcMotor.RunMode.RUN_TO_POSITION, PID_COEFFS);

        lift_r.setDirection(DcMotorSimple.Direction.REVERSE);

        lift_l.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        lift_r.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        lift_l.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        lift_r.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        // Upon initialization we reset all servos to their default position
        uncap();
        release();
        retract();
        turn(-100);
        elevate(0);

    }

    public void shutdown() {
        // When the robot is stopped, we cut all power and stop all motors
        set_power(0);
        lift_l.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        lift_r.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }

    @Override
    public void updateTelemetry(Telemetry telemetry) {
        super.updateTelemetry(telemetry);

        telemetry.addData("LL TURNS",TELEMETRY_DECIMAL.format(robot.bulk_data_2.getMotorCurrentPosition(lift_l)));
        telemetry.addData("RL TURNS",TELEMETRY_DECIMAL.format(robot.bulk_data_2.getMotorCurrentPosition(lift_r)));


        //telemetry.addData("LL TARGET",TELEMETRY_DECIMAL.format(lift_l.getTargetPosition()));
        //telemetry.addData("RL TARGET",TELEMETRY_DECIMAL.format(lift_r.getTargetPosition()));

        telemetry.addData("LIFT BUSY",robot.bulk_data_2.isMotorAtTargetPosition(lift_l)+" "+robot.bulk_data_2.isMotorAtTargetPosition(lift_r));

        telemetry.addData("EXT POS",TELEMETRY_DECIMAL.format(ext.getPosition()));

        telemetry.addData("GT POS",TELEMETRY_DECIMAL.format(grb_t.getPosition()));
        telemetry.addData("GG POS",TELEMETRY_DECIMAL.format(grb_g.getPosition()));

        telemetry.addData("LEVEL", level);

        telemetry.addData("BD", robot.bulk_data_1.getDigitalInputState(1));

    }

    public void set_power(double speed) {
        lift_l.setPower(speed);
        lift_r.setPower(speed);
        cached_power = speed;
    }

    private void set_target_position(int pos) {
        lift_l_target = pos;
        lift_r_target = pos;
    }
    
    // Change the height of the lift by amt levels
    public void elevate(int amt) {
        level = Math.max(Math.min(level + amt, MAX_LEVEL), MIN_LEVEL);
        set_target_position((level * BLOCK_HEIGHT) + LIFT_OFFSET);
        starting_move = true;
    }

    // Bring the lift to its minimum level
    public void min_lift() {
        elevate(MIN_LEVEL - level);
    }

    // Bring the lift to its maximum level
    public void max_lift() {
        elevate(MAX_LEVEL - level);
    }

    // Elevate the lift without worrying about encoder stops, used for encoder reset stuff
    public void elevate_without_stops(int amt) {
        level = level + amt;
        set_target_position((level * BLOCK_HEIGHT) + LIFT_OFFSET);
        starting_move = true;
    }
    
    // Retract the extension
    public void retract() {
        ext_pos = EXTENSION_IN;
    }

    // Extend the extension
    public void extend() {
        ext_pos = EXTENSION_OUT;
    }

    // Close the gripper
    public void grab() {
        grab_pos = GRABBER_CLOSED;
    }

    // Open the gripper
    public void release() {
        grab_pos = GRABBER_OPEN;
    }

    // Move the capstone servo to its default position
    public void uncap() {
        cap_pos = CAPSTONE_UP;
    }
    
    // Drop the capstone
    public void cap() {
        cap_pos = CAPSTONE_DOWN;
    }

    // Turn the gripper servo by an amount
    public void turn(int direction) {
        grabber_turn = Range.clip(grabber_turn+direction, 0, 2);
        turn_pos = (0.995-(grabber_turn*0.33333));
    }
}