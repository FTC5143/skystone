package org.firstinspires.ftc.teamcode.opmodes.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.robot.robots.LiveRobot;

import static org.firstinspires.ftc.teamcode.constants.AutonomousConst.RED;

@Autonomous(name="Debug Auto", group="autonomous")
public class DebugAuto extends LiveAutoBase {

    int pattern;

    @Override
    public void on_init() {
        robot.phone_camera.start_streaming();

        while(!isStarted()) {
            pattern = robot.phone_camera.get_pattern();

            telemetry.addData("PATTERN", pattern);
            telemetry.update();
        }
    }

    @Override
    public void on_start() {
        robot.phone_camera.stop_streaming();

        robot.drive_train.encoder_drive(0,1,0,8,0.5);

        robot.drive_train.turn(0.5, 0.5);


        robot.drive_train.encoder_drive(0,-1,0,12,0.5);

        switch(pattern) {
            case(1): {
                robot.drive_train.encoder_drive(-1,0,0,6,0.5);
            }
            case(2): {
                //robot.drive_train.encoder_drive(-1,0,0,0,0.5);
            }
            case(3): {
                robot.drive_train.encoder_drive(1,0,0,6,0.5);
            }
        }

        robot.stone_grabber.grab();

        sleep(800);

        robot.drive_train.encoder_drive(0,1,0,8,0.5);

        switch(pattern) {
            case(1): {
                robot.drive_train.encoder_drive(1,0,0,50,0.5);
            }
            case(2): {
                robot.drive_train.encoder_drive(1,0,0,44,0.5);
            }
            case(3): {
                robot.drive_train.encoder_drive(1,0,0,38,0.5);
            }
        }

        robot.stone_grabber.release();

        sleep(800);
    }

    @Override
    public void on_stop() { }
}
