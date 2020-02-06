package org.firstinspires.ftc.teamcode.systems.pathfollowing;

import com.acmerobotics.dashboard.canvas.Canvas;

import java.util.ArrayList;

import static java.lang.Math.*;
import static org.firstinspires.ftc.teamcode.util.MathUtil.signum;

public class CurvePath {

    ArrayList<CurvePoint> points = new ArrayList<>();

    public boolean reverse = true;

    public double radius = 5;

    public CurvePoint get_first_unpassed_point() {

        for(CurvePoint point : points) {
            if (!point.passed) {
                return point;
            }
        }

        // If for some ungodly reason there are no points in our path, return the last point which also doesn't exist
        return points.get(points.size() - 1);

    }

    public Point get_lookahead_point(double robot_x, double robot_y) {

        // Default to going to the last unpassed point if we aren't intersecting any line
        Point lookahead_point = get_first_unpassed_point();

        // Iterate through all the points in the path, and find our last intersection
        for(int i = 0; i < points.size() - 1; i++) {
            CurvePoint seg_start    = points.get(i);
            CurvePoint seg_end      = points.get(i + 1);

            Point p1 = new Point(seg_start.x - robot_x, seg_start.y - robot_y);
            Point p2 = new Point(seg_end.x - robot_x, seg_end.y - robot_y);

            double dx = p2.x - p1.x;
            double dy = p2.y - p1.y;

            double d = sqrt(dx * dx + dy * dy);
            double D = p1.x * p2.y - p2.x * p1.y;

            // If the discriminant is zero or the points are equal, there is no intersection
            double discriminant = radius * radius * d * d - D * D;
            if (discriminant < 0 || p1.equals(p2)) continue;

            double x1 = (D * dy + signum(dy) * dx * sqrt(discriminant)) / (d * d);
            double x2 = (D * dy - signum(dy) * dx * sqrt(discriminant)) / (d * d);

            double y1 = (-D * dx + abs(dy) * sqrt(discriminant)) / (d * d);
            double y2 = (-D * dx - abs(dy) * sqrt(discriminant)) / (d * d);

            boolean valid_intersection_1 = min(p1.x, p2.x) < x1 && x1 < max(p1.x, p2.x) || min(p1.y, p2.y) < y1 && y1 < max(p1.y, p2.y);
            boolean valid_intersection_2 = min(p1.x, p2.x) < x2 && x2 < max(p1.x, p2.x) || min(p1.y, p2.y) < y2 && y2 < max(p1.y, p2.y);

            if(valid_intersection_1 || valid_intersection_2) {
                lookahead_point = null;

                // If we are intersecting with a line, mark the start point of that line as passed
                if (!seg_start.passed) {
                    seg_start.passed = true;
                }

            }

            if (valid_intersection_1) {
                lookahead_point = new Point(x1 + robot_x, y1 + robot_y);
            }

            if(valid_intersection_2) {
                // Only choose the second point if it's closer to the end of the line segment than the first one
                if (lookahead_point == null || Math.abs(x1 - p2.x) > Math.abs(x2 - p2.x) || Math.abs(y1 - p2.y) > Math.abs(y2 - p2.y)) {
                    lookahead_point = new Point(x2 + robot_x, y2 + robot_y);
                }
            }
        }

        if (points.size() > 0) {

            CurvePoint last_point = points.get(points.size() - 1);

            double end_x = last_point.x;
            double end_y = last_point.y;

            // If we're close enough to the end, use that as our follow point
            if (sqrt((end_x - robot_x) * (end_x - robot_x) + (end_y - robot_y) * (end_y - robot_y)) <= radius) {
                return new Point(end_x, end_y);
            }

        }

        return lookahead_point;
    }

    public CurvePoint last_point() {
        return points.get(points.size()-1);
    }

    public CurvePath add_point(CurvePoint point) {
        points.add(point);
        return this;
    }

    public CurvePath reverse() {
        this.reverse = true;
        return this;
    }

    public CurvePath radius(double radius) {
        this.radius = radius;
        return this;
    }

    public void dashboard_draw(Canvas canvas, double robot_x, double robot_y) {

        canvas.setStrokeWidth(1);

        canvas.setStroke("#0000ff");

        for(int i = 0; i < points.size() - 1; i++) {

            CurvePoint seg_start = points.get(i);
            CurvePoint seg_end = points.get(i + 1);

            canvas.strokeLine(seg_start.x, seg_start.y, seg_end.x, seg_end.y);

        }

        for (CurvePoint point : points) {
            if (point.passed) {
                canvas.fillCircle(point.x, point.y, 2);
            } else {
                canvas.strokeCircle(point.x, point.y, 2);
            }
        }

        Point lookahead_point = get_lookahead_point(robot_x, robot_y);


        canvas.setStroke("#00ff00");
        canvas.strokeCircle(robot_x, robot_y, radius);

        canvas.setStroke("#ff0000");
        canvas.strokeLine(robot_x, robot_y, lookahead_point.x, lookahead_point.y);
        canvas.strokeCircle(lookahead_point.x, lookahead_point.y, 1);

    }
}
