package org.firstinspires.ftc.teamcode.robot.components.debug;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.robot.robots.Robot;
import org.firstinspires.ftc.teamcode.robot.components.Component;

// Elevator lifts the stone and extender up
// Extender extends over the tower, and the grabber releases the stone

public class Lift extends Component {
    
    //// MOTORS ////
    private DcMotor lift_l;
    private DcMotor lift_r;


    {
        name = "Lift";
    }

    public Lift(Robot robot)
    {
        super(robot);
    }

    @Override
    public void registerHardware (HardwareMap hwmap)
    {
        super.registerHardware(hwmap);

        //// MOTORS ////
        lift_l     = hwmap.get(DcMotor.class, "lift_l");
        lift_r     = hwmap.get(DcMotor.class, "lift_r");

    }

    @Override
    public void startup() {
        super.startup();

        lift_l.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        lift_r.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

    }

    @Override
    protected void updateTelemetry(Telemetry telemetry) {
        super.updateTelemetry(telemetry);

        telemetry.addData("LL SPEED",TELEMETRY_DECIMAL.format(lift_l.getPower()));
        telemetry.addData("RL SPEED",TELEMETRY_DECIMAL.format(lift_r.getPower()));

        telemetry.addData("LL TURNS",TELEMETRY_DECIMAL.format(lift_l.getCurrentPosition()));
        telemetry.addData("RL TURNS",TELEMETRY_DECIMAL.format(lift_r.getCurrentPosition()));
    }

    public void elevate(double speed) {
        lift_l.setPower(speed);
        lift_r.setPower(speed);
    }
}
