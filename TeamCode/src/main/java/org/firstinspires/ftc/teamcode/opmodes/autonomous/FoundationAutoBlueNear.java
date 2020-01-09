package org.firstinspires.ftc.teamcode.opmodes.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import static org.firstinspires.ftc.teamcode.constants.AutonomousConst.BLUE;
import static org.firstinspires.ftc.teamcode.constants.AutonomousConst.NEAR;

@Autonomous(name="Foundation Auto Blue Near", group="autonomous")
public class FoundationAutoBlueNear extends FoundationAuto {
    {
        COLOR = BLUE;
        PARK = NEAR;
    }
}
