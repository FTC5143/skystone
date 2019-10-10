package org.firstinspires.ftc.teamcode.robot.robots;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;
import org.firstinspires.ftc.teamcode.robot.components.Component;

import java.util.ArrayList;

public class Robot {

    HardwareMap hwmap;
    public OpMode opmode;
    public LinearOpMode lopmode;

    ArrayList<Component> components = new ArrayList<>();

    public String name;

    public Robot(OpMode opmode) { // FIRST THIS IS *YOUR* FAULT IT HAS TO BE THIS WAY AND I HATE IT AND YOU SHOULD BE ASHAMED
        if (opmode instanceof LinearOpMode) {
            this.lopmode = (LinearOpMode) opmode;
        } else {
            this.opmode = opmode;
        }

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
