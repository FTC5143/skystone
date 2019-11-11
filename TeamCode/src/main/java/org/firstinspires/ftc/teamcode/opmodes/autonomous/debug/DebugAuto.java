package org.firstinspires.ftc.teamcode.opmodes.autonomous.debug;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.opmodes.autonomous.LiveAutoBase;
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

        robot.drive_train.encoder_drive(0,1,0,12,1);

        robot.drive_train.turn(0.25, 1);


        if (pattern == 1) {
            robot.drive_train.encoder_drive(0, -1, 0, 4, 1);
        } else if (pattern == 3) {
            robot.drive_train.encoder_drive(0,1,0,6,1);
        }


        robot.drive_train.encoder_drive(-1,0,0,19,1);

        robot.feeder.spin(1);
        robot.drive_train.encoder_drive(0,1,0,7,0.5); // Intake the block
        robot.feeder.spin(0);

        robot.drive_train.encoder_drive(0,-1,0,7,1); // Back up from next block

        robot.drive_train.encoder_drive(1,0,0,13,1); // Drive adjacent to the tape

        robot.drive_train.turn(0.5, 1); // 180

        robot.drive_train.encoder_drive(0,1,0,40,1);

        robot.feeder.spin(-1);
        robot.drive_train.encoder_drive(0,-1,0,25,1); // Spit the block
        robot.feeder.spin(0);


        sleep(800);
    }

    @Override
    public void on_stop() { }
}
