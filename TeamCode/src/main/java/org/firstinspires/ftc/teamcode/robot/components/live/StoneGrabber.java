package org.firstinspires.ftc.teamcode.robot.components.live;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.robot.components.Component;
import org.firstinspires.ftc.teamcode.robot.robots.Robot;

public class StoneGrabber extends Component {
    Servo stone_grabber;

    public static final double GRABBER_UP = 0.05;
    public static final double GRABBER_DOWN = 0.45;

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
        stone_grabber    = hwmap.get(Servo.class, "stone_grabber");
        release();
    }

    @Override
    protected void updateTelemetry(Telemetry telemetry) {
        super.updateTelemetry(telemetry);

        telemetry.addData("SG", TELEMETRY_DECIMAL.format(stone_grabber.getPosition()));
    }

    @Override
    public void update(OpMode opmode) {
        super.update(opmode);
    }

    public void inc_position(double amt) {
        stone_grabber.setPosition(stone_grabber.getPosition()+amt);
    }

    public void grab() {
        stone_grabber.setPosition(GRABBER_DOWN);
    }

    public void release() {
        stone_grabber.setPosition(GRABBER_UP);
    }
}
