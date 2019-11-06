package org.firstinspires.ftc.teamcode.opmodes.autonomous.debug;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.opmodes.autonomous.LiveAutoBase;

@Autonomous(name="Debug Auto", group="autonomous")
public class AutoStoneIntake extends LiveAutoBase {

    int pattern;

    @Override
    public void on_init() {
    }

    @Override
    public void on_start() {
        robot.phone_camera.stop_streaming();

        robot.drive_train.turn(1/8, 0.5);

        robot.feeder.spin(1);
        robot.drive_train.encoder_drive(-1,1,0,80,0.5);
        robot.feeder.spin(0);

    }

    @Override
    public void on_stop() { }
}