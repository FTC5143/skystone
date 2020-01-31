package org.firstinspires.ftc.teamcode.components.live;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.components.Component;
import org.firstinspires.ftc.teamcode.robots.Robot;
import org.firstinspires.ftc.teamcode.systems.LocalCoordinateSystem;

import static org.firstinspires.ftc.teamcode.components.live.DriveTrain.DriveTrainConfig.DEBUG_WAIT;
import static org.firstinspires.ftc.teamcode.components.live.DriveTrain.DriveTrainConfig.INCHES_PER_ROTATION;
import static org.firstinspires.ftc.teamcode.components.live.DriveTrain.DriveTrainConfig.TICKS_PER_INCH;
import static org.firstinspires.ftc.teamcode.constants.AutonomousConst.RED;

// Drive Train component
// Includes: Drive Motors, IMU
// I hate it also

public class DriveTrain extends Component {

    @Config
    static class DriveTrainConfig {
        static double WHEEL_DIAMETER = 4.0;
        static double TICKS_PER_REVOLUTION = 1120;
        static double WHEEL_CIRCUMFERENCE = WHEEL_DIAMETER * Math.PI;
        static double TICKS_PER_INCH = TICKS_PER_REVOLUTION / WHEEL_CIRCUMFERENCE;

        static double TICKS_PER_ROTATION = 5450;
        static double INCHES_PER_ROTATION = TICKS_PER_ROTATION / TICKS_PER_INCH;

        static int DEBUG_WAIT = 0; // Time to wait after each move, for debug purposes
    }

    //// MOTORS ////
    private DcMotorEx drive_lf;   // Left-Front drive motor
    private DcMotorEx drive_rf;   // Right-Front drive motor
    private DcMotorEx drive_lb;   // Left-Back drive motor
    private DcMotorEx drive_rb;   // Right-Back drive motor

    public LocalCoordinateSystem lcs = new LocalCoordinateSystem();

    public int color = RED;

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
        drive_lf    = hwmap.get(DcMotorEx.class, "drive_lf");
        drive_rf    = hwmap.get(DcMotorEx.class, "drive_rf");
        drive_lb    = hwmap.get(DcMotorEx.class, "drive_lb");
        drive_rb    = hwmap.get(DcMotorEx.class, "drive_rb");

