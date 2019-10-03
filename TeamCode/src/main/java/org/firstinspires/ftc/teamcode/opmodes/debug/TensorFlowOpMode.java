package org.firstinspires.ftc.teamcode.opmodes.debug;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.robot.robots.SoftwareRobot;

import java.util.Arrays;

@TeleOp(name="TensorFlow Stones", group="driver control")
//@Disabled
public class TensorFlowOpMode extends OpMode {

    SoftwareRobot robot;


    @Override
    public void init() {
        robot = new SoftwareRobot(this);
        robot.startup();
    }

    @Override
    public void loop() {
        robot.update();

        telemetry.addData("STONES", Arrays.toString(robot.phone_camera.findBlocks().toArray()));
    }

    @Override
    public void stop() {
        super.stop();
        robot.shutdown();
    }
}
