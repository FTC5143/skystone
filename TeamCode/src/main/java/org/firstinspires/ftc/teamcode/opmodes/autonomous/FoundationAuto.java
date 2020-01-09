package org.firstinspires.ftc.teamcode.opmodes.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

import static org.firstinspires.ftc.teamcode.constants.AutonomousConst.BLUE;
import static org.firstinspires.ftc.teamcode.constants.AutonomousConst.FAR;
import static org.firstinspires.ftc.teamcode.constants.AutonomousConst.NEAR;

@Autonomous(name="Foundation Auto", group="autonomous")
@Disabled
public class FoundationAuto extends LiveAutoBase {

    protected static int COLOR = BLUE;
    protected static int PARK = FAR;


    @Override
    public void on_init() {
        robot.drive_train.color = COLOR;
    }

    @Override
    public void on_start() {

        robot.drive_train.odo_move(11, 26, 0, 1);

        robot.drive_train.odo_move(11, 32, 0, 0.5);

        robot.dragger.grab();

        sleep(1000);

        robot.drive_train.odo_move(11, 2, 0, 0.5);

        robot.dragger.release();

        if (PARK == FAR) {

            robot.drive_train.odo_move(-19, 2, 0, 0.5);

            robot.drive_train.odo_move(-19, 26, 0, 0.5);

            robot.drive_train.odo_move(-40, 26, 0, 0.5);

        } else if (PARK == NEAR) {

            robot.drive_train.odo_move(-40, 2, 0, 0.5);

        }

    }

    @Override
    public void on_stop() {

    }
}
