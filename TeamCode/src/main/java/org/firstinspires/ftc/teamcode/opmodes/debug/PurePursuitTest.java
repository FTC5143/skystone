package org.firstinspires.ftc.teamcode.opmodes.debug;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.robots.LiveRobot;
import org.firstinspires.ftc.teamcode.systems.pathfollowing.CurvePath;
import org.firstinspires.ftc.teamcode.systems.pathfollowing.CurvePoint;

import static org.firstinspires.ftc.teamcode.constants.AutonomousConst.RED;

@Autonomous(name="Pure Pursuit Test", group="autonomous")
//@Disabled
public class PurePursuitTest extends LinearOpMode {

    LiveRobot robot;


    @Override
    public void runOpMode() throws InterruptedException {
        robot = new LiveRobot(this);
        robot.startup();

        waitForStart();

        robot.drive_train.follow_curve_path(
                new CurvePath()
                .add_point(new CurvePoint(0, 0).speed(0.5))
                .add_point(new CurvePoint(120, 0))
                .add_point(new CurvePoint(120, 50))
                .add_point(new CurvePoint(50, 80))
                .reverse()
        );


        robot.shutdown();
    }
}