        //// LYNX ////
        //lynx_module = hwmap.get(LynxModule.class, "Rev expansion hub 1");
    }

    @Override
    public void update(OpMode opmode) {
        super.update(opmode);

        lcs.update(robot.bulk_data_1.getMotorCurrentPosition(drive_lf), robot.bulk_data_1.getMotorCurrentPosition(drive_rf), robot.bulk_data_1.getMotorCurrentPosition(drive_lb));

    }

    @Override
    public void updateTelemetry(Telemetry telemetry) {
        super.updateTelemetry(telemetry);

        telemetry.addData("LE TURNS", robot.bulk_data_1.getMotorCurrentPosition(drive_lf));
        telemetry.addData("RE TURNS", robot.bulk_data_1.getMotorCurrentPosition(drive_rf));
        telemetry.addData("CE TURNS", robot.bulk_data_1.getMotorCurrentPosition(drive_lb));

        telemetry.addData("X", lcs.x);
        telemetry.addData("Y", lcs.y);
        telemetry.addData("A", lcs.a);

        telemetry.addData("PID", drive_lf.getPIDCoefficients(DcMotor.RunMode.RUN_TO_POSITION)+" | "+drive_rf.getPIDCoefficients(DcMotor.RunMode.RUN_TO_POSITION)+" | "+drive_lb.getPIDCoefficients(DcMotor.RunMode.RUN_TO_POSITION)+" | "+drive_rb.getPIDCoefficients(DcMotor.RunMode.RUN_TO_POSITION));
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

        set_mode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        set_mode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

    }

    @Override
    public void shutdown() {
        super.shutdown();

        stop();
    }

    private double[] mecanum_math(double lx, double ly, double rx) {
        double[] power = new double[]{-lx + ly + rx, +lx + ly - rx, +lx + ly + rx, -lx + ly - rx};

        double max = Math.max(Math.max(Math.abs(power[0]),Math.abs(power[1])),Math.max(Math.abs(power[2]),Math.abs(power[3])));

        if (max > 1) {
            power[0] /= max;
            power[1] /= max;
            power[2] /= max;
            power[3] /= max;
        }

        return power;
    }

    public void mechanumDrive(double lx, double ly, double rx) {
        double[] power = mecanum_math(lx, ly, rx);
        set_power(power[0], power[1], power[2], power[3]);
    }

    public void stop() {
        set_power(0);
        set_mode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
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

    public void turn(double turns, double speed) {
        double lf = turns * INCHES_PER_ROTATION * TICKS_PER_INCH;
        double rf = -turns * INCHES_PER_ROTATION * TICKS_PER_INCH;
        double lb = turns * INCHES_PER_ROTATION * TICKS_PER_INCH;
        double rb = -turns * INCHES_PER_ROTATION * TICKS_PER_INCH;

        drive_lf.setTargetPosition(drive_lf.getCurrentPosition()+(int)lf);
        drive_rf.setTargetPosition(drive_rf.getCurrentPosition()+(int)rf);
        drive_lb.setTargetPosition(drive_lb.getCurrentPosition()+(int)lb);
        drive_rb.setTargetPosition(drive_rb.getCurrentPosition()+(int)rb);

        set_mode(DcMotor.RunMode.RUN_TO_POSITION);

        set_power(speed);


        while (is_busy() && robot.lopmode.opModeIsActive()){
            robot.lopmode.idle();
            robot.lopmode.telemetry.addData("TURNING", (int)lf+" "+(int)rf+" "+(int)lb+" "+(int)rb);
            robot.lopmode.telemetry.addData("MOTORS", drive_lf.isBusy()+" "+drive_rf.isBusy()+" "+drive_lb.isBusy()+" "+drive_rb.isBusy());
            robot.lopmode.telemetry.addData("POSITION", drive_lf.getCurrentPosition()+" "+drive_rf.getCurrentPosition()+" "+drive_lb.getCurrentPosition()+" "+drive_rb.getCurrentPosition());
            robot.lopmode.telemetry.addData("TARGET", drive_lf.getTargetPosition()+" "+drive_rf.getTargetPosition()+" "+drive_lb.getTargetPosition()+" "+drive_rb.getTargetPosition());
            robot.lopmode.telemetry.update();

            if (!is_busy()) {
                break;
            }
        }

        robot.lopmode.telemetry.addData("MOVING", "STOPPING");
        robot.lopmode.telemetry.update();

        set_power(0);
        set_mode(DcMotor.RunMode.RUN_USING_ENCODER);

        robot.lopmode.telemetry.addData("MOVING", "COMPLETE");
        robot.lopmode.telemetry.update();

        if(DEBUG_WAIT > 0) robot.lopmode.sleep(DEBUG_WAIT);
    }

    public void encoder_drive(double x, double y, double a, double d, double speed) {

        double lf =  (-x - y + a);
        double rf = (+x - y - a);
        double lb = (+x - y + a);
        double rb =  (-x - y - a);

        drive_lf.setTargetPosition(drive_lf.getCurrentPosition()+(int)(lf*TICKS_PER_INCH*d));
        drive_rf.setTargetPosition(drive_rf.getCurrentPosition()+(int)(rf*TICKS_PER_INCH*d));
        drive_lb.setTargetPosition(drive_lb.getCurrentPosition()+(int)(lb*TICKS_PER_INCH*d));
        drive_rb.setTargetPosition(drive_rb.getCurrentPosition()+(int)(rb*TICKS_PER_INCH*d));


        set_mode(DcMotor.RunMode.RUN_TO_POSITION);


        set_power(lf*speed, rf*speed, lb*speed, rb*speed);

        while (is_busy() && robot.lopmode.opModeIsActive()){
            robot.lopmode.idle();
            robot.lopmode.telemetry.addData("MOVING", (int)(lf*TICKS_PER_INCH*d)+" "+(int)(rf*TICKS_PER_INCH*d)+" "+(int)(lb*TICKS_PER_INCH*d)+" "+(int)(rb*TICKS_PER_INCH*d));
            robot.lopmode.telemetry.addData("MOTORS", drive_lf.isBusy()+" "+drive_rf.isBusy()+" "+drive_lb.isBusy()+" "+drive_rb.isBusy());
            robot.lopmode.telemetry.addData("POSITION", drive_lf.getCurrentPosition()+" "+drive_rf.getCurrentPosition()+" "+drive_lb.getCurrentPosition()+" "+drive_rb.getCurrentPosition());
            robot.lopmode.telemetry.addData("TARGET", drive_lf.getTargetPosition()+" "+drive_rf.getTargetPosition()+" "+drive_lb.getTargetPosition()+" "+drive_rb.getTargetPosition());
            robot.lopmode.telemetry.update();

            if (!is_busy()) {
                break;
            }
        }

        robot.lopmode.telemetry.addData("MOVING", "STOPPING");
        robot.lopmode.telemetry.update();

        set_power(0);
        set_mode(DcMotor.RunMode.RUN_USING_ENCODER);

        robot.lopmode.telemetry.addData("MOVING", "COMPLETE");
        robot.lopmode.telemetry.update();

        if(DEBUG_WAIT > 0) robot.lopmode.sleep(DEBUG_WAIT);
    }

    public void odo_move(double x, double y, double a, double speed) {
        odo_move(x, y, a, speed, 1, 0.02, 0);
    }

    public void odo_move(double x, double y, double a, double speed, double pos_acc, double angle_acc) {
        odo_move(x, y, a, speed, pos_acc, angle_acc, 0);
    }

    public void odo_move(double x, double y, double a, double speed, double pos_acc, double angle_acc, double timeout) {

        final double MOVE_FEEDFORWARD  = 0.1;

        FtcDashboard dashboard = FtcDashboard.getInstance();
        Telemetry dtelemetry = dashboard.getTelemetry();


        if (color == RED) {
            a = -a;
        } else {
            x = -x;
        }

        double original_distance = Math.hypot(x-lcs.x, y-lcs.y);
        double original_distance_a = Math.abs(a - lcs.a);

        robot.lopmode.resetStartTime();

        if (original_distance > 0 || original_distance_a > 0) {
            while (robot.lopmode.opModeIsActive()) {
                double distance = Math.hypot(x - lcs.x, y - lcs.y);
                double distance_a = Math.abs(a - lcs.a);

                double progress = distance/original_distance;
                double progress_a = distance_a/original_distance_a;

                double drive_angle = Math.atan2(y-lcs.y, x-lcs.x);
                double drive_x = Math.cos(drive_angle - lcs.a) * ((Range.clip(distance, 0, (8*speed)))/(8*speed)) * speed;
                double drive_y = Math.sin(drive_angle - lcs.a) * ((Range.clip(distance, 0, (8*speed)))/(8*speed)) * speed;
                double drive_a = Range.clip((a-lcs.a)*3, -1, 1) * speed;

                //drive_x = 0;
                //drive_y = 0;

                robot.lopmode.telemetry.addData("x", lcs.x);
                robot.lopmode.telemetry.addData("y", lcs.y);
                robot.lopmode.telemetry.addData("a", lcs.a);

                robot.lopmode.telemetry.addData("le", lcs.prev_le);
                robot.lopmode.telemetry.addData("re", lcs.prev_re);
                robot.lopmode.telemetry.addData("ce", lcs.prev_ce);


                robot.lopmode.telemetry.addData("drive_x", drive_x);
                robot.lopmode.telemetry.addData("drive_y", drive_y);
                robot.lopmode.telemetry.addData("drive_a", drive_a);

                robot.lopmode.telemetry.addData("drive_angle", drive_angle);

                robot.lopmode.telemetry.addData("distance", distance);
                robot.lopmode.telemetry.addData("distance_a", distance_a);

                robot.lopmode.telemetry.update();


                dtelemetry.addData("distance", distance);
                dtelemetry.addData("orig_distance", original_distance);

                dtelemetry.addData("distance_a", distance_a);
                dtelemetry.addData("orig_distance_a", original_distance_a);

                dtelemetry.update();

                /*
                if (drive_x > 0) {
                    drive_x += MOVE_FEEDFORWARD;
                } else if (drive_x < 0) {
                    drive_x -= MOVE_FEEDFORWARD;
                }

                if (drive_y > 0) {
                    drive_y += MOVE_FEEDFORWARD;
                } else if (drive_y < 0) {
                    drive_y -= MOVE_FEEDFORWARD;
                }

                if (drive_a > 0) {
                    drive_a += MOVE_FEEDFORWARD;
                } else if (drive_a < 0) {
                    drive_a -= MOVE_FEEDFORWARD;
                }
                */

                mechanumDrive(drive_x, -drive_y, -drive_a);

                if ((distance < pos_acc && distance_a < angle_acc) || (timeout > 0 && robot.lopmode.getRuntime() > timeout)) {
                    mechanumDrive(0, 0, 0);
                    break;
                }
            }
        }
    }
}
