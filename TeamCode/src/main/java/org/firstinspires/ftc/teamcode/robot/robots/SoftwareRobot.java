package org.firstinspires.ftc.teamcode.robot.robots;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.robot.components.debug.Dragger;
import org.firstinspires.ftc.teamcode.robot.components.debug.DriveTrain;
import org.firstinspires.ftc.teamcode.robot.components.debug.SensorDebug;

public class SoftwareRobot extends Robot {

    public DriveTrain drive_train;
    public Dragger dragger;
    public SensorDebug sensor_debug;

    {
        name = "Deloris";
    }


    public SoftwareRobot(OpMode opmode) {
        super(opmode);

        drive_train = new DriveTrain(this);
        dragger     = new Dragger(this);
        sensor_debug = new SensorDebug(this);
    }
}
