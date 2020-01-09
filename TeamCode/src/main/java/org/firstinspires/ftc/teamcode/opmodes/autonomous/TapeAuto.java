package org.firstinspires.ftc.teamcode.opmodes.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

import static org.firstinspires.ftc.teamcode.constants.AutonomousConst.BLUE;
import static org.firstinspires.ftc.teamcode.constants.AutonomousConst.FAR;
import static org.firstinspires.ftc.teamcode.constants.AutonomousConst.NEAR;

@Autonomous(name="Tape Auto", group="autonomous")
@Disabled
public class TapeAuto extends LiveAutoBase {

    protected static int COLOR = BLUE;
    protected static int PARK = FAR;


    @Override
    public void on_init() {
        robot.drive_train.color = COLOR;
    }

    @Override
    public void on_start() {

        if (PARK == FAR) {

            robot.drive_train.odo_move(0, 24, 0, 0.5);

            robot.drive_train.odo_move(30, 24, 0, 0.5);

        } else if (PARK == NEAR) {

            robot.drive_train.odo_move(30, 2, 0, 0.5);

        }

    }

    @Override
    public void on_stop() {

    }
}
