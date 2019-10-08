package org.firstinspires.ftc.teamcode.opmodes.debug;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.robot.robots.SoftwareRobot;

@TeleOp(name="Debug Auto Linear", group="driver control")
//@Disabled
public class AutoOpModeLinear extends LinearOpMode {

    SoftwareRobot robot;


    @Override
    public void runOpMode() throws InterruptedException {
        robot = new SoftwareRobot(this);
        robot.startup();
        waitForStart();
        robot.drive_train.encoder_drive(10,10,0,1000,0.1);
        robot.shutdown();
    }
}
