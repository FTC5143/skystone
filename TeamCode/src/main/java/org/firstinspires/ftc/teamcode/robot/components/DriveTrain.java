package org.firstinspires.ftc.teamcode.robot.components;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

// Drive Train component
// Includes: Drive Motors, IMU

public class DriveTrain extends Component {
    //// MOTORS ////
    private DcMotor drive_lf;   // Left-Front drive motor
    private DcMotor drive_rf;   // Right-Front drive motor
    private DcMotor drive_lb;   // Left-Back drive motor
    private DcMotor drive_rb;   // Right-Back drive motor

    //// SENSORS ////
    private BNO055IMU imu;      // Internal REV IMU, which we might use to drive straight

    {
        name = "Drive Train";
    }

    @Override
    public void registerHardware(HardwareMap hwMap) {
        super.registerHardware(hwMap);

        drive_lf    = hwMap.get(DcMotor.class, "drive_lf");
        drive_rf    = hwMap.get(DcMotor.class, "drive_rf");
        drive_lb    = hwMap.get(DcMotor.class, "drive_lb");
        drive_rb    = hwMap.get(DcMotor.class, "drive_rb");

        imu         = hwMap.get(BNO055IMU.class, "drive_imu");
    }

    @Override
    protected void updateTelemetry(Telemetry telemetry) {
        statusString = "LF: "+drive_lf.getPower()+" | RF: "+drive_rf.getPower()+" | LB: "+drive_lb.getPower()+" | RB: "+drive_rb.getPower();
        super.updateTelemetry(telemetry);
    }
}
