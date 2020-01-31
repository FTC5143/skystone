package org.firstinspires.ftc.teamcode.systems;

/*  Three Wheel Odometry
 *  Local Coordinate System
 *
 *  Copyright 2019 qwertyquerty
 *
 *  Licensed under MIT
 */

public class LocalCoordinateSystem {
    public double x = 0;    // The approximated x position of the robot relative to where it started
    public double y = 0;    // The approximated y position of the robot relative to where it started
    public double a = 0;    // The approximated heading of the robot relative to its initial heading

    public double prev_le;
    public double prev_re;
    public double prev_ce;

    private double WHEEL_DIAMETER       = 1.48982939421;    // Diameter of the omniwheels
    private double ENCODER_CPR          = 1440;             // Counts per full rotation of an encoder
    private double ROBOT_DIAMETER       = 15.7075609922;    //15.74735 //15.53           // Distance between the left and right encoder (diameter) in inches
    private double CENTER_WHEEL_OFFSET  = 7.702;            //7.719 //7.375 Distance of the center encoder to the line made between the left and right encoders (radius) in inches

    private double WHEEL_CIRCUMFERENCE  = WHEEL_DIAMETER * Math.PI;
    private double INCHES_PER_COUNT     = WHEEL_CIRCUMFERENCE / ENCODER_CPR;

    public void update(double le, double re, double ce) {

        // Calculate encoder deltas
        double ld = le - prev_le;
        double rd = re - prev_re;
        double cd = ce - prev_ce;


        // Calculate phi, or the delta of our angle
        double ph = (rd * INCHES_PER_COUNT - ld * INCHES_PER_COUNT) / ROBOT_DIAMETER;

        // The arclength of movement forward/backward
        double dc = (rd * INCHES_PER_COUNT + ld * INCHES_PER_COUNT) / 2;

        // The arclength of movement left/right
        double sc = (cd * INCHES_PER_COUNT) + (ph * CENTER_WHEEL_OFFSET);

        // Calculate the new position of the robot by adding the arc vector to the absolute pos
        y += (dc * Math.cos(a + (ph / 2))) - (sc * Math.sin(a + (ph / 2)));
        x -= (dc * Math.sin(a + (ph / 2))) + (sc * Math.cos(a + (ph / 2)));

        // Calculate the new angle of the robot using the difference between the left and right encoder
        a = (re * INCHES_PER_COUNT - le * INCHES_PER_COUNT) / ROBOT_DIAMETER;

        // Used to calculate deltas for next loop
        prev_le = le;
        prev_re = re;
        prev_ce = ce;

    }
}
