package org.firstinspires.ftc.teamcode.opmodes.debug;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.robots.LiveRobot;

import static org.firstinspires.ftc.teamcode.constants.AutonomousConst.BLUE;

@Autonomous(name="Two Stone Auto", group="autonomous")
//@Disabled
public class TwoStoneAuto extends LinearOpMode {

    LiveRobot robot;

    int pattern;

    @Override
    public void runOpMode() throws InterruptedException {
        robot = new LiveRobot(this);
        robot.startup();

        int color = BLUE;

        robot.drive_train.color = color;


        robot.phone_camera.start_streaming(color);

        while(!isStarted()) {
            pattern = robot.phone_camera.get_pattern(color);

            telemetry.addData("PATTERN", pattern);
            telemetry.update();
        }

        waitForStart();

        if (pattern == 1) {

            robot.drive_train.odo_move(2, 30, -Math.PI / 2, 1);

            robot.drive_train.odo_move(4, 40, -Math.PI / 2, 0.5, 0.5, 0.02);

            robot.feeder.spin(1);

            robot.drive_train.odo_move(-4, 40, -Math.PI / 2, 0.5);

        }
        else if (pattern == 2) {

            robot.drive_train.odo_move(10, 30, -Math.PI / 2, 1);

            robot.drive_train.odo_move(12, 40, -Math.PI / 2, 0.5, 0.5, 0.02);

            robot.feeder.spin(1);

            robot.drive_train.odo_move(4, 40, -Math.PI / 2, 0.5);

        } else if (pattern == 3) {
            robot.drive_train.odo_move(2, 30, -Math.PI / 2, 1);

            robot.drive_train.odo_move(4, 41, -Math.PI / 2, 0.5, 0.5, 0.02);

            robot.drive_train.odo_move(4, 27, -Math.PI / 2, 1);

            robot.drive_train.odo_move(-4, 27, -Math.PI / 2, 0.5, 0.5, 0.02);

            robot.drive_train.odo_move(-4, 40, -Math.PI / 2, 0.5, 0.5, 0.02);

            robot.feeder.spin(1);

            robot.drive_train.odo_move(-12, 40, -Math.PI / 2, 0.5);
        }

        resetStartTime();

        while (!robot.lift.block_detector.isPressed() && getRuntime() < 2) {} // Wait for block to be intook, or 2 seconds

        robot.feeder.spin(0);

        robot.drive_train.odo_move(6, 25, Math.PI/2, 1);

        robot.drive_train.odo_move(48, 25, Math.PI/2, 1);

        robot.drive_train.odo_move(52, 25, Math.PI/2, 0.5);

        robot.feeder.spin(-1);

        robot.drive_train.odo_move(48, 25, Math.PI/2, 0.5);


        if (pattern == 1) {

            robot.drive_train.odo_move(6, 25, Math.PI/2, 1);

            robot.drive_train.odo_move(-20, 25, -Math.PI/2, 1);

            robot.feeder.spin(1);

            robot.drive_train.odo_move(-20, 40, -Math.PI / 2, 0.5);

            robot.drive_train.odo_move(-28, 40, -Math.PI / 2, 1);

        }

        else if (pattern == 2) {

            robot.drive_train.odo_move(6, 25, Math.PI/2, 1);

            robot.drive_train.odo_move(-12, 25, -Math.PI/2, 1);

            robot.feeder.spin(1);

            robot.drive_train.odo_move(-12, 40, -Math.PI / 2, 0.5);

            robot.drive_train.odo_move(-20, 40, -Math.PI / 2, 1);

        }

        else if (pattern == 3) {

            robot.drive_train.odo_move(6, 25, Math.PI/2, 1);

            robot.drive_train.odo_move(-4, 25, Math.PI/2, 1);

            robot.feeder.spin(1);

            robot.drive_train.odo_move(-4, 52, Math.PI / 2, 0.5);

            robot.drive_train.odo_move(4, 52, Math.PI / 2, 1);

        }

        resetStartTime();

        while (!robot.lift.block_detector.isPressed() && getRuntime() < 2) {} // Wait for block to be intook, or 2 seconds

        robot.feeder.spin(0);

        robot.drive_train.odo_move(10, 25, Math.PI/2, 1);

        robot.drive_train.odo_move(48, 25, Math.PI/2, 1);

        robot.drive_train.odo_move(52, 25, Math.PI/2, 0.5);

        robot.feeder.spin(-1);

        robot.drive_train.odo_move(48, 25, Math.PI/2, 0.5);

        robot.drive_train.odo_move(36, 25, Math.PI/2, 1);



        robot.shutdown();
    }
}
