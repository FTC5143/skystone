package org.firstinspires.ftc.teamcode.robot;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.robot.components.Component;
import org.firstinspires.ftc.teamcode.robot.components.debug.DriveTrain;
import org.firstinspires.ftc.teamcode.robot.components.debug.Feeder;

import java.util.ArrayList;

public class Robot {

    HardwareMap hwmap;
    OpMode opmode;

    ArrayList<Component> components = new ArrayList<>();

    public DriveTrain drive_train;
    public Feeder feeder;

    public Robot(OpMode opmode) {
        this.opmode = opmode;
        this.hwmap  = opmode.hardwareMap;

        feeder      = new Feeder(this);
        drive_train = new DriveTrain(this);
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
