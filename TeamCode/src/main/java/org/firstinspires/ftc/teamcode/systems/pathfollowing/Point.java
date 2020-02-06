package org.firstinspires.ftc.teamcode.systems.pathfollowing;

import android.support.annotation.Nullable;

public class Point {
    public double x;
    public double y;

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public boolean equals(Point p2) {
        return this.x == p2.x && this.y == p2.y;
    }
}
