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

    // Called when init is pressed, runs once
    public abstract void on_init();

    // Called when start is pressed, runs once
    public abstract void on_start();

    // Called when the program stops, runs once
    public abstract void on_stop();

    // Called repeatedly while the program is running
    public abstract void on_loop();
}
