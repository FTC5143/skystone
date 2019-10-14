package org.firstinspires.ftc.teamcode.robot.components.debug;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.robot.robots.Robot;
import org.firstinspires.ftc.teamcode.robot.components.Component;

// Dragger Component
// For dragging the baseplate.

public class Dragger extends Component {
    //// SERVOS ////
    Servo left_dragger;
    Servo right_dragger;

    {
        name = "Dragger";
    }

    public Dragger(Robot robot) {
        super(robot);
    }

    @Override
    public void registerHardware(HardwareMap hwmap) {
        super.registerHardware(hwmap);

        //// SERVOS ////
        left_dragger    = hwmap.get(Servo.class, "left_dragger");
        right_dragger   = hwmap.get(Servo.class, "right_dragger");
    }

    @Override
    protected void updateTelemetry(Telemetry telemetry) {
        super.updateTelemetry(telemetry);

        telemetry.addData("LG", TELEMETRY_DECIMAL.format(left_dragger.getPosition()));
        telemetry.addData("RG", TELEMETRY_DECIMAL.format(right_dragger.getPosition()));
    }

    public void grab() {
        left_dragger.setPosition(1);
        right_dragger.setPosition(1);
    }

    public void release() {
        left_dragger.setPosition(0);
        right_dragger.setPosition(0);
    }
}
