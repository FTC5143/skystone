package org.firstinspires.ftc.teamcode.opmodes.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.robot.robots.LiveRobot;

@Autonomous(name="Forward 3FT", group="autonomous")
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

        robot.drive_train.encoder_drive(0,1,0,12*24, 1);

        sleep(3000);



        robot.shutdown();
    }
}
