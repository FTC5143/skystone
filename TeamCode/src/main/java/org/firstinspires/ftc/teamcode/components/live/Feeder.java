package org.firstinspires.ftc.teamcode.components.live;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.components.Component;
import org.firstinspires.ftc.teamcode.robots.Robot;

// Feeder Component
// Feeds in blocks using quick spinning compliant wheels

public class Feeder extends Component {
    //// MOTORS ////
    DcMotor left_spinner;    // The left feeder wheel on the intake device
    DcMotor right_spinner;   // The right feeder wheel on the intake device

    // For caching motor powers so we only write once every update loop
    private double lp_speed;
    private double rp_speed;

    private double lp_cache;
    private double rp_cache;

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

    }

    @Override
    public void update(OpMode opmode) {
        super.update(opmode);

        if (lp_speed != lp_cache) {
            left_spinner.setPower(lp_speed);
            lp_cache = lp_speed;
        }

        if (rp_speed != rp_cache) {
            right_spinner.setPower(-rp_speed);
            rp_cache = rp_speed;
        }

    }

    // Spin both wheels at the specified power
    public void spin(double power) {
        spin(power, power);
    }

    // Spin each wheel independently
    public void spin(double lp, double rp) {
        lp_speed = lp;
        rp_speed = rp;
    }
}
