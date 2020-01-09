package org.firstinspires.ftc.teamcode.opmodes.autonomous.dead;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

import org.firstinspires.ftc.teamcode.opmodes.autonomous.LiveAutoBase;

import static org.firstinspires.ftc.teamcode.constants.AutonomousConst.FAR;
import static org.firstinspires.ftc.teamcode.constants.AutonomousConst.NEAR;
import static org.firstinspires.ftc.teamcode.constants.AutonomousConst.RED;

@Autonomous(name="Foundation Auto", group="autonomous")
@Disabled
public class FoundationAutoOld extends LiveAutoBase {

    protected static int COLOR = RED;
    protected static int PARK = FAR;

    int x_mod;

    @Override
    public void on_init() {
        x_mod = COLOR == RED ? 1 : -1;
    }

    @Override
    public void on_start() {
        robot.dragger.release();

        robot.drive_train.encoder_drive(1*x_mod,0,0,9, 0.5);

        robot.drive_train.encoder_drive(0,1,0,20, 0.5);

        robot.drive_train.encoder_drive(0,1,0,6, 0.25);

        robot.dragger.grab();
        sleep(500);

        robot.drive_train.encoder_drive(0,-1,0,27, 0.5);

        robot.dragger.release();
        sleep(500);

        robot.drive_train.encoder_drive(-1*x_mod,0,0, 23, 0.5);

        robot.drive_train.encoder_drive(0,1,0, 13, 0.5);

        robot.drive_train.encoder_drive(1*x_mod,0,0, 11, 0.5);

        if(PARK == NEAR) {
            robot.drive_train.encoder_drive(0,-1,0, 13, 0.5);
        }

        robot.drive_train.encoder_drive(-1*x_mod,0,0, 24, 0.5);
    }

    @Override
    public void on_stop() {

    }
}
