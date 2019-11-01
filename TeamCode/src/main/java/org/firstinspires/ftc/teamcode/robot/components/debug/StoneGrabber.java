package org.firstinspires.ftc.teamcode.robot.components.debug;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.robot.components.Component;
import org.firstinspires.ftc.teamcode.robot.robots.Robot;

public class StoneGrabber extends Component {
    Servo stone_grabber;

    public double grabber_target = 0.8;

    public static final double grabber_up = 0.8;
    public static final double grabber_down = 0.5;

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
        stone_grabber.setPosition(grabber_target);
    }

    public void grab() {
        grabber_target = grabber_down;
        stone_grabber.setPosition(grabber_target);
    }

    public void release() {
        grabber_target = grabber_up;
        stone_grabber.setPosition(grabber_target);
    }
}
