package org.firstinspires.ftc.teamcode.opmodes.teleop;

import android.view.KeyEvent;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.robot.robots.LiveRobot;
import org.firstinspires.ftc.teamcode.robot.robots.SoftwareRobot;
import org.firstinspires.ftc.teamcode.util.LessBadGamepad;

@TeleOp(name="Teleop Live", group="driver control")
//@Disabled
public class DebugOpmodeLive extends OpMode {

    LiveRobot robot;

    boolean dpad_up_pressed = false;
    boolean dpad_down_pressed = false;


    @Override
    public void init() {
        robot = new LiveRobot(this);
        robot.startup();
    }

    @Override
    public void loop() {
        robot.update();

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

        robot.lift.extend(gamepad2.b ? -1 : (gamepad2.a ? 1 : 0));
        //robot.lift.set_power(gamepad1.dpad_up ? 1 : (gamepad1.dpad_down ? -1 : 0));


        if(gamepad1.left_bumper) {
            robot.drive_train.mechanumDrive(gamepad1.left_stick_x/4, gamepad1.left_stick_y/4, gamepad1.right_stick_x/4);
        }
        else if(gamepad1.right_bumper) {
            robot.drive_train.mechanumDrive(gamepad1.left_stick_x/2, gamepad1.left_stick_y/2, gamepad1.right_stick_x/2);
        }
        else {
            robot.drive_train.mechanumDrive(gamepad1.left_stick_x, gamepad1.left_stick_y, gamepad1.right_stick_x);
        }


        if(gamepad2.x)  { robot.lift.grab(); }
        if(gamepad2.y)    { robot.lift.release(); }


        if(gamepad2.dpad_left)  { robot.lift.turn(0.4); }
        else if(gamepad2.dpad_right) { robot.lift.turn(0.6); }
        else { robot.lift.turn(0.5); }

        if(gamepad1.x) {
            robot.stone_grabber.grab();
        }

        if(gamepad1.y) {
            robot.stone_grabber.release();
        }

        if(gamepad1.a) {
            robot.dragger.grab();
        }

        if(gamepad1.b) {
            robot.dragger.release();
        }

        if(gamepad1.back) {
            robot.phone_camera.start_streaming();
        }

        if (gamepad2.left_bumper || gamepad2.right_bumper) {
            robot.feeder.spin(gamepad2.left_bumper ? -1 : 0, gamepad2.right_bumper ? -1 : 0);
        } else {
            robot.feeder.spin(gamepad2.left_trigger, gamepad2.right_trigger);
        }
    }

    @Override
    public void stop() {
        super.stop();
        robot.shutdown();
    }
}
