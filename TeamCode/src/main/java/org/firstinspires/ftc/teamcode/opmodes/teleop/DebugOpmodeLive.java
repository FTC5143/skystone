package org.firstinspires.ftc.teamcode.opmodes.teleop;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.robots.LiveRobot;

@TeleOp(name="Teleop Live", group="driver control")
//@Disabled
public class DebugOpmodeLive extends OpMode {

    LiveRobot robot;

    boolean dpad_up_pressed = false;
    boolean dpad_down_pressed = false;

    boolean dpad_left_pressed = false;
    boolean dpad_right_pressed = false;

    @Override
    public void init() {
        robot = new LiveRobot(this);
        robot.startup();
    }

    @Override
    public void loop() {

        if(gamepad2.dpad_up && !dpad_up_pressed) {
            robot.lift.elevate(1);
            dpad_up_pressed = true;
        } else if (!gamepad2.dpad_up) {
            dpad_up_pressed = false;
        }

        if(gamepad2.dpad_down && !dpad_down_pressed) {
            robot.lift.elevate(-1);
            dpad_down_pressed = true;
        } else if (!gamepad2.dpad_down) {
            dpad_down_pressed = false;
        }

        if(gamepad2.back) {
            if(gamepad2.dpad_up) {
                robot.lift.max_lift();
            }
            else {
                robot.lift.min_lift();
                robot.lift.turn(-3);
            }
        }


        if(gamepad2.a) {
            robot.lift.extend();
        } else if (gamepad2.b) {
            robot.lift.retract();
        }


        if(gamepad1.left_bumper) {
            robot.drive_train.mechanum_drive(gamepad1.left_stick_x/4, gamepad1.left_stick_y/4, gamepad1.right_stick_x/4);
        }
        else if(gamepad1.right_bumper) {
            robot.drive_train.mechanum_drive(gamepad1.left_stick_x/2, gamepad1.left_stick_y/2, gamepad1.right_stick_x/2);
        }
        else {
            robot.drive_train.mechanum_drive(gamepad1.left_stick_x, gamepad1.left_stick_y, gamepad1.right_stick_x);
        }

        if(gamepad2.x)  { robot.lift.grab(); }
        if(gamepad2.y)    { robot.lift.release(); }


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



        if(gamepad1.a) {
            robot.dragger.grab();
        }

        if(gamepad1.b) {
            robot.dragger.release();
        }

        if (gamepad2.left_bumper || gamepad2.right_bumper) {
            robot.feeder.spin(gamepad2.left_bumper ? -1 : 0, gamepad2.right_bumper ? -1 : 0);
        } else if (robot.bulk_data_1.getDigitalInputState(1)){
            robot.feeder.spin(gamepad2.left_trigger, gamepad2.right_trigger);
        } else {
            robot.feeder.spin(0, 0);
        }

        if (gamepad2.right_stick_button) {
            robot.lift.cap();
        } else if (gamepad2.left_stick_button) {
            robot.lift.uncap();
        }
    }

    @Override
    public void stop() {
        super.stop();
        robot.shutdown();
    }
}
