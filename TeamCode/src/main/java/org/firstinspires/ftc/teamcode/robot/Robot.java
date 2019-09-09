package org.firstinspires.ftc.teamcode.robot;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.robot.components.Component;
import org.firstinspires.ftc.teamcode.robot.components.DriveTrain;
import org.firstinspires.ftc.teamcode.robot.components.Feeder;

import java.util.ArrayList;
import java.util.List;

public class Robot {

    HardwareMap hwMap;
    OpMode opmode;

    ArrayList<Component> components = new ArrayList<>();

    DriveTrain driveTrain;
    Feeder feeder;

    public Robot(OpMode opmode) {
        this.opmode = opmode;
        this.hwMap  = opmode.hardwareMap;

        feeder = new Feeder(this);
        driveTrain = new DriveTrain(this);
    }

    public void update() {
        for (Component component : components) {
            component.update(opmode);
        }
    }

    public void registerComponent(Component component) {
        component.registerHardware(hwMap);
        components.add(component);
    }
}
