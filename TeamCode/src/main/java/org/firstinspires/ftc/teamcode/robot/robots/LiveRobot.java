package org.firstinspires.ftc.teamcode.robot.robots;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.robot.components.debug.DriveTrain;

public class LiveRobot extends Robot {

    public DriveTrain   drive_train;

    {
        name = "Boris";
    }


    public LiveRobot(OpMode opmode) {
        super(opmode);

        drive_train     = new DriveTrain(this);
    }
}
