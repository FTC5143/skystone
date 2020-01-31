package org.firstinspires.ftc.teamcode.robots;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.components.live.Dragger;
import org.firstinspires.ftc.teamcode.components.live.DriveTrain;
import org.firstinspires.ftc.teamcode.components.live.Feeder;
import org.firstinspires.ftc.teamcode.components.live.Lift;
import org.firstinspires.ftc.teamcode.components.live.OCVPhoneCamera;

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

    @Override
    public void update() {
        super.update();
    }

}