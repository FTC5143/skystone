package org.firstinspires.ftc.teamcode.systems;

/*  Three Wheel Odometry
 *  Local Coordinate System
 *
 *  Copyright 2019 qwertyquerty
 *
 *  Licensed under MIT
 */

public class LocalCoordinateSystem {
    public double x = 0;    /// The approximated x position of the robot relative to where it started
    public double y = 0;    /// The approximated y position of the robot relative to where it started
    public double a = 0;    /// The approximated heading of the robot relative to its initial heading

    private double prev_le;
    private double prev_re;
    private double prev_ce;

    private double WHEEL_DIAMETER = 1.49606299;
    private double WHEEL_CIRCUMFERENCE = WHEEL_DIAMETER * Math.PI;
    private double ENCODER_CPR = 1440;
    private double INCHES_PER_COUNT = WHEEL_CIRCUMFERENCE / ENCODER_CPR;
    private double ROBOT_RADIUS = 15.75;

    public void update(double le, double re, double ce) {
        double ld = le-prev_le;
        double rd = re-prev_re;
        double cd = ce-prev_ce;

        double dc = ((rd*INCHES_PER_COUNT + ld*INCHES_PER_COUNT) / 2);
        double ph = (ld*INCHES_PER_COUNT - rd*INCHES_PER_COUNT) / ROBOT_RADIUS;
        y += dc * Math.cos(a + (ph / 2)) - (cd*INCHES_PER_COUNT) * Math.sin(a + (ph / 2));
        x += dc * Math.sin(a + (ph / 2)) + (cd*INCHES_PER_COUNT) * Math.cos(a + (ph / 2));
        a += ph;

        prev_le = le;
        prev_re = re;
        prev_ce = ce;
    }
}
