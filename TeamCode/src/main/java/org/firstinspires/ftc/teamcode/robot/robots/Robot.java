package org.firstinspires.ftc.teamcode.robot.robots;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;
import org.firstinspires.ftc.teamcode.robot.components.Component;

import java.util.ArrayList;

public class Robot {

    HardwareMap hwmap;
    public OpMode opmode;

    ArrayList<Component> components = new ArrayList<>();

    public String name;

    public Robot(OpMode opmode) {
        this.opmode = opmode;
        this.hwmap  = opmode.hardwareMap;
    }

    public void startup() {
        for (Component component : components) {
            component.startup();
        }
    }

    public void shutdown() {
        for (Component component : components) {
            component.shutdown();
        }
    }

    public void update() {
        for (Component component : components) {
            component.update(opmode);
        }
    }

    public void registerComponent(Component component) {
        component.registerHardware(hwmap);
        components.add(component);
    }
}
