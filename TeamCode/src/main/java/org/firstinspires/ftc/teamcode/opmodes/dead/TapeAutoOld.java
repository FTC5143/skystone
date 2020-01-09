package org.firstinspires.ftc.teamcode.opmodes.dead;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

import org.firstinspires.ftc.teamcode.opmodes.autonomous.LiveAutoBase;

import static org.firstinspires.ftc.teamcode.constants.AutonomousConst.FAR;
import static org.firstinspires.ftc.teamcode.constants.AutonomousConst.RED;

@Autonomous(name="Tape Auto", group="autonomous")
@Disabled
public class TapeAutoOld extends LiveAutoBase {

    protected static int COLOR = RED;
    protected static int PARK = FAR;

    int x_mod;

    @Override
    public void on_init() {
        x_mod = COLOR == RED ? 1 : -1;
    }

    @Override
    public void on_start() {
        if (PARK == FAR) {
            robot.drive_train.encoder_drive(0, 1, 0, 18, 0.5);
        }

        robot.drive_train.encoder_drive(1*x_mod,0,0, 24, 0.5);
    }

    @Override
    public void on_stop() { }
}
