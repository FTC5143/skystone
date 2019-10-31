package org.firstinspires.ftc.teamcode.opmodes.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.robot.robots.LiveRobot;

@Autonomous(name="Foundation Auto", group="autonomous")
public class Forward3ft extends LinearOpMode {

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

        waitForStart();

        robot.dragger.release();
        sleep(500);
        robot.drive_train.encoder_drive(1,0,0,9, 0.5);
        sleep(500);
        robot.drive_train.encoder_drive(0,1,0,20, 0.5);
        robot.drive_train.encoder_drive(0,1,0,6, 0.25);
        sleep(500);
        robot.dragger.grab();
        sleep(500);
        robot.drive_train.encoder_drive(0,-1,0,27, 0.5);
        sleep(500);
        robot.dragger.release();
        sleep(500);
        robot.drive_train.encoder_drive(-1,0,0, 23, 0.5);
        sleep(500);
        robot.drive_train.encoder_drive(0,1,0, 13, 0.5);
        sleep(500);
        robot.drive_train.encoder_drive(1,0,0, 11, 0.5);
        sleep(500);
        robot.drive_train.encoder_drive(-1,0,0, 28, 0.5);



        robot.shutdown();
    }
}
