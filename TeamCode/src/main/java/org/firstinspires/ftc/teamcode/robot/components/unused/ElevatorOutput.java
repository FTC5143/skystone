package org.firstinspires.ftc.teamcode.robot.components.unused;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.robot.robots.Robot;
import org.firstinspires.ftc.teamcode.robot.components.Component;

// Elevator lifts the stone and edxtender up
// Extender extends over the twoer, and the grabber releases the stone

public class ElevatorOutput extends Component {
    
    //// MOTORS ////
    private DcMotor elevator;

    //// SERVOS ////
    private Servo extender;
    private Servo grabber;

    {
        name = "Elevator Output";
    }

    public ElevatorOutput(Robot robot)
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
