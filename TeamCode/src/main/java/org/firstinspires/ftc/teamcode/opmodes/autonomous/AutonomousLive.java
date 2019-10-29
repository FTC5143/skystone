package org.firstinspires.ftc.teamcode.opmodes.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.constants.AutonomousConst;
import org.firstinspires.ftc.teamcode.robot.robots.LiveRobot;

import static org.firstinspires.ftc.teamcode.constants.AutonomousConst.*;

@Autonomous(name="Live Auto", group="autonomous")
public class AutonomousLive extends LinearOpMode {

    LiveRobot robot;

    protected static int COLOR;
    protected static int SIDE;

    protected static boolean TAPE;
    protected static boolean FOUNDATION;
    protected static boolean SKYSTONE;

    int pattern;

    @Override
    public void runOpMode() throws InterruptedException {

        robot = new LiveRobot(this);
        robot.startup();

        if (SKYSTONE) {
            while(!isStarted()) {
                robot.phone_camera.start_streaming();
                pattern = robot.phone_camera.get_pattern();

                telemetry.addData("PATTERN", pattern);
                telemetry.update();
            }
        }

        waitForStart();

        if (SKYSTONE) {
            robot.phone_camera.stop_streaming();
        }

        if (TAPE) {
            int dir = (SIDE == LEFT) ? 1 : -1;
            robot.drive_train.encoder_drive(dir, 0, 0, 33, 1);
        }

        robot.shutdown();
    }
}
