package org.firstinspires.ftc.teamcode.components.live;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.components.Component;
import org.firstinspires.ftc.teamcode.robots.Robot;

import static org.firstinspires.ftc.teamcode.components.live.DraggerConfig.*;

// Dragger Component
// For dragging the foundation

@Config
class DraggerConfig {
    // The up positions of the dragger servos, for when not dragging the foundation
    public static double LEFT_HORIZONTAL = 0.75278666666;
    public static double RIGHT_HORIZONTAL = 0.25222333333;

    // The down positions of the dragger servos, for when dragging the foundation
    public static double RIGHT_VERTICAL = 0.35633333333;
    public static double LEFT_VERTICAL = 0.64767666666;
}

public class Dragger extends Component {
    //// SERVOS ////
    Servo left_dragger;
    Servo right_dragger;


    final int DOWN = 0;
    final int UP = 1;

    double dragger_pos;
    double dragger_pos_cache;

    {
        name = "Dragger";
    }

    public Dragger(Robot robot) {
        super(robot);
    }

    @Override
    public void registerHardware(HardwareMap hwmap) {
        super.registerHardware(hwmap);

        //// SERVOS ////
        left_dragger    = hwmap.get(Servo.class, "left_dragger");
        right_dragger   = hwmap.get(Servo.class, "right_dragger");

    }

    @Override
    public void startup() {
        super.startup();

        // Upon initialization, set the servos in the up position
        release();
    }

    @Override
    public void update(OpMode opmode) {
        super.update(opmode);

        // Caching servo positions so we only write to them every update loop when they have changed
        if(dragger_pos != dragger_pos_cache) {

            if(dragger_pos == UP) {
                left_dragger.setPosition(LEFT_HORIZONTAL);
                right_dragger.setPosition(RIGHT_HORIZONTAL);
            } else if (dragger_pos == DOWN) {
                left_dragger.setPosition(LEFT_VERTICAL);
                right_dragger.setPosition(RIGHT_VERTICAL);
            }

            dragger_pos_cache = dragger_pos;

        }

    }

    @Override
    public void updateTelemetry(Telemetry telemetry) {
        super.updateTelemetry(telemetry);

        telemetry.addData("LG", TELEMETRY_DECIMAL.format(left_dragger.getPosition()));
        telemetry.addData("RG", TELEMETRY_DECIMAL.format(right_dragger.getPosition()));

    }

    // Put the foundation draggers down to drag the foundation
    public void grab() {
        dragger_pos = DOWN;
    }

    // Bring the foundation draggers up to release the foundation
    public void release() {
        dragger_pos = UP;
    }
}
