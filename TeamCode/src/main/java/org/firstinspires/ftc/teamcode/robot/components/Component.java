package org.firstinspires.ftc.teamcode.robot.components;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.robot.Robot;

public class Component {
    // The name of the Component, used in telemetry
    String name = "Component";

    // The string that appears in telemetry
    String statusString;

    // The robot that we are a part of
    Robot robot;


    public Component() {

    }

    // Trying to stray away from using the constructors at all in child classes
    public Component(Robot robot) {
        this.robot = robot;
        robot.registerComponent(this);
    }

    // Where all hardware used in the component is registered to its respective variable
    public void registerHardware(HardwareMap hwMap) {

    }

    public void update(OpMode opmode) {
        updateTelemetry(opmode.telemetry);
    }

    protected void updateTelemetry(Telemetry telemetry) {
        telemetry.addData("CMP "+name, statusString);
    }
}
