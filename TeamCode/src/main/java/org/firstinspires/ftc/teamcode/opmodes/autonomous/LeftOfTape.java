package org.firstinspires.ftc.teamcode.opmodes.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.robot.robots.LiveRobot;

import static org.firstinspires.ftc.teamcode.constants.AutonomousConst.LEFT;

@Autonomous(name="Left Of Tape", group="autonomous")
public class LeftOfTape extends AutonomousLive {
    LiveRobot robot;

    {
        SKYSTONE = true;
        TAPE = true;
        SIDE = LEFT;
    }
}
