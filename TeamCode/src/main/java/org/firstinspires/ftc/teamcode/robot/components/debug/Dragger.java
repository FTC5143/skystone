package org.firstinspires.ftc.teamcode.robot.components.debug;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.robot.robots.Robot;
import org.firstinspires.ftc.teamcode.robot.components.Component;

// Feeder Component
// Feeds in blocks using the spinner wheels, and uses a touch sensor to detect the blocks

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
        statusString = "LG: "+TELEMETRY_DECIMAL.format(left_dragger.getPosition())+" | RG: "+TELEMETRY_DECIMAL.format(right_dragger.getPosition());
        super.updateTelemetry(telemetry);
    }
}
