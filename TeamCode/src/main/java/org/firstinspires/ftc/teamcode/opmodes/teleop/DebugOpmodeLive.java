package org.firstinspires.ftc.teamcode.opmodes.teleop;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.robot.robots.LiveRobot;
import org.firstinspires.ftc.teamcode.robot.robots.SoftwareRobot;

@TeleOp(name="Debug Teleop Live", group="driver control")
//@Disabled
public class DebugOpmodeLive extends OpMode {

    LiveRobot robot;

    @Override
    public void init() {
        robot = new LiveRobot(this);
        robot.startup();
    }

    @Override
    public void loop() {
        robot.update();

        robot.drive_train.mechanumDrive(gamepad1.left_stick_x, gamepad1.left_stick_y, gamepad1.right_stick_x);

        robot.lift.elevate(gamepad1.right_trigger-gamepad1.left_trigger);


        if(gamepad1.dpad_down)  { robot.dragger.left_target -= 0.05; }
        if(gamepad1.dpad_up)    { robot.dragger.left_target += 0.05; }
        if(gamepad1.dpad_left)  { robot.dragger.right_target -= 0.05; }
        if(gamepad1.dpad_right) { robot.dragger.right_target += 0.05; }

        if(gamepad1.a) {
            robot.dragger.grab();
        }

        if(gamepad1.b) {
            robot.dragger.release();
        }

        if(gamepad1.x) {
            robot.phone_camera.start_streaming();
        }

        if(gamepad1.y) {
            robot.phone_camera.stop_streaming();
        }
    }

    @Override
    public void stop() {
        super.stop();
        robot.shutdown();
    }
}
