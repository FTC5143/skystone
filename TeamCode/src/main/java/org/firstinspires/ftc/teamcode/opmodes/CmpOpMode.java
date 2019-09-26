package org.firstinspires.ftc.teamcode.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.robot.robots.Robot;

@Disabled
public abstract class CmpOpMode extends OpMode {

    protected Robot robot;

    @Override
    public void init() {
        robot = new Robot(this);
        robot.startup();
    }

    @Override
    public void loop() {
        robot.update();
    }

    @Override
    public void stop() {
        super.stop();
        robot.shutdown();
    }
}
