package org.firstinspires.ftc.teamcode.systems.pathfollowing;

import com.acmerobotics.roadrunner.geometry.Vector2d;

public class CurvePoint {

    public Point pos;

    public double speed = 1;
    public double follow_radius = 5;

    public boolean passed = false;

    public CurvePoint(double x, double y) {
        this(new Point(x, y));
    }

    public CurvePoint(Point point) {
        this.pos = point;
    }

    public CurvePoint speed(double speed) {
        this.speed = speed;
        return this;
    }

}
