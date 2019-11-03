package org.firstinspires.ftc.teamcode.robot.components.live;

import com.qualcomm.hardware.rev.RevTouchSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.TouchSensor;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.robot.robots.Robot;
import org.firstinspires.ftc.teamcode.robot.components.Component;

// Feeder Component
// Feeds in blocks using the spinner wheels, and uses a touch sensor to detect the blocks

public class Feeder extends Component {
    //// MOTORS ////
    DcMotor left_spinner;    // The left feeder wheel on the take-in device
    DcMotor right_spinner;   // The right feeder wheel on the take-in device

    //// SENSORS ////
    RevTouchSensor block_detector; // The touch sensor to detect a block in the chamber

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
        block_detector  = hwmap.get(RevTouchSensor.class, "block_detector");
    }

    @Override
    public void startup() {
        super.startup();
    }

    public void spin(double power) {
        spin(power, power);
    }

    public void spin(double lp, double rp) {
        left_spinner.setPower(lp);
        right_spinner.setPower(-rp);
    }

    @Override
    protected void updateTelemetry(Telemetry telemetry) {
        super.updateTelemetry(telemetry);

        telemetry.addData("LS",left_spinner.getPower());
        telemetry.addData("RS",right_spinner.getPower());
        telemetry.addData("BD",block_detector.isPressed());
    }
}
