package org.firstinspires.ftc.teamcode.robot.components.debug;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.lynx.LynxModule;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.robot.robots.Robot;
import org.firstinspires.ftc.teamcode.robot.components.Component;
import org.firstinspires.ftc.teamcode.util.LynxOptimizedI2cFactory;

import java.util.ArrayList;

// Drive Train component
// Includes: Drive Motors, IMU
// I hate it also

public class DriveTrain extends Component {


    final static double WHEEL_DIAMETER = 4.0;

    //// MOTORS ////
    private DcMotor drive_lf;   // Left-Front drive motor
    private DcMotor drive_rf;   // Right-Front drive motor
    private DcMotor drive_lb;   // Left-Back drive motor
    private DcMotor drive_rb;   // Right-Back drive motor

    //// LYNX ////
    private LynxModule lynx_module;

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

        //// LYNX ////
        //lynx_module = hwmap.get(LynxModule.class, "Rev expansion hub 1");

        //// SENSORS ////
        //imu = LynxOptimizedI2cFactory.createLynxEmbeddedImu(lynx_module, 0);
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

        telemetry.addData("IMU",imu.getAngularOrientation().toString()+", "+imu.getPosition().toString()+", "+imu.isGyroCalibrated());
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
    }

    public void mechanumDrive(double lx, double ly, double rx) {

        // I hate myself for writing it out like this but I also was too lazy to figure out a better way
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

    private void set_power(double lf, double rf, double lb, double rb) {
        drive_lf.setPower(lf);
        drive_rf.setPower(rf);
        drive_lb.setPower(lb);
        drive_rb.setPower(rb);
    }

    private void set_power(double power) {
        set_power(power, power, power ,power);
    }

    private boolean is_busy() {
        return drive_lf.isBusy() || drive_rf.isBusy() || drive_lb.isBusy() || drive_rb.isBusy();
    }

    public void encoder_drive(double x, double y, double a, double distance, double speed) {

        set_mode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        set_mode(DcMotor.RunMode.RUN_TO_POSITION);

        double lf =  (x - y + a);
        double rf = (-x - y - a);
        double lb = (-x - y + a);
        double rb =  (x - y - a);

        drive_lf.setTargetPosition((int)(lf*distance));
        drive_rf.setTargetPosition((int)(rf*distance));
        drive_lb.setTargetPosition((int)(lb*distance));
        drive_rb.setTargetPosition((int)(rb*distance));

        set_power(lf, rf, lb, rb);

        while (is_busy() && robot.lopmode.opModeIsActive()){
            robot.lopmode.idle();
        }

        set_power(0);

        set_mode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        set_mode(DcMotor.RunMode.RUN_USING_ENCODER);
    }
}
