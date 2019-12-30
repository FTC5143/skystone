package org.firstinspires.ftc.teamcode.components.debug.drivetrain;


import android.support.annotation.NonNull;

import com.acmerobotics.roadrunner.control.PIDCoefficients;
import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;

import org.firstinspires.ftc.teamcode.util.LynxModuleUtil;
import org.openftc.revextensions2.ExpansionHubEx;
import org.openftc.revextensions2.ExpansionHubMotor;
import org.openftc.revextensions2.RevBulkData;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.firstinspires.ftc.teamcode.components.debug.drivetrain.DriveConstants.MOTOR_VELO_PID;
import static org.firstinspires.ftc.teamcode.components.debug.drivetrain.DriveConstants.RUN_USING_ENCODER;
import static org.firstinspires.ftc.teamcode.components.debug.drivetrain.DriveConstants.encoderTicksToInches;
import static org.firstinspires.ftc.teamcode.components.debug.drivetrain.DriveConstants.getMotorVelocityF;



/*

 * Optimized mecanum drive implementation for REV ExHs. The time savings may significantly improve

 * trajectory following performance with moderate additional complexity.

 */

public class SampleMecanumDriveREVOptimized extends SampleMecanumDriveBase {

    private ExpansionHubEx hub;

    private ExpansionHubMotor leftFront, leftRear, rightRear, rightFront;

    private List<ExpansionHubMotor> motors;

    private BNO055IMU imu;



    public SampleMecanumDriveREVOptimized(HardwareMap hardwareMap) {

        super();



        LynxModuleUtil.ensureMinimumFirmwareVersion(hardwareMap);



        leftFront = hardwareMap.get(ExpansionHubMotor.class, "leftFront");

        leftRear = hardwareMap.get(ExpansionHubMotor.class, "leftRear");

        rightRear = hardwareMap.get(ExpansionHubMotor.class, "rightRear");

        rightFront = hardwareMap.get(ExpansionHubMotor.class, "rightFront");



        motors = Arrays.asList(leftFront, leftRear, rightRear, rightFront);



        for (ExpansionHubMotor motor : motors) {

            if (RUN_USING_ENCODER) {

                motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

            }

            motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        }



        if (RUN_USING_ENCODER && MOTOR_VELO_PID != null) {

            setPIDCoefficients(DcMotor.RunMode.RUN_USING_ENCODER, MOTOR_VELO_PID);

        }



        // TODO: reverse any motors using DcMotor.setDirection()



        // TODO: if desired, use setLocalizer() to change the localization method

        // for instance, setLocalizer(new ThreeTrackingWheelLocalizer(...));

    }



    @Override

    public PIDCoefficients getPIDCoefficients(DcMotor.RunMode runMode) {

        PIDFCoefficients coefficients = leftFront.getPIDFCoefficients(runMode);

        return new PIDCoefficients(coefficients.p, coefficients.i, coefficients.d);

    }



    @Override

    public void setPIDCoefficients(DcMotor.RunMode runMode, PIDCoefficients coefficients) {

        for (ExpansionHubMotor motor : motors) {

            motor.setPIDFCoefficients(runMode, new PIDFCoefficients(

                    coefficients.kP, coefficients.kI, coefficients.kD, getMotorVelocityF()

            ));

        }

    }



    @NonNull

    @Override

    public List<Double> getWheelPositions() {

        RevBulkData bulkData = hub.getBulkInputData();



        if (bulkData == null) {

            return Arrays.asList(0.0, 0.0, 0.0, 0.0);

        }



        List<Double> wheelPositions = new ArrayList<>();

        for (ExpansionHubMotor motor : motors) {

            wheelPositions.add(encoderTicksToInches(bulkData.getMotorCurrentPosition(motor)));

        }

        return wheelPositions;

    }



    @Override

    public List<Double> getWheelVelocities() {

        RevBulkData bulkData = hub.getBulkInputData();



        if (bulkData == null) {

            return Arrays.asList(0.0, 0.0, 0.0, 0.0);

        }



        List<Double> wheelVelocities = new ArrayList<>();

        for (ExpansionHubMotor motor : motors) {

            wheelVelocities.add(encoderTicksToInches(bulkData.getMotorVelocity(motor)));

        }

        return wheelVelocities;

    }



    @Override

    public void setMotorPowers(double v, double v1, double v2, double v3) {

        leftFront.setPower(v);

        leftRear.setPower(v1);

        rightRear.setPower(v2);

        rightFront.setPower(v3);

    }



    @Override

    public double getRawExternalHeading() {

        return imu.getAngularOrientation().firstAngle;

    }

}