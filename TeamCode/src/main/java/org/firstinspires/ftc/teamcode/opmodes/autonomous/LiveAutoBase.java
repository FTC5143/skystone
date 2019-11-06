package org.firstinspires.ftc.teamcode.opmodes.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.robot.robots.LiveRobot;

public abstract class LiveAutoBase extends LinearOpMode {

    protected LiveRobot robot;

    @Override
    public void runOpMode() throws InterruptedException {
        robot = new LiveRobot(this);
        robot.startup();
        on_init();
        waitForStart();
        on_start();
        while(opModeIsActive() && !isStopRequested()) {
            idle();
        }
        on_stop();
        robot.shutdown();
    }

    public abstract void on_init();

    public abstract void on_start();

    public abstract void on_stop();
}
