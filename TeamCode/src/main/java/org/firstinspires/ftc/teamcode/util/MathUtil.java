package org.firstinspires.ftc.teamcode.util;

public class MathUtil {
    public static double angle_wrap(double angle) {
        return Math.atan2(Math.sin(angle), Math.cos(angle));
    }

    public static double signum(double n) {
        if (n == 0) return 1.0;
        else return (double) Math.signum((float) n);
    }
}
