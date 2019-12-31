package org.firstinspires.ftc.teamcode.systems;

/*  Three Wheel Odometry
 *  Local Coordinate System
 *
 *  Copyright 2019 qwertyquerty
 *
 *  Licensed under MIT
 */

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.geometry.Pose2d;

import org.firstinspires.ftc.teamcode.util.DashboardUtil;

public class LocalCoordinateSystem {
    public double x = 0;    // The approximated x position of the robot relative to where it started
    public double y = 0;    // The approximated y position of the robot relative to where it started
    public double a = 0;    // The approximated heading of the robot relative to its initial heading

    private double prev_le;
    private double prev_re;
    private double prev_ce;

    private double WHEEL_DIAMETER       = 1.49606299;   // Diameter of the omniwheels
    private double ENCODER_CPR          = 1440;         // Counts per full rotation of an encoder
    private double ROBOT_DIAMETER       = 15.75;        // Distance between the left and right encoder (diameter) in inches
    private double CENTER_WHEEL_OFFSET  = 7;            // Distance of the center encoder to the line made between the left and right encoders (radius) in inches

    private double WHEEL_CIRCUMFERENCE  = WHEEL_DIAMETER * Math.PI;
    private double INCHES_PER_COUNT     = WHEEL_CIRCUMFERENCE / ENCODER_CPR;

    FtcDashboard dashboard = FtcDashboard.getInstance();

    public void update(double le, double re, double ce) {

        // Calculate encoder deltas
        double ld = le-prev_le;
        double rd = re-prev_re;
        double cd = ce-prev_ce;


        // Calculate phi, or the delta of our angle
        double ph = (rd*INCHES_PER_COUNT - ld*INCHES_PER_COUNT) / ROBOT_DIAMETER;

        // The arclength of movement forward/backward
        double dc = ((rd*INCHES_PER_COUNT + ld*INCHES_PER_COUNT) / 2);

        // The arclength of movement left/right
        double sc = cd - (ph*CENTER_WHEEL_OFFSET);

        y += dc * Math.cos(a + (ph / 2)) - (sc*INCHES_PER_COUNT) * Math.sin(a + (ph / 2));
        x += dc * Math.sin(a + (ph / 2)) + (sc*INCHES_PER_COUNT) * Math.cos(a + (ph / 2));
        a += ph;

        // Used to calculate deltas for next loop
        prev_le = le;
        prev_re = re;
        prev_ce = ce;

        TelemetryPacket packet = new TelemetryPacket();

        DashboardUtil.drawRobot(packet.fieldOverlay(), new Pose2d(y, x, a));

        dashboard.sendTelemetryPacket(packet);

    }
}
