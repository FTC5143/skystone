package org.firstinspires.ftc.teamcode.opmodes.teleop;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.opmodes.LiveTeleopBase;

@TeleOp(name="Teleop Live", group="driver control")
//@Disabled
public class DebugOpmodeLive extends LiveTeleopBase {

    boolean dpad_up_pressed = false;
    boolean dpad_down_pressed = false;

    boolean dpad_left_pressed = false;
    boolean dpad_right_pressed = false;

    boolean a2_pressed = false;

    long slow_accel_starttime = System.currentTimeMillis();

    int prepared_level = 1;

    @Override
    public void on_init() {

    }

    @Override
    public void on_start() {
        this.getRuntime();
    }

    @Override
    public void on_loop() {

        if(gamepad2.left_bumper) {
            robot.lift.elevate_to(prepared_level);
        }


        if(gamepad2.right_bumper) {
            robot.lift.elevate_to(2);
        }

        if(gamepad2.dpad_up && !dpad_up_pressed) {
            prepared_level = Range.clip(prepared_level + 1, 0, 12);
            dpad_up_pressed = true;
        } else if (!gamepad2.dpad_up) {
            dpad_up_pressed = false;
        }

        if(gamepad2.dpad_down && !dpad_down_pressed) {
            prepared_level = Range.clip(prepared_level - 1, 0, 12);
            dpad_down_pressed = true;
        } else if (!gamepad2.dpad_down) {
            dpad_down_pressed = false;
        }


        if(gamepad2.back) {
            if(gamepad2.dpad_up) {
                robot.lift.max_lift();
            }
            else {

                final boolean extended = robot.lift.extended();

                robot.lift.turn(-100);
                robot.lift.release();
                robot.lift.min_lift();
                robot.lift.retract();
                robot.lift.uncap();
                //robot.lift.min_lift();
                //robot.lift.retract();
            }
        }


        if(gamepad2.a) {
            robot.lift.extend();
        }
        if (gamepad2.b) {
            robot.lift.retract();
        }

        robot.lift.tweak(-gamepad2.left_stick_y);

        if(gamepad2.x)  { robot.lift.grab(); }
        if(gamepad2.y)  { robot.lift.release(); }

        double speed_mod = 1;

        if(gamepad1.left_bumper) {
            speed_mod = 0.25;
        } else if(gamepad1.right_bumper) {
            speed_mod = 0.5;
        }

        robot.parker.park(gamepad2.left_trigger - gamepad2.right_trigger);

        robot.drive_train.mechanum_drive(gamepad1.left_stick_x * speed_mod, gamepad1.left_stick_y * speed_mod, gamepad1.right_stick_x * speed_mod);


        long current_time = System.currentTimeMillis();
        double slow_accel_speed = ((double) current_time - (double) slow_accel_starttime) / 3000.0;

        if (gamepad1.dpad_right) {
            robot.drive_train.mechanum_drive(0, -slow_accel_speed, slow_accel_speed);
        } else if (gamepad1.dpad_left) {
            robot.drive_train.mechanum_drive(0, -slow_accel_speed, -slow_accel_speed);
        } else if (gamepad1.dpad_up) {
            robot.drive_train.mechanum_drive(0, slow_accel_speed, 0);
        } else if (gamepad1.dpad_down) {
            robot.drive_train.mechanum_drive(0, -slow_accel_speed, 0);
        } else {
            slow_accel_starttime = System.currentTimeMillis();
        }

        if(gamepad2.dpad_left && !dpad_left_pressed) {
            robot.lift.turn(1);
            dpad_left_pressed = true;
        } else if (!gamepad2.dpad_left) {
            dpad_left_pressed = false;
        }

        if(gamepad2.dpad_right && !dpad_right_pressed) {
            robot.lift.turn(-1);
            dpad_right_pressed = true;
        } else if (!gamepad2.dpad_right) {
            dpad_right_pressed = false;
        }

        if(gamepad1.b) {
            robot.dragger.grab();
        }

        if(gamepad1.a) {
            robot.dragger.release();
        }

        double intake_movement = gamepad1.left_trigger - gamepad1.right_trigger;

        if (intake_movement < 0 || (intake_movement > 0 && robot.bulk_data_1 != null && robot.bulk_data_1.getDigitalInputState(1))) {
            robot.feeder.spin(intake_movement);
        } else {
            robot.feeder.spin(0);
        }

        if (gamepad2.right_stick_button) {
            robot.lift.cap();
        } else if (gamepad2.left_stick_button) {
            robot.lift.uncap();
        }
    }

    @Override
    public void on_stop() {

    }
}
