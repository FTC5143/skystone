package org.firstinspires.ftc.teamcode.robot.components.unused;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.robot.robots.Robot;
import org.firstinspires.ftc.teamcode.robot.components.Component;

public class MineralArm extends Component {

    //// MOTORS ////
    private DcMotor extender;
    private DcMotor lifter;

    //// SERVOS ////
    private Servo spindle;
    private Servo flapper;

    {
        name = "Mineral Arm";
    }

    public MineralArm(Robot robot) {
        super(robot);
    }

    @Override
    public void registerHardware(HardwareMap hwmap) {
        super.registerHardware(hwmap);

        //// MOTORS ////
        extender    = hwmap.get(DcMotor.class, "extender");
        lifter      = hwmap.get(DcMotor.class, "lifter");

        //// SERVOS ////
        spindle     = hwmap.get(Servo.class, "spindle");
        flapper     = hwmap.get(Servo.class, "flapper");
    }
}
