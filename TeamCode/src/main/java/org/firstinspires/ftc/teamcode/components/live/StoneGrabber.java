package org.firstinspires.ftc.teamcode.components.live;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.hardware.rev.RevTouchSensor;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.components.Component;
import org.firstinspires.ftc.teamcode.robots.Robot;

import static org.firstinspires.ftc.teamcode.components.live.StoneGrabber.StoneGrabberConfig.*;

public class StoneGrabber extends Component {
    Servo stone_grabber_l;
    Servo stone_grabber_r;

    @Config
    static class StoneGrabberConfig {
        static double GRABBER_UP_L = 0.25;
        static double GRABBER_DOWN_L = 1;
        static double GRABBER_UP_R = 0.75;
        static double GRABBER_DOWN_R = 0.025;
    }

    {
        name = "StoneGrabber";
    }

    public StoneGrabber(Robot robot) {
        super(robot);
    }

    @Override
    public void registerHardware(HardwareMap hwmap) {
        super.registerHardware(hwmap);

        //// SERVOS ////
        stone_grabber_l    = hwmap.get(Servo.class, "sg_l");
        stone_grabber_r    = hwmap.get(Servo.class, "sg_r");
    }

    public void startup() {
        release_l();
        release_r();
    }

    @Override
    public void updateTelemetry(Telemetry telemetry) {
        super.updateTelemetry(telemetry);

        telemetry.addData("SGL", TELEMETRY_DECIMAL.format(stone_grabber_l.getPosition()));
        telemetry.addData("SGR", TELEMETRY_DECIMAL.format(stone_grabber_r.getPosition()));
    }

    @Override
    public void update(OpMode opmode) {
        super.update(opmode);
    }


    public void grab_l() {
        stone_grabber_l.setPosition(GRABBER_DOWN_L);
    }
    public void release_l() {
        stone_grabber_l.setPosition(GRABBER_UP_L);
    }


    public void grab_r() {
        stone_grabber_r.setPosition(GRABBER_DOWN_R);
    }
    public void release_r() {
        stone_grabber_r.setPosition(GRABBER_UP_R);
    }
}
