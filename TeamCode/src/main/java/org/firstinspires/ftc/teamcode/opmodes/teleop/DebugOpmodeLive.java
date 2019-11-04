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


        if(gamepad1.dpad_up && !dpad_up_pressed) {
            robot.lift.elevate(1);
            dpad_up_pressed = true;
        } else if (!gamepad1.dpad_up) {
            dpad_up_pressed = false;
        }

        if(gamepad1.dpad_down && !dpad_down_pressed) {
            robot.lift.elevate(-1);
            dpad_down_pressed = true;
        } else if (!gamepad1.dpad_down) {
            dpad_down_pressed = false;
        }

        if(gamepad1.left_bumper) {
            robot.drive_train.mechanumDrive(gamepad1.left_stick_x/4, gamepad1.left_stick_y/4, gamepad1.right_stick_x/4);
        }
        else if(gamepad1.right_bumper) {
            robot.drive_train.mechanumDrive(gamepad1.left_stick_x/2, gamepad1.left_stick_y/2, gamepad1.right_stick_x/2);
        }
        else {
            robot.drive_train.mechanumDrive(gamepad1.left_stick_x, gamepad1.left_stick_y, gamepad1.right_stick_x);
        }


        if(gamepad2.dpad_down)  { robot.dragger.left_target -= 0.05; }
        if(gamepad2.dpad_up)    { robot.dragger.left_target += 0.05; }
        if(gamepad2.dpad_left)  { robot.dragger.right_target -= 0.05; }
        if(gamepad2.dpad_right) { robot.dragger.right_target += 0.05; }

        if(gamepad2.right_bumper) {
            robot.stone_grabber.grab();
        }

        if(gamepad2.left_bumper) {
            robot.stone_grabber.release();
        }

        //robot.stone_grabber.inc_position(gamepad2.left_trigger-gamepad2.right_trigger);

        if(gamepad2.x) {
            robot.dragger.grab();
        }

        if(gamepad2.y) {
            robot.dragger.release();
        }

        if(gamepad1.x) {
            robot.phone_camera.start_streaming();
        }

        if(gamepad1.y) {
            robot.phone_camera.stop_streaming();
        }

        if(gamepad2.a) {
            robot.feeder.spin(1);
        } else if (gamepad2.b) {
            robot.feeder.spin(-1);
        } else {
            robot.feeder.spin(-gamepad2.left_trigger, -gamepad2.right_trigger);
        }
    }

    @Override
    public void stop() {
        super.stop();
        robot.shutdown();
    }
}
