package org.firstinspires.ftc.teamcode.robot.robots;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.robot.components.debug.Dragger;
import org.firstinspires.ftc.teamcode.robot.components.debug.DriveTrain;
import org.firstinspires.ftc.teamcode.robot.components.debug.Feeder;
import org.firstinspires.ftc.teamcode.robot.components.debug.Lift;
import org.firstinspires.ftc.teamcode.robot.components.debug.OCVPhoneCamera;
import org.firstinspires.ftc.teamcode.robot.components.debug.PhoneCamera;

public class LiveRobot extends Robot {

    public DriveTrain       drive_train;
    public Lift             lift;
    public OCVPhoneCamera   phone_camera;
    public Dragger          dragger;
    public Feeder           feeder;

    {
        name = "Boris";
    }


    public LiveRobot(OpMode opmode) {
        super(opmode);

        drive_train     = new DriveTrain(this);
        lift            = new Lift(this);
        phone_camera    = new OCVPhoneCamera(this);
        dragger         = new Dragger(this);
        feeder          = new Feeder(this);
    }
}
