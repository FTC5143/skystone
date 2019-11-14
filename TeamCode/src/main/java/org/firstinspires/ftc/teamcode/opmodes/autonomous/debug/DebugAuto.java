package org.firstinspires.ftc.teamcode.opmodes.autonomous.debug;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.opmodes.autonomous.LiveAutoBase;
import org.firstinspires.ftc.teamcode.robot.robots.LiveRobot;

import static org.firstinspires.ftc.teamcode.constants.AutonomousConst.RED;

@Autonomous(name="TwoStone", group="autonomous")
@Disabled
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

        robot.drive_train.encoder_drive(0,1,0,12,0.75);

        robot.drive_train.turn(0.25, 0.5);


        if (pattern == 1) {
            robot.drive_train.encoder_drive(0, 1, 0, 1, 0.75);
        } else if (pattern == 3) {
            robot.drive_train.encoder_drive(0,-1,0,7,0.75);
        }


        robot.drive_train.encoder_drive(-1,0,0,9,0.75);

        robot.stone_grabber.grab_l();
        sleep(1000);

        robot.drive_train.encoder_drive(1,0,0,15,0.75); // Drive adjacent to the tape

        if (pattern == 1) {
            robot.drive_train.encoder_drive(0, -1, 0, 36, 0.75);
        } else if (pattern == 3) {
            robot.drive_train.encoder_drive(0,-1,0,28,0.75);
        }

        robot.stone_grabber.release_l();
        sleep(1000);

        if (pattern == 1) {
            robot.drive_train.encoder_drive(0, 1, 0, 51, 0.75);
        } else if (pattern == 3) {
            robot.drive_train.encoder_drive(0,1,0,43,0.75);
        }

        robot.drive_train.encoder_drive(-1,0,0,15,0.75);
        robot.stone_grabber.grab_l();
        sleep(1000);
        robot.drive_train.encoder_drive(1,0,0,15,0.75); // Drive adjacent to the tape

        if (pattern == 1) {
            robot.drive_train.encoder_drive(0, -1, 0, 51, 0.75);
        } else if (pattern == 3) {
            robot.drive_train.encoder_drive(0,-1,0,43,0.75);
        }

        robot.stone_grabber.release_l();
        sleep(1000);

        robot.drive_train.encoder_drive(0,1,0,10,1);


        sleep(800);
    }

    @Override
    public void on_stop() { }
}
