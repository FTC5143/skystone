package org.firstinspires.ftc.teamcode.components.live;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.components.Component;
import org.firstinspires.ftc.teamcode.robots.Robot;

// Dragger Component
// For dragging the baseplate.

public class Dragger extends Component {
    //// SERVOS ////
    Servo left_dragger;
    Servo right_dragger;

    private static final double LEFT_HORIZONTAL = 0.75778666666;
    private static final double LEFT_VERTICAL = 0.64767666666;

    private static final double RIGHT_HORIZONTAL = 0.25222333333;
    private static final double RIGHT_VERTICAL = 0.35633333333;

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

        release();
    }

    @Override
    public void update(OpMode opmode) {
        super.update(opmode);

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

    public void grab() {
        dragger_pos = DOWN;
    }

    public void release() {
        dragger_pos = UP;
    }
}
