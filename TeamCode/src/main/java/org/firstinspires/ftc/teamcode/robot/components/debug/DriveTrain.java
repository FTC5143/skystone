package org.firstinspires.ftc.teamcode.robot.components.debug;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.robot.robots.Robot;
import org.firstinspires.ftc.teamcode.robot.components.Component;

import java.util.ArrayList;

// Drive Train component
// Includes: Drive Motors, IMU

public class DriveTrain extends Component {

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

        telemetry.addData("LF: ",TELEMETRY_DECIMAL.format(drive_lf.getPower()));
        telemetry.addData("RF: ",TELEMETRY_DECIMAL.format(drive_rf.getPower()));
        telemetry.addData("LB: ",TELEMETRY_DECIMAL.format(drive_lb.getPower()));
        telemetry.addData("RB: ",TELEMETRY_DECIMAL.format(drive_rb.getPower()));
        telemetry.addData(" IMU: ",imu.getAngularOrientation().toString()+", "+imu.getPosition().toString()+", "+imu.isGyroCalibrated());
    }

    @Override
    public void startup() {
        super.startup();

        drive_lf.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        drive_rf.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        drive_lb.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        drive_rb.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
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

        if (opmode.gamepad1.x) {drive_lf.setPower(1.0);}
        if (opmode.gamepad1.y) {drive_rf.setPower(1.0);}
        if (opmode.gamepad1.a) {drive_lb.setPower(1.0);}
        if (opmode.gamepad1.b) {drive_rb.setPower(1.0);}
    }

    public void mechanumDrive(double lx, double ly, double rx) {
        double l_diag =  lx - ly + rx;
        double r_diag = -lx - ly + rx;

        // I hate myself for writing it out like this but I also was too lazy to figure out a better way
        drive_lf.setPower(-(lx - ly + rx));
        drive_rf.setPower(-lx - ly - rx);
        drive_lb.setPower(-(-lx - ly + rx));
        drive_rb.setPower(lx - ly - rx);
    }

    public void stop() {
        drive_lf.setPower(0.0);
        drive_rf.setPower(0.0);
        drive_lb.setPower(0.0);
        drive_rb.setPower(0.0);
    }
}
