package org.firstinspires.ftc.teamcode.robot.robots;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.robot.components.live.Dragger;
import org.firstinspires.ftc.teamcode.robot.components.live.DriveTrain;
import org.firstinspires.ftc.teamcode.robot.components.live.Feeder;
import org.firstinspires.ftc.teamcode.robot.components.live.Lift;
import org.firstinspires.ftc.teamcode.robot.components.live.OCVPhoneCamera;
import org.firstinspires.ftc.teamcode.robot.components.live.StoneGrabber;

public class LiveRobot extends Robot {

    public DriveTrain       drive_train;
    public Lift             lift;
    public OCVPhoneCamera   phone_camera;
    public Dragger          dragger;
    public StoneGrabber     stone_grabber;
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
        stone_grabber   = new StoneGrabber(this);
        feeder          = new Feeder(this);
    }
}
