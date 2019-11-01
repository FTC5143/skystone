package org.firstinspires.ftc.teamcode.opmodes.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.robot.robots.LiveRobot;

import static org.firstinspires.ftc.teamcode.constants.AutonomousConst.RED;

@Autonomous(name="Tape Auto", group="autonomous")
@Disabled
public class TapeAuto extends LinearOpMode {

    LiveRobot robot;

    protected static int COLOR = RED;

    @Override
    public void runOpMode() throws InterruptedException {
        int x_mod = COLOR == RED ? 1 : -1;

        robot = new LiveRobot(this);
        robot.startup();

        waitForStart();

        robot.drive_train.encoder_drive(0,1,0,18,0.5);

        robot.drive_train.encoder_drive(1*x_mod,0,0, 24, 0.5);

        robot.shutdown();
    }
}
