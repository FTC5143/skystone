package org.firstinspires.ftc.teamcode.opmodes.teleop;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.opmodes.LiveTeleopBase;

@TeleOp(name="Encoder Reset", group="driver control")
//@Disabled
public class EncoderReset extends LiveTeleopBase {

    boolean dpad_up_pressed = false;
    boolean dpad_down_pressed = false;

    boolean dpad_left_pressed = false;
    boolean dpad_right_pressed = false;

    @Override
    public void on_init() {

    }

    @Override
    public void on_start() {

    }

    @Override
    public void on_loop() {

        if(gamepad2.dpad_up && !dpad_up_pressed) {

            robot.lift.elevate_without_stops(1);
            dpad_up_pressed = true;

        } else if (!gamepad2.dpad_up) {

            dpad_up_pressed = false;

        }

        if(gamepad2.dpad_down && !dpad_down_pressed) {

            robot.lift.elevate_without_stops(-1);
            dpad_down_pressed = true;

        } else if (!gamepad2.dpad_down) {

            dpad_down_pressed = false;

        }

        if(gamepad2.a) {
            robot.lift.extend();
        } else if (gamepad2.b) {
            robot.lift.retract();
        }


        if(gamepad2.x)  { robot.lift.grab(); }
        if(gamepad2.y)  { robot.lift.release(); }

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

        if (gamepad2.left_bumper || gamepad2.right_bumper) {
            robot.feeder.spin(gamepad2.left_bumper ? -1 : 0, gamepad2.right_bumper ? -1 : 0);
        } else {
            robot.feeder.spin(gamepad2.left_trigger, gamepad2.right_trigger);
        }
    }

    @Override
    public void on_stop() {

    }
}
