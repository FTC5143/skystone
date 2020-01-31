package org.firstinspires.ftc.teamcode.opmodes.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

import static org.firstinspires.ftc.teamcode.constants.AutonomousConst.BLUE;
import static org.firstinspires.ftc.teamcode.constants.AutonomousConst.FAR;

@Autonomous(name="Two Stone Auto", group="autonomous")
@Disabled
public class TwoStoneAuto extends LiveAutoBase {

    int pattern;

    protected static int COLOR = BLUE;
    protected static int PARK = FAR;
    protected static boolean FOUNDATION = false;

    @Override
    public void on_init() {
        robot.phone_camera.start_streaming(COLOR);

        robot.drive_train.color = COLOR;

        while(!isStarted()) {
            pattern = robot.phone_camera.get_pattern(COLOR);

            telemetry.addData("PATTERN", pattern);
            telemetry.update();
        }

    }

    @Override
    public void on_start() {

        if (pattern == 1) {

            robot.drive_train.odo_move(5, 30, -Math.PI / 2, 1);

            robot.drive_train.odo_move(5, 40, -Math.PI / 2, 0.5, 0.5, 0.02);

            robot.feeder.spin(1);

            robot.drive_train.odo_move(-4, 40, -Math.PI / 2, 0.5);

        }
        else if (pattern == 2) {

            robot.drive_train.odo_move(13, 30, -Math.PI / 2, 1);

            robot.drive_train.odo_move(13, 40, -Math.PI / 2, 0.5, 0.5, 0.02);

            robot.feeder.spin(1);

            robot.drive_train.odo_move(4, 40, -Math.PI / 2, 0.5);

        } else if (pattern == 3) {
            robot.drive_train.odo_move(2, 30, -Math.PI / 2, 1);

            robot.drive_train.odo_move(4, 41, -Math.PI / 2, 0.5, 0.5, 0.02);

            robot.drive_train.odo_move(4, 27, -Math.PI / 2, 1);

            robot.drive_train.odo_move(-4, 27, -Math.PI / 2, 0.5, 0.5, 0.02);

            robot.drive_train.odo_move(-4, 40, -Math.PI / 2, 0.5, 0.5, 0.02);

            robot.feeder.spin(1);

            robot.drive_train.odo_move(-12, 40, -Math.PI / 2, 0.5);
        }

        resetStartTime();

        while (!robot.lift.block_detector.isPressed() && getRuntime() < 2) {} // Wait for block to be intook, or 2 seconds

        robot.feeder.spin(0);

        robot.drive_train.odo_move(6, 25, Math.PI/2, 1);

        robot.drive_train.odo_move(48, 25, Math.PI/2, 1);

        if (FOUNDATION == true) {

            robot.drive_train.odo_move(78, 28, 0, 1);

            robot.drive_train.odo_move(78, 34, 0, 0.5);

            robot.dragger.grab();

            sleep(500);

            robot.drive_train.odo_move(76, 16, Math.PI/2, 1, 1, 0.03, 3);

            robot.dragger.release();

            sleep(500);

        }

        robot.drive_train.odo_move(52, 25, Math.PI/2, 0.5);

        robot.feeder.spin(-1);

        robot.drive_train.odo_move(48, 25, Math.PI/2, 0.5);


        if (pattern == 1) {

            robot.drive_train.odo_move(6, 25, Math.PI/2, 1);

            robot.drive_train.odo_move(-20, 25, -Math.PI/2, 1);

            robot.feeder.spin(1);

            robot.drive_train.odo_move(-20, 40, -Math.PI / 2, 0.5);

            robot.drive_train.odo_move(-28, 40, -Math.PI / 2, 1);

        }

        else if (pattern == 2) {

            robot.drive_train.odo_move(6, 25, Math.PI/2, 1);

            robot.drive_train.odo_move(-12, 25, -Math.PI/2, 1);

            robot.feeder.spin(1);

            robot.drive_train.odo_move(-12, 40, -Math.PI / 2, 0.5);

            robot.drive_train.odo_move(-20, 40, -Math.PI / 2, 1);

        }

        else if (pattern == 3) {

            robot.drive_train.odo_move(8, 25, Math.PI/2, 1);

            robot.drive_train.odo_move(-6, 25, Math.PI/2, 1);

            robot.feeder.spin(1);

            robot.drive_train.odo_move(-6, 50, Math.PI / 2, 0.5);

            robot.drive_train.odo_move(4, 50, Math.PI / 2, 1);

        }

        resetStartTime();

        while (!robot.lift.block_detector.isPressed() && getRuntime() < 2) {} // Wait for block to be intook, or 2 seconds

        robot.feeder.spin(0);

        robot.drive_train.odo_move(10, 25, Math.PI/2, 1);

        robot.drive_train.odo_move(48, 25, Math.PI/2, 1);

        robot.drive_train.odo_move(52, 25, Math.PI/2, 0.5);

        robot.feeder.spin(-1);

        robot.drive_train.odo_move(48, 25, Math.PI/2, 0.5);

        if (PARK == FAR) {
            robot.drive_train.odo_move(36, 25, Math.PI / 2, 1);
        }

    }

    @Override
    public void on_stop() {

    }
}
