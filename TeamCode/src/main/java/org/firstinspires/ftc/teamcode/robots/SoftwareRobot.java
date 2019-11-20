package org.firstinspires.ftc.teamcode.robots;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.components.debug.SensorDebug;
import org.firstinspires.ftc.teamcode.components.live.Dragger;
import org.firstinspires.ftc.teamcode.components.live.DriveTrain;
import org.firstinspires.ftc.teamcode.components.live.OCVPhoneCamera;
import org.firstinspires.ftc.teamcode.components.live.Dragger;
import org.firstinspires.ftc.teamcode.components.live.DriveTrain;
import org.firstinspires.ftc.teamcode.components.live.OCVPhoneCamera;
import org.firstinspires.ftc.teamcode.components.debug.SensorDebug;

public class SoftwareRobot extends Robot {

    public DriveTrain   drive_train;
    public Dragger      dragger;
    public SensorDebug  sensor_debug;
    public OCVPhoneCamera phone_camera;

    {
        name = "Deloris";
    }


    public SoftwareRobot(OpMode opmode) {
        super(opmode);

        drive_train     = new DriveTrain(this);
        dragger         = new Dragger(this);
        sensor_debug    = new SensorDebug(this);
        phone_camera    = new OCVPhoneCamera(this);
    }
}