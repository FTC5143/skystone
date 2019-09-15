package org.firstinspires.ftc.teamcode.robot.components.Componentjunkyard;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.robot.Robot;
import org.firstinspires.ftc.teamcode.robot.components.Component;

public class ElevatorOuttake extends Component {

    /*
    elevator lifts stone and extender up
    extender extends over the tower
    grabber releases stone
    *\
     */
    /// MOTORS ////
    private DcMotor elevator;

    //// SERVOS ////
    private Servo extender;
    private Servo grabber;

    {
        name = "ElevatorOuttake";
    }

    public ElevatorOuttake (Robot robot)
    {
        super(robot);
    }

    @Override
    public void registerHardware (HardwareMap hwmap)
    {
        super.registerHardware(hwmap);

        //// MOTORS ////
        elevator     = hwmap.get(DcMotor.class, "elevator");

        //// SERVOS ////
        extender     = hwmap.get(Servo.class, "extender");
        grabber      = hwmap.get(Servo.class, "grabber");

    }


}
