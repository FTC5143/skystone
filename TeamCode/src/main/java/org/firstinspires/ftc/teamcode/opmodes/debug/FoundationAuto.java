package org.firstinspires.ftc.teamcode.opmodes.debug;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.robots.LiveRobot;

import static org.firstinspires.ftc.teamcode.constants.AutonomousConst.RED;

@Autonomous(name="Foundation Auto", group="autonomous")
//@Disabled
public class FoundationAuto extends LinearOpMode {

    LiveRobot robot;

    int pattern;

    @Override
    public void runOpMode() throws InterruptedException {
        robot = new LiveRobot(this);
        robot.startup();

        int color = RED;

        robot.drive_train.color = color;


        waitForStart();

        robot.drive_train.odo_move(11, 26, 0, 1);

        robot.drive_train.odo_move(11, 32, 0, 0.5);

        robot.dragger.grab();

        sleep(1000);

        robot.drive_train.odo_move(11, 2, 0, 0.5);

        robot.dragger.release();

        robot.drive_train.odo_move(-19, 2, 0, 0.5);

        robot.drive_train.odo_move(-19, 26, 0, 0.5);

        robot.drive_train.odo_move(-40, 26, 0, 0.5);

        robot.shutdown();
    }
}
