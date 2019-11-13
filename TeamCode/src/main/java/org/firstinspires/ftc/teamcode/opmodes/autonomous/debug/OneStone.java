package org.firstinspires.ftc.teamcode.opmodes.autonomous.debug;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.opmodes.autonomous.LiveAutoBase;

@Autonomous(name="One Stone", group="autonomous")
public class OneStone extends LiveAutoBase {

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
            robot.drive_train.encoder_drive(0, -1, 0, 4, 0.75);
        } else if (pattern == 3) {
            robot.drive_train.encoder_drive(0,1,0,6,0.75);
        }


        robot.drive_train.encoder_drive(-1,0,0,19,0.75);

        robot.feeder.spin(1);
        robot.drive_train.encoder_drive(0,1,0,7,0.5); // Intake the block
        robot.feeder.spin(0);

        robot.drive_train.encoder_drive(0,-1,0,7,0.75); // Back up from next block

        robot.drive_train.encoder_drive(1,0,0,14,0.75); // Drive adjacent to the tape

        robot.drive_train.turn(0.5, 0.5); // 180

        robot.drive_train.encoder_drive(0,1,0,40,0.75);

        robot.feeder.spin(-1);
        robot.drive_train.encoder_drive(0,-1,0,22,0.5); // Spit the block
        robot.feeder.spin(0);


        sleep(800);
    }

    @Override
    public void on_stop() { }
}
