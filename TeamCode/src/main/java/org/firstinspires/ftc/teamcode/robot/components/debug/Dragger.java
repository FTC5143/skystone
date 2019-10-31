package org.firstinspires.ftc.teamcode.robot.components.debug;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.robot.robots.Robot;
import org.firstinspires.ftc.teamcode.robot.components.Component;

// Dragger Component
// For dragging the baseplate.

public class Dragger extends Component {
    //// SERVOS ////
    Servo left_dragger;
    Servo right_dragger;

    public double left_target = 0;
    public double right_target = 0;

    private static final double LEFT_OPEN = 0.65;
    private static final double LEFT_CLOSE = 0.97;

    private static final double RIGHT_OPEN = 0.37;
    private static final double RIGHT_CLOSE = 0.05;



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
        left_target = LEFT_CLOSE;
        right_target = RIGHT_CLOSE;
        left_dragger.setPosition(left_target);
        right_dragger.setPosition(right_target);
    }

    public void release() {
        left_target = LEFT_OPEN;
        right_target = RIGHT_OPEN;
        left_dragger.setPosition(left_target);
        right_dragger.setPosition(right_target);
    }
}
