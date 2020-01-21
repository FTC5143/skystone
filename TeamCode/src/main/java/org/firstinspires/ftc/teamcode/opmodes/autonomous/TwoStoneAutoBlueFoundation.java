package org.firstinspires.ftc.teamcode.opmodes.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import static org.firstinspires.ftc.teamcode.constants.AutonomousConst.BLUE;
import static org.firstinspires.ftc.teamcode.constants.AutonomousConst.FAR;

@Autonomous(name="Two Stone Auto Blue Foundation", group="autonomous")
public class TwoStoneAutoBlueFoundation extends TwoStoneAuto {
    {
        COLOR = BLUE;
        PARK = FAR;
        FOUNDATION = true;
    }
}
