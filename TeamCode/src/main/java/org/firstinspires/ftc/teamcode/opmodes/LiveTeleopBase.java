package org.firstinspires.ftc.teamcode.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.robots.LiveRobot;

public abstract class LiveTeleopBase extends LinearOpMode {

    protected LiveRobot robot;

    @Override
    public void runOpMode() throws InterruptedException {
        robot = new LiveRobot(this);
        robot.startup();
        on_init();
        waitForStart();
        on_start();
        while(opModeIsActive() && !isStopRequested()) {
            on_loop();
        }
        on_stop();
        robot.shutdown();
        stop();
    }

    public abstract void on_init();

    public abstract void on_start();

    public abstract void on_stop();

    public abstract void on_loop();
}
