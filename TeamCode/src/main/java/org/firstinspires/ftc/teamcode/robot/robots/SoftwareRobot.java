package org.firstinspires.ftc.teamcode.robot.robots;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.robot.components.debug.Dragger;
import org.firstinspires.ftc.teamcode.robot.components.debug.DriveTrain;

public class SoftwareRobot extends Robot {

    public DriveTrain drive_train;
    public Dragger dragger;

    {
        name = "Deloris";
    }


    public SoftwareRobot(OpMode opmode) {
        super(opmode);

        drive_train = new DriveTrain(this);
        dragger     = new Dragger(this);
    }
}
