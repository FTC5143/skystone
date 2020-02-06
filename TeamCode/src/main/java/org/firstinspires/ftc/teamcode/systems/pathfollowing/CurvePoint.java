package org.firstinspires.ftc.teamcode.systems.pathfollowing;

public class CurvePoint extends Point {

    public double speed = 1;

    public boolean passed = false;

    public CurvePoint speed(double speed) {
        this.speed = speed;
        return this;
    }

}
