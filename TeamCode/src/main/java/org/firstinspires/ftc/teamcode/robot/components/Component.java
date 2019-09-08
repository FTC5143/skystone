package org.firstinspires.ftc.teamcode.robot.components;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class Component {
    // The name of the Component, used in telemetry
    String name = "Component";

    // The string that appears in telemetry
    String statusString;


    public Component() {

    }

    // Trying to stray away from using the constructors at all in child classes
    public Component(HardwareMap hwMap) {
        registerHardware(hwMap);
    }

    // Where all hardware used in the component is registered to its respective variable
    protected void registerHardware(HardwareMap hwMap) {

    }

    protected void updateTelemetry(Telemetry telemetry) {
        telemetry.addData("CMP "+name, statusString);
    }
}
