package org.firstinspires.ftc.teamcode.opmodes.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

import org.firstinspires.ftc.teamcode.opmodes.autonomous.LiveAutoBase;

import static org.firstinspires.ftc.teamcode.constants.AutonomousConst.FAR;
import static org.firstinspires.ftc.teamcode.constants.AutonomousConst.RED;

@Autonomous(name="One Stone", group="autonomous")
@Disabled
public class OneStone extends LiveAutoBase {

    protected static int COLOR = RED;
    protected static int PARK = FAR;

    int pattern;

    int x_mod;

    @Override
    public void on_init() {
        robot.phone_camera.start_streaming(COLOR);

        while(!isStarted()) {
            pattern = robot.phone_camera.get_pattern(COLOR);

            telemetry.addData("PATTERN", pattern);
            telemetry.update();
        }

        x_mod = COLOR == RED ? 1 : -1;

    }

    @Override
    public void on_start() {
        robot.phone_camera.stop_streaming();

        robot.drive_train.encoder_drive(0,1,0,12,0.75);

        robot.drive_train.turn(-0.25*x_mod, 0.5);


        if (pattern == 1) {
            robot.drive_train.encoder_drive(0, -1, 0, 4, 0.75);
        } else if (pattern == 2) {
            robot.drive_train.encoder_drive(0,-1,0,9,0.75);
        }
        else if (pattern == 3) {
            robot.drive_train.encoder_drive(0,1,0,2,0.75);
        }


        robot.drive_train.encoder_drive(1*x_mod,0,0,19,0.75);

        robot.feeder.spin(1);
        robot.drive_train.encoder_drive(0,1,0,7,0.5); // Intake the block
        robot.feeder.spin(0);



        robot.drive_train.encoder_drive(0,-1,0,7,0.75); // Back up from next block

        robot.drive_train.encoder_drive(-1*x_mod,0,0,14,0.75); // Drive adjacent to the tape

        robot.drive_train.turn(-0.5*x_mod, 0.5); // 180

        if (pattern == 1) {
            robot.drive_train.encoder_drive(0, 1, 0, 36, 0.75);
        } else if (pattern == 2) {
            robot.drive_train.encoder_drive(0,1,0,31,0.75);
        } else if (pattern == 3) {
            robot.drive_train.encoder_drive(0,1,0,42,0.75);
        }

        robot.feeder.spin(-0.7);
        robot.drive_train.encoder_drive(0,-1,0,18,0.5); // Spit the block
        robot.feeder.spin(0);


        sleep(800);
    }

    @Override
    public void on_stop() { }
}
