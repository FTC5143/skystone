package org.firstinspires.ftc.teamcode.robot.components;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.TouchSensor;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.robot.Robot;

// Feeder Component
// Feeds in blocks using the spinner wheels, and uses a touch sensor to detect the blocks

public class Feeder extends Component {
    //// MOTORS ////
    DcMotor leftSpinner;    // The left feeder wheel on the take-in device
    DcMotor rightSpinner;   // The right feeder wheel on the take-in device

    //// SENSORS ////
    TouchSensor blockDetector; // The touch sensor to detect a block in the chamber

    {
        name = "Feeder";
    }

    public Feeder(Robot robot) {
        super(robot);
    }

    @Override
    public void registerHardware(HardwareMap hwMap) {
        super.registerHardware(hwMap);

        //// MOTORS ////
        hwMap.get(DcMotor.class, "left_spinner");
        hwMap.get(DcMotor.class, "right_spinner");

        //// SENSORS ////
        hwMap.get(TouchSensor.class, "block_detector");
    }

    @Override
    protected void updateTelemetry(Telemetry telemetry) {
        statusString = "LS: "+leftSpinner.getPower()+" | RS: "+rightSpinner.getPower()+" | BD: "+blockDetector.isPressed();
        super.updateTelemetry(telemetry);
    }
}
