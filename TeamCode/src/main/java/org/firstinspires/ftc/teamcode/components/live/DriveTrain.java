package org.firstinspires.ftc.teamcode.components.live;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.components.Component;
import org.firstinspires.ftc.teamcode.robots.Robot;
import org.firstinspires.ftc.teamcode.systems.LocalCoordinateSystem;
import org.firstinspires.ftc.teamcode.systems.pathfollowing.CurvePath;
import org.firstinspires.ftc.teamcode.systems.pathfollowing.CurvePoint;
import org.firstinspires.ftc.teamcode.systems.pathfollowing.Point;

import static org.firstinspires.ftc.teamcode.constants.AutonomousConst.RED;

// Drive Train component
// Includes: Drive Motors, IMU
// I hate it also

public class DriveTrain extends Component {

    //// MOTORS ////
    private DcMotorEx drive_lf;   // Left-Front drive motor
    private DcMotorEx drive_rf;   // Right-Front drive motor
    private DcMotorEx drive_lb;   // Left-Back drive motor
    private DcMotorEx drive_rb;   // Right-Back drive motor

    public LocalCoordinateSystem lcs = new LocalCoordinateSystem();

    public int color = RED;

    // Drive train moves according to these
    private double drive_x = 0;
    private double drive_y = 0;
    private double drive_a = 0;

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

    }

    @Override
    public void update(OpMode opmode) {
        super.update(opmode);

        lcs.update(robot.bulk_data_1.getMotorCurrentPosition(drive_lf), robot.bulk_data_1.getMotorCurrentPosition(drive_rf), robot.bulk_data_1.getMotorCurrentPosition(drive_lb));

        double[] motor_powers = mecanum_math(drive_x, drive_y, drive_a);

        if (robot.cycle % 4 == 0) {drive_lf.setPower(motor_powers[0]);}
        if (robot.cycle % 4 == 1) {drive_rf.setPower(motor_powers[1]);}
        if (robot.cycle % 4 == 2) {drive_lb.setPower(motor_powers[2]);}
        if (robot.cycle % 4 == 3) {drive_rb.setPower(motor_powers[3]);}
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

    private double[] mecanum_math(double x, double y, double a) {
        double[] power = new double[]{-x + y + a, +x + y - a, +x + y + a, -x + y - a};

        double max = Math.max(Math.max(Math.abs(power[0]),Math.abs(power[1])),Math.max(Math.abs(power[2]),Math.abs(power[3])));

        if (max > 1) {
            power[0] /= max;
            power[1] /= max;
            power[2] /= max;
            power[3] /= max;
        }

        return power;
    }

    public void mechanum_drive(double x, double y, double a) {
        drive_x = x;
        drive_y = y;
        drive_a = a;
    }

    public void stop() {
        mechanum_drive(0, 0, 0);
        set_power(0);
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

    public void odo_move(double x, double y, double a, double speed) {
        odo_move(x, y, a, speed, 1, 0.02, 0);
    }

    public void odo_move(double x, double y, double a, double speed, double pos_acc, double angle_acc) {
        odo_move(x, y, a, speed, pos_acc, angle_acc, 0);
    }

    public void odo_move(double x, double y, double a, double speed, double pos_acc, double angle_acc, double timeout) {

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
                double mvmt_x = Math.cos(drive_angle - lcs.a) * ((Range.clip(distance, 0, (8*speed)))/(8*speed)) * speed;
                double mvmt_y = -Math.sin(drive_angle - lcs.a) * ((Range.clip(distance, 0, (8*speed)))/(8*speed)) * speed;
                double mvmt_a = -Range.clip((a-lcs.a)*3, -1, 1) * speed;

                mechanum_drive(mvmt_x, mvmt_y, mvmt_a);

                if ((distance < pos_acc && distance_a < angle_acc) || (timeout > 0 && robot.lopmode.getRuntime() > timeout)) {
                    stop();
                    break;
                }
            }
        }
    }

    public void follow_curve_path(CurvePath path) {
        while (robot.lopmode.opModeIsActive()) {

            Point lookahead_point = path.get_lookahead_point(lcs.x, lcs.y);

            drive_towards_point(lookahead_point, 1, 1);
        }
    }

    public void drive_towards_point(Point point, double drive_speed, double turn_speed) {

        double drive_angle = Math.atan2(point.y-lcs.y, point.x-lcs.x);

        double mvmt_x = Math.cos(drive_angle - lcs.a) * drive_speed;
        double mvmt_y = -Math.sin(drive_angle - lcs.a) * drive_speed;
        double mvmt_a = -Range.clip((drive_angle-lcs.a), -1, 1) * turn_speed;

        mechanum_drive(mvmt_x, mvmt_y, mvmt_a);

    }

    public void drive_to_pose(Pose2d pose, double drive_speed, double turn_speed) {

    }

}
