package org.firstinspires.ftc.teamcode.systems.pathfollowing;

public class Point {

    public double x;
    public double y;

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Point() {
        this(0, 0);
    }

    public boolean equals(Point p2) {
        return this.x == p2.x && this.y == p2.y;
    }

    public boolean within_circle(double x, double y, double r) {
        return Math.abs(Math.hypot(this.x-x, this.y-y)) <= r;
    }

    public Pose to_pose(double angle) {
        return new Pose(x, y, angle);
    }
}
