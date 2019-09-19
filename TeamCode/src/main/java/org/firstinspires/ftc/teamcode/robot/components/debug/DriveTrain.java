package org.firstinspires.ftc.teamcode.robot.components.debug;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.robot.Robot;
import org.firstinspires.ftc.teamcode.robot.components.Component;

import java.lang.reflect.Array;
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
        imu         = hwmap.get(BNO055IMU.class, "drive_imu");
    }

    @Override
    protected void updateTelemetry(Telemetry telemetry) {
        statusString = "LF: "+drive_lf.getPower()+" | RF: "+drive_rf.getPower()+" | LB: "+drive_lb.getPower()+" | RB: "+drive_rb.getPower();
        super.updateTelemetry(telemetry);
    }

    @Override
    public void startup() {
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
        mechanumDrive(opmode.gamepad1.left_stick_x, opmode.gamepad1.left_stick_y, opmode.gamepad2.right_stick_x);
    }

    public void mechanumDrive(double lx, double ly, double rx) {
        double r = Math.hypot(lx, ly);

        double angle = Math.atan2(ly, lx) - Math.PI / 4;

        drive_lf.setPower(r * Math.cos(angle) + rx);
        drive_rf.setPower(r * Math.sin(angle) - rx);
        drive_lb.setPower(r * Math.sin(angle) + rx);
        drive_rb.setPower(r * Math.cos(angle) - rx);
    }

    public void stop() {
        drive_lf.setPower(0.0);
        drive_rf.setPower(0.0);
        drive_lb.setPower(0.0);
        drive_rb.setPower(0.0);
    }
}
