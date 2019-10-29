package org.firstinspires.ftc.teamcode.opmodes.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.robot.robots.LiveRobot;

import static org.firstinspires.ftc.teamcode.constants.AutonomousConst.RIGHT;

@Autonomous(name="Right Of Tape", group="autonomous")
public class RightOfTape extends AutonomousLive {
    LiveRobot robot;

    {
        SKYSTONE = true;
        TAPE = true;
        SIDE = RIGHT;
    }
}
