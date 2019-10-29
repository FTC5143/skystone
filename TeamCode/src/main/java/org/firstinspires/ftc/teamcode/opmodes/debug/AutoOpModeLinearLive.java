package org.firstinspires.ftc.teamcode.opmodes.debug;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.robot.robots.LiveRobot;
import org.firstinspires.ftc.teamcode.robot.robots.SoftwareRobot;

@Autonomous(name="Debug Auto Linear Live", group="autonomous")
//@Disabled
public class AutoOpModeLinearLive extends LinearOpMode {

    LiveRobot robot;


    @Override
    public void runOpMode() throws InterruptedException {
        robot = new LiveRobot(this);
        robot.startup();
        waitForStart();
        while (opModeIsActive()) {
            robot.drive_train.encoder_drive(0, 1, 0, 24, 0.5);
            robot.drive_train.encoder_drive(1, 0, 0, 24, 0.5);
            robot.drive_train.encoder_drive(0, -1, 0, 24, 0.5);
            robot.drive_train.encoder_drive(-1, 0, 0, 24, 0.5);

            robot.drive_train.encoder_drive(1, 1, 0, 24, 0.5);
            robot.drive_train.encoder_drive(1, -1, 0, 24, 0.5);
            robot.drive_train.encoder_drive(-1, -1, 0, 24, 0.5);
            robot.drive_train.encoder_drive(-1, 1, 0, 24, 0.5);
        }
        robot.shutdown();
    }
}
