package org.firstinspires.ftc.teamcode.components.debug;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.control.PIDCoefficients;
import com.acmerobotics.roadrunner.trajectory.constraints.DriveConstraints;
import com.qualcomm.hardware.motors.GoBILDA5202Series;
import com.qualcomm.robotcore.hardware.configuration.typecontainers.MotorConfigurationType;

import org.firstinspires.ftc.teamcode.components.Component;
import org.firstinspires.ftc.teamcode.robots.Robot;

public class RRDriveTrain extends Component {

    @Config
    static class DriveTrainConfig {
        static MotorConfigurationType MOTOR_CONFIG = MotorConfigurationType.getMotorType(GoBILDA5202Series.class);

        static boolean RUN_USING_ENCODER = true;
        static PIDCoefficients MOTOR_VELO_PID = null;

        static double WHEEL_RADIUS = 4;
        static double GEAR_RATIO = 0.5; // output (wheel) speed / input (motor) speed
        static double TRACK_WIDTH = 1;

        static double kV = 1.0 / rpmToVelocity(getMaxRpm());
        static double kA = 0;
        static double kStatic = 0;

        static DriveConstraints BASE_CONSTRAINTS = new DriveConstraints(
                30.0, 30.0, 0.0,
                Math.toRadians(180.0), Math.toRadians(180.0), 0.0
        );

        static double encoderTicksToInches(double ticks) {
            return WHEEL_RADIUS * 2 * Math.PI * GEAR_RATIO * ticks / MOTOR_CONFIG.getTicksPerRev();
        }

        static double rpmToVelocity(double rpm) {
            return rpm * GEAR_RATIO * 2 * Math.PI * WHEEL_RADIUS / 60.0;
        }

        static double getMaxRpm() {
            return MOTOR_CONFIG.getMaxRPM() * (RUN_USING_ENCODER ? MOTOR_CONFIG.getAchieveableMaxRPMFraction() : 1.0);
        }

        static double getTicksPerSec() {
            // note: MotorConfigurationType#getAchieveableMaxTicksPerSecond() isn't quite what we want
            return (MOTOR_CONFIG.getMaxRPM() * MOTOR_CONFIG.getTicksPerRev() / 60.0);
        }

        static double getMotorVelocityF() {
            // see https://docs.google.com/document/d/1tyWrXDfMidwYyP_5H4mZyVgaEswhOC35gvdmP-V-5hA/edit#heading=h.61g9ixenznbx
            return 32767 / getTicksPerSec();
        }
    }

    public RRDriveTrain(Robot robot) {
        super(robot);
    }
}
