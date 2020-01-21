package org.firstinspires.ftc.teamcode.opmodes.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import static org.firstinspires.ftc.teamcode.constants.AutonomousConst.FAR;
import static org.firstinspires.ftc.teamcode.constants.AutonomousConst.RED;

@Autonomous(name="Two Stone Auto Red Foundation", group="autonomous")
public class TwoStoneAutoRedFoundation extends TwoStoneAuto {
    {
        COLOR = RED;
        PARK = FAR;
        FOUNDATION = true;
    }
}
