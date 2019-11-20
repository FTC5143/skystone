package org.firstinspires.ftc.teamcode.components.live;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.robots.Robot;
import org.firstinspires.ftc.teamcode.components.Component;

// Dragger Component
// For dragging the baseplate.

public class Dragger extends Component {
    //// SERVOS ////
    Servo left_dragger;
    Servo right_dragger;

    public double left_target = 0.65;
    public double right_target = 0.37;

    private static final double LEFT_HORIZONTAL = 0.65;
    private static final double LEFT_VERTICAL = 0.97;

    private static final double RIGHT_HORIZONTAL = 0.37;
    private static final double RIGHT_VERTICAL = 0.05;



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
        release();
    }

    @Override
    protected void updateTelemetry(Telemetry telemetry) {
        super.updateTelemetry(telemetry);

        telemetry.addData("LG", TELEMETRY_DECIMAL.format(left_dragger.getPosition()));
        telemetry.addData("RG", TELEMETRY_DECIMAL.format(right_dragger.getPosition()));
    }

    @Override
    public void update(OpMode opmode) {
        super.update(opmode);
        left_dragger.setPosition(left_target);
        right_dragger.setPosition(right_target);
    }

    public void grab() {
        left_target = LEFT_VERTICAL;
        right_target = RIGHT_VERTICAL;
        left_dragger.setPosition(left_target);
        right_dragger.setPosition(right_target);
    }

    public void release() {
        left_target = LEFT_HORIZONTAL;
        right_target = RIGHT_HORIZONTAL;
        left_dragger.setPosition(left_target);
        right_dragger.setPosition(right_target);
    }
}
