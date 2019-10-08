package org.firstinspires.ftc.teamcode.opmodes.debug;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.robot.robots.SoftwareRobot;

@TeleOp(name="Debug Auto", group="driver control")
//@Disabled
public class AutoOpMode extends OpMode {

    SoftwareRobot robot;


    @Override
    public void init() {
        robot = new SoftwareRobot(this);
        robot.startup();
    }

    @Override
    public void start() {
        robot.drive_train.encoder_drive(10,10,0,1000,0.1);
    }

    @Override
    public void loop() {
        stop();
    }

    @Override
    public void stop() {
        super.stop();
        robot.shutdown();
    }
}
