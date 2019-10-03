package org.firstinspires.ftc.teamcode.robot.components.debug;

import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.TouchSensor;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.robot.components.Component;
import org.firstinspires.ftc.teamcode.robot.robots.Robot;

public class SensorDebug extends Component {

    ColorSensor color_sensor;
    TouchSensor touch_sensor;
    DistanceSensor distance_sensor;

    {
        name = "Sensor Debug";
    }

    public SensorDebug(Robot robot) {
        super(robot);
    }

    @Override
    public void registerHardware(HardwareMap hwmap) {
        super.registerHardware(hwmap);

        color_sensor = hwmap.get(ColorSensor.class, "color_sensor");
        touch_sensor = hwmap.get(TouchSensor.class, "touch_sensor");
    }

    @Override
    protected void updateTelemetry(Telemetry telemetry) {
        super.updateTelemetry(telemetry);

        telemetry.addData("color", color_sensor.red()+","+color_sensor.green()+","+color_sensor.blue());
        telemetry.addData("touch", touch_sensor.isPressed());

    }


    public boolean isSkystone() {
        return (color_sensor.red() + color_sensor.green() + color_sensor.blue()) < 50;
    }
}
