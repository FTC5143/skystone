package org.firstinspires.ftc.teamcode.robot.components.debug;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.TouchSensor;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.robot.Robot;
import org.firstinspires.ftc.teamcode.robot.components.Component;

// Feeder Component
// Feeds in blocks using the spinner wheels, and uses a touch sensor to detect the blocks

public class Feeder extends Component {
    //// MOTORS ////
    DcMotor left_spinner;    // The left feeder wheel on the take-in device
    DcMotor right_spinner;   // The right feeder wheel on the take-in device

    //// SENSORS ////
    TouchSensor block_detector; // The touch sensor to detect a block in the chamber

    {
        name = "Feeder";
    }

    public Feeder(Robot robot) {
        super(robot);
    }

    @Override
    public void registerHardware(HardwareMap hwmap) {
        super.registerHardware(hwmap);

        //// MOTORS ////
        left_spinner    = hwmap.get(DcMotor.class, "left_spinner");
        right_spinner   = hwmap.get(DcMotor.class, "right_spinner");

        //// SENSORS ////
        block_detector  = hwmap.get(TouchSensor.class, "block_detector");
    }

    @Override
    protected void updateTelemetry(Telemetry telemetry) {
        statusString = "LS: "+left_spinner.getPower()+" | RS: "+right_spinner.getPower()+" | BD: "+block_detector.isPressed();
        super.updateTelemetry(telemetry);
    }
}
