package org.firstinspires.ftc.teamcode.opmodes.debug;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.robots.LiveRobot;

import static org.firstinspires.ftc.teamcode.constants.AutonomousConst.RED;

@Autonomous(name="Odo Test", group="autonomous")
//@Disabled
public class OdoTest extends LinearOpMode {

    LiveRobot robot;

    int pattern;

    @Override
    public void runOpMode() throws InterruptedException {
        robot = new LiveRobot(this);
        robot.startup();

        robot.phone_camera.start_streaming(RED);

        while(!isStarted()) {
            pattern = robot.phone_camera.get_pattern(RED);

            telemetry.addData("PATTERN", pattern);
            telemetry.update();
        }

        waitForStart();

        robot.drive_train.odo_move(10, 30,-Math.PI/2, 1);

        robot.drive_train.odo_move(12, 40, -Math.PI/2, 0.5, 0.5, 0.02);

        robot.feeder.spin(1);

        robot.drive_train.odo_move(4, 40, -Math.PI/2, 1);

        resetStartTime();

        while (!robot.lift.block_detector.isPressed() && getRuntime() < 2) {} // Wait for block to be intook, or 2 seconds

        robot.feeder.spin(0);

        robot.drive_train.odo_move(6, 26, -Math.PI/2, 1);

        robot.drive_train.odo_move(6, 26, Math.PI/2, 1);

        robot.drive_train.odo_move(48, 26, Math.PI/2, 1);

        robot.drive_train.odo_move(52, 26, Math.PI/2, 0.5);

        robot.feeder.spin(-1);

        robot.drive_train.odo_move(48, 26, Math.PI/2, 0.5);

        robot.drive_train.odo_move(6, 26, Math.PI/2, 1);

        robot.drive_train.odo_move(-12, 26, -Math.PI/2, 1);

        robot.feeder.spin(1);


        robot.drive_train.odo_move(-12, 40, -Math.PI/2, 0.5);

        robot.drive_train.odo_move(-20, 40, -Math.PI/2, 1);

        resetStartTime();

        while (!robot.lift.block_detector.isPressed() && getRuntime() < 2) {} // Wait for block to be intook, or 2 seconds

        robot.feeder.spin(0);

        robot.drive_train.odo_move(10, 26, Math.PI/2, 1);

        robot.drive_train.odo_move(48, 26, Math.PI/2, 1);

        robot.drive_train.odo_move(52, 26, Math.PI/2, 0.5);

        robot.feeder.spin(-1);

        robot.drive_train.odo_move(48, 26, Math.PI/2, 0.5);

        robot.drive_train.odo_move(36, 26, Math.PI/2, 1);



        robot.shutdown();
    }
}
