package org.firstinspires.ftc.teamcode.opmodes.debug;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.robots.LiveRobot;

@Autonomous(name="Debug Auto Linear Live", group="autonomous")
@Disabled
public class OdoTest extends LinearOpMode {

    LiveRobot robot;


    @Override
    public void runOpMode() throws InterruptedException {
        robot = new LiveRobot(this);
        robot.startup();
        waitForStart();
        robot.drive_train.odo_move(0,0,Math.PI,1);
        robot.shutdown();
    }
}
