package org.firstinspires.ftc.teamcode.opmodes.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import static org.firstinspires.ftc.teamcode.constants.AutonomousConst.BLUE;
import static org.firstinspires.ftc.teamcode.constants.AutonomousConst.FAR;
import static org.firstinspires.ftc.teamcode.constants.AutonomousConst.NEAR;
import static org.firstinspires.ftc.teamcode.constants.AutonomousConst.RED;

@Autonomous(name="Tape Auto Blue Near", group="autonomous")
public class TapeAutoBlueNear extends TapeAuto {
    {
        COLOR = BLUE;
        PARK = NEAR;
    }
}
