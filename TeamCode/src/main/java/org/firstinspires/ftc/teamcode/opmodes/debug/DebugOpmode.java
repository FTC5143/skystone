package org.firstinspires.ftc.teamcode.opmodes.debug;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.robot.robots.SoftwareRobot;

@TeleOp(name="Debug Teleop", group="driver control")
//@Disabled
public class DebugOpmode extends OpMode {

    SoftwareRobot robot;


    @Override
    public void init() {
        robot = new SoftwareRobot(this);
        robot.startup();
    }

    @Override
    public void loop() {
        robot.update();

        robot.drive_train.mechanumDrive(gamepad1.left_stick_x, gamepad1.left_stick_y, gamepad1.right_stick_x);

        telemetry.addData("IS IT A SKYSTONE", robot.sensor_debug.isSkystone());

    }

    @Override
    public void stop() {
        super.stop();
        robot.shutdown();
    }
}
