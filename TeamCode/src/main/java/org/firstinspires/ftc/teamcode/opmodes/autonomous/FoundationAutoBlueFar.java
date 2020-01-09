package org.firstinspires.ftc.teamcode.opmodes.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import static org.firstinspires.ftc.teamcode.constants.AutonomousConst.BLUE;
import static org.firstinspires.ftc.teamcode.constants.AutonomousConst.FAR;

@Autonomous(name="Foundation Auto Blue Far", group="autonomous")
public class FoundationAutoBlueFar extends FoundationAuto {
    {
        COLOR = BLUE;
        PARK = FAR;
    }
}
