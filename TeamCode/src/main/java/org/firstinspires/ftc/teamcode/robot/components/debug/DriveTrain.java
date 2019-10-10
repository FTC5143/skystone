package org.firstinspires.ftc.teamcode.robot.components.debug;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.robot.robots.Robot;
import org.firstinspires.ftc.teamcode.robot.components.Component;

import java.util.ArrayList;

// Drive Train component
// Includes: Drive Motors, IMU

public class DriveTrain extends Component {


    final static double WHEEL_DIAMETER = 4.0;

    ArrayList<DcMotor> motors;

    //// MOTORS ////
    private DcMotor drive_lf;   // Left-Front drive motor
    private DcMotor drive_rf;   // Right-Front drive motor
    private DcMotor drive_lb;   // Left-Back drive motor
    private DcMotor drive_rb;   // Right-Back drive motor

    //// SENSORS ////
    private BNO055IMU imu;      // Internal REV IMU, which we might use to drive straight

    {
        name = "Drive Train";
    }

    public DriveTrain(Robot robot) {
        super(robot);
    }

    @Override
    public void registerHardware(HardwareMap hwmap) {
        super.registerHardware(hwmap);

        //// MOTORS ////
        drive_lf    = hwmap.get(DcMotor.class, "drive_lf");
        drive_rf    = hwmap.get(DcMotor.class, "drive_rf");
        drive_lb    = hwmap.get(DcMotor.class, "drive_lb");
        drive_rb    = hwmap.get(DcMotor.class, "drive_rb");

        //// SENSORS ////
        imu         = hwmap.get(BNO055IMU.class, "imu");
        imu.initialize(new BNO055IMU.Parameters());
    }

    @Override
    protected void updateTelemetry(Telemetry telemetry) {
        super.updateTelemetry(telemetry);

        telemetry.addData("LF SPEED",TELEMETRY_DECIMAL.format(drive_lf.getPower()));
        telemetry.addData("RF SPEED",TELEMETRY_DECIMAL.format(drive_rf.getPower()));
        telemetry.addData("LB SPEED",TELEMETRY_DECIMAL.format(drive_lb.getPower()));
        telemetry.addData("RB SPEED",TELEMETRY_DECIMAL.format(drive_rb.getPower()));

        telemetry.addData("LF TURNS",TELEMETRY_DECIMAL.format(drive_lf.getCurrentPosition()));
        telemetry.addData("RF TURNS",TELEMETRY_DECIMAL.format(drive_rf.getCurrentPosition()));
        telemetry.addData("LB TURNS",TELEMETRY_DECIMAL.format(drive_lb.getCurrentPosition()));
        telemetry.addData("RB TURNS",TELEMETRY_DECIMAL.format(drive_rb.getCurrentPosition()));

        telemetry.addData(" IMU: ",imu.getAngularOrientation().toString()+", "+imu.getPosition().toString()+", "+imu.isGyroCalibrated());
    }

    @Override
    public void startup() {
        super.startup();

        drive_lf.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        drive_rf.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        drive_lb.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        drive_rb.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        drive_lf.setDirection(DcMotor.Direction.REVERSE);
        drive_lb.setDirection(DcMotor.Direction.REVERSE);

        set_mode(DcMotor.RunMode.RUN_USING_ENCODER);

    }

    @Override
    public void shutdown() {
        super.shutdown();

        stop();
    }

    @Override
    public void update(OpMode opmode) {
        super.update(opmode);
        mechanumDrive(opmode.gamepad1.left_stick_x, opmode.gamepad1.left_stick_y, opmode.gamepad1.right_stick_x);

        int dir = opmode.gamepad1.back ? -1 : 1;
        if (opmode.gamepad1.x) {drive_lf.setPower(dir);}
        if (opmode.gamepad1.y) {drive_rf.setPower(dir);}
        if (opmode.gamepad1.a) {drive_lb.setPower(dir);}
        if (opmode.gamepad1.b) {drive_rb.setPower(dir);}
    }

    public void mechanumDrive(double lx, double ly, double rx) {

        // I hate myself for writing it out like this but I also was too lazy to figure out a better way\
        //don't worry I hate you too - Brain
        drive_lf.setPower(lx - ly + rx);
        drive_rf.setPower(-lx - ly - rx);
        drive_lb.setPower(-lx - ly + rx);
        drive_rb.setPower(lx - ly - rx);
    }

    public void stop() {
        set_power(0.0);
    }

    private void set_mode(DcMotor.RunMode mode) {
        drive_lf.setMode(mode);
        drive_rf.setMode(mode);
        drive_lb.setMode(mode);
        drive_rb.setMode(mode);
    }

    private void set_power(double speed) {
        drive_lf.setPower(speed);
        drive_rf.setPower(speed);
        drive_lb.setPower(speed);
        drive_rb.setPower(speed);
    }

    private boolean is_busy() {
        return drive_lf.isBusy() && drive_rf.isBusy() && drive_lb.isBusy() && drive_rb.isBusy();
    }

    public void encoder_drive(double x, double y, double a, double distance, double speed) {

        set_mode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        set_mode(DcMotor.RunMode.RUN_TO_POSITION);

        double lf =  (x - y + a) * distance;
        double rf = (-x - y - a) * distance;
        double lb = (-x - y + a) * distance;
        double rb =  (x - y - a) * distance;

        drive_lf.setTargetPosition((int)lf);
        drive_rf.setTargetPosition((int)rf);
        drive_lb.setTargetPosition((int)lb);
        drive_rb.setTargetPosition((int)rb);

        set_power(speed);

        while (is_busy()){}

        set_mode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        set_mode(DcMotor.RunMode.RUN_USING_ENCODER);
    }
}
