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

            robot.drive_train.odo_move(5, 40, -Math.PI / 2, 0.75, 0.5, 0.02);

            robot.feeder.spin(1);

            robot.drive_train.odo_move(-4, 40, -Math.PI / 2, 0.5);

        }
        else if (pattern == 2) {

            robot.drive_train.odo_move(13, 30, -Math.PI / 2, 1);

            robot.drive_train.odo_move(13, 40, -Math.PI / 2, 0.75, 0.5, 0.02);

            robot.feeder.spin(1);

            robot.drive_train.odo_move(4, 40, -Math.PI / 2, 0.5);

        } else if (pattern == 3) {
            robot.drive_train.odo_move(2, 30, -Math.PI / 2, 1);

            robot.drive_train.odo_move(4, 41, -Math.PI / 2, 1);

            robot.drive_train.odo_move(4, 27, -Math.PI / 2, 1);

            robot.drive_train.odo_move(-4, 27, -Math.PI / 2, 1);

            robot.drive_train.odo_move(-4, 40, -Math.PI / 2, 0.75, 0.5, 0.02);

            robot.feeder.spin(1);

            robot.drive_train.odo_move(-12, 40, -Math.PI / 2, 0.5);
        }

        resetStartTime();

        while (!robot.lift.block_detector.isPressed() && getRuntime() < 2) {} // Wait for block to be intook, or 2 seconds

        robot.lift.grab();

        robot.feeder.spin(0);



        if (FOUNDATION == true) {

            robot.drive_train.odo_move(6, 25, -Math.PI/2, 1);

            robot.drive_train.odo_move(52, 25, -Math.PI/2, 1);

            robot.lift.elevate(4);

            robot.drive_train.odo_move(78, 28, -Math.PI, 1);

            robot.lift.extend();

            robot.drive_train.odo_move(78, 34, -Math.PI, 0.6);

            robot.lift.turn(2);

            robot.dragger.grab();

            sleep(500);

            robot.drive_train.odo_move(76, 12, -Math.PI/2, 1, 1, 0.03, 3);

            robot.lift.release();

            sleep(750);

            robot.lift.turn(-2);

            robot.lift.retract();

            sleep(500);

            robot.dragger.release();

            robot.drive_train.odo_move(76, 25, -Math.PI/2, 1, 1, 0.03, 1.5);

            robot.lift.min_lift();

            sleep(500);

        } else {

            robot.drive_train.odo_move(6, 25, Math.PI/2, 1);

            robot.drive_train.odo_move(52, 25, Math.PI / 2, 0.5);

            robot.feeder.spin(-1);


            robot.drive_train.odo_move(48, 25, Math.PI/2, 0.5);

            robot.feeder.spin(0);

        }


        if (pattern == 1) {

            if (FOUNDATION == true) {
                robot.drive_train.odo_move(6, 25, -Math.PI/2, 1);
            } else {
                robot.drive_train.odo_move(6, 25, Math.PI/2, 1);
            }

            robot.drive_train.odo_move(-20, 25, -Math.PI/2, 1);

            robot.feeder.spin(1);

            robot.drive_train.odo_move(-20, 40, -Math.PI / 2, 0.5);

            robot.drive_train.odo_move(-28, 40, -Math.PI / 2, 1);

        }

        else if (pattern == 2) {

            if (FOUNDATION == true) {
                robot.drive_train.odo_move(6, 25, -Math.PI/2, 1);
            } else {
                robot.drive_train.odo_move(6, 25, Math.PI/2, 1);
            }

            robot.drive_train.odo_move(-12, 25, -Math.PI/2, 1);

            robot.feeder.spin(1);

            robot.drive_train.odo_move(-12, 40, -Math.PI / 2, 0.5);

            robot.drive_train.odo_move(-20, 40, -Math.PI / 2, 1);

        }

        else if (pattern == 3) {

            if (FOUNDATION == true) {
                robot.drive_train.odo_move(8, 25, -Math.PI/2, 1);

            } else {
                robot.drive_train.odo_move(8, 25, Math.PI/2, 1);

            }

            robot.drive_train.odo_move(-6, 25, Math.PI/2, 1);

            robot.feeder.spin(1);

            robot.drive_train.odo_move(-6, 50, Math.PI / 2, 0.5);

            robot.drive_train.odo_move(4, 50, Math.PI / 2, 1);

        }

        resetStartTime();

        while (!robot.lift.block_detector.isPressed() && getRuntime() < 2) {} // Wait for block to be intaken, or 2 seconds

        robot.feeder.spin(0);

        robot.lift.grab();

        if (FOUNDATION == true) {

            robot.drive_train.odo_move(10, 25, -Math.PI/2, 1);

            robot.drive_train.odo_move(52, 25, -Math.PI/2, 1);

            robot.lift.elevate(4);

            robot.drive_train.odo_move(76, 20, -Math.PI/2, 1, 1, 0.03, 3);

            robot.lift.extend();

            sleep(500);

            robot.lift.turn(2);

            sleep(500);

            robot.lift.release();

            sleep(750);

            robot.lift.retract();

            robot.lift.turn(-2);

            sleep(500);

            robot.lift.min_lift();

        } else {

            robot.drive_train.odo_move(10, 25, Math.PI/2, 1);

            robot.drive_train.odo_move(52, 25, Math.PI/2, 1);

            robot.feeder.spin(-1);

            robot.drive_train.odo_move(48, 25, Math.PI / 2, 0.5);

            robot.feeder.spin(0);

        }


        if (PARK == FAR) {

            if (FOUNDATION == true) {
                robot.drive_train.odo_move(36, 25, -Math.PI / 2, 1);
            } else {
                robot.drive_train.odo_move(36, 25, Math.PI / 2, 1);
            }

        }

    }

    @Override
    public void on_stop() {

    }
}
