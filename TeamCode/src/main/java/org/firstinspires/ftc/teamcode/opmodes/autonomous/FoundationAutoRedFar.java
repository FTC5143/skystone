package org.firstinspires.ftc.teamcode.opmodes.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import static org.firstinspires.ftc.teamcode.constants.AutonomousConst.FAR;
import static org.firstinspires.ftc.teamcode.constants.AutonomousConst.RED;

@Autonomous(name="Foundation Auto Red Far", group="autonomous")
public class FoundationAutoRedFar extends FoundationAuto {
    {
        COLOR = RED;
        PARK = FAR;
    }
}
