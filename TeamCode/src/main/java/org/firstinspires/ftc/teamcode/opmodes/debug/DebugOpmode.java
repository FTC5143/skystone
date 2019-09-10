package org.firstinspires.ftc.teamcode.opmodes.debug;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.robot.Robot;

@TeleOp(name="Debug Teleop", group="driver control")
//@Disabled
public class DebugOpmode extends OpMode{

    Robot robot;

    @Override
    public void init() {
        robot = new Robot(this);
    }

    @Override
    public void init_loop() {
    }

    @Override
    public void start() {
    }

    @Override
    public void loop() {
        robot.update();
    }

    @Override
    public void stop() {
    }
}
