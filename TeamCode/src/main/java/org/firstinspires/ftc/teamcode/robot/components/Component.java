package org.firstinspires.ftc.teamcode.robot.components;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.robot.Robot;

public class Component {
    // The name of the Component, used in telemetry
    protected String name = "Component";

    // The string that appears in telemetry
    protected String statusString;

    // The robot that we are a part of
    protected Robot robot;

    // Trying to stray away from using the constructors at all in child classes
    public Component(Robot robot) {
        this.robot = robot;
        robot.registerComponent(this);
    }

    // Where all hardware used in the component is registered to its respective variable
    public void registerHardware(HardwareMap hwMap) {

    }

    // Called every time the robot update method is called
    public void update(OpMode opmode) {
        updateTelemetry(opmode.telemetry);
    }

    // Called on every update. Modify statusString here to update telemetry on the phone
    protected void updateTelemetry(Telemetry telemetry) {
        telemetry.addData("CMP "+name, statusString);
    }
}
