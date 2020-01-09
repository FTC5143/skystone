package org.firstinspires.ftc.teamcode.opmodes.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import static org.firstinspires.ftc.teamcode.constants.AutonomousConst.NEAR;
import static org.firstinspires.ftc.teamcode.constants.AutonomousConst.RED;

@Autonomous(name="Foundation Auto Red Near", group="autonomous")
public class FoundationAutoRedNear extends FoundationAuto {
    {
        COLOR = RED;
        PARK = NEAR;
    }
}
