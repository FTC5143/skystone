package org.firstinspires.ftc.teamcode.systems.pathfollowing;

import com.acmerobotics.dashboard.canvas.Canvas;

import java.util.ArrayList;

import static java.lang.Math.*;
import static org.firstinspires.ftc.teamcode.util.MathUtil.signum;

public class CurvePath {

    ArrayList<CurvePoint> points = new ArrayList<>();

    public boolean reverse = true;

    public double radius = 8;

    public CurvePoint get_first_unpassed_point() {

        for(CurvePoint point : points) {
            if (!point.passed) {
                return point;
            }
        }

        // If for some ungodly reason there are no points in our path, return the last point which also doesn't exist
        return points.get(points.size() - 1);

    }

    public Pose get_lookahead_pose(double robot_x, double robot_y) {

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

                // If we are intersecting with a line, mark the start point of that line as passed
                if (!seg_start.passed) {

                    CurvePoint prev_seg = (i - 1) > 0 ? points.get(i - 1) : null;

                    // Only pass this segment if we are the first segment, or the previous segment is passed
                    if(prev_seg == null || prev_seg.passed) {
                        seg_start.passed = true;
                    }
                }

                // If we have determined we can begin traveling towards this segment, clear the lookahead point to be set to the intersection
                if(seg_start.passed) {
                    lookahead_point = null;
                } else {
                    // If this intersection is on a line we haven't made it to yet, do nothing
                    continue;
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

        boolean approaching_end = false;

        if (points.size() > 0) {

            CurvePoint last_point = points.get(points.size() - 1);

            double end_x = last_point.x;
            double end_y = last_point.y;

            // If we're close enough to the end, use that as our follow point
            if (sqrt((end_x - robot_x) * (end_x - robot_x) + (end_y - robot_y) * (end_y - robot_y)) <= radius) {
                lookahead_point = new Point(end_x, end_y);
                approaching_end = true;
            }

        }


        Pose lookahead_pose;

        // If we are close enough to the end point, make our angle just be the angle of the last
        // Segment so we don't overshoot and then try to turn around to fix it

        // Otherwise just do the angle between the robot and the next point
        if (approaching_end) {

            CurvePoint last_seg_end = points.get(points.size()-1);
            CurvePoint last_seg_start = points.get(points.size()-2);

            double last_segment_angle = Math.atan2(last_seg_end.y-last_seg_start.y, last_seg_end.x-last_seg_start.x);
            lookahead_pose = lookahead_point.to_pose(last_segment_angle);

        } else {
            double angle_to_point = Math.atan2(lookahead_point.y-robot_y, lookahead_point.x-robot_x);
            lookahead_pose = lookahead_point.to_pose(angle_to_point);

        }

        if(lookahead_pose == null) {
            throw new RuntimeException("For some ungodly reason, we were unable to locate any plausible lookahead pose");
        }

        return lookahead_pose;

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

    public CurvePath verify() {

        if(points.size() < 2) {
            throw new RuntimeException("You idiot, you're trying to run a path that has less than 2 points. How the hell is that supposed to work?");
        }

        if(radius <= 0) {
            throw new RuntimeException("You absolute baffoon. You're trying to run a path with a radius of 0 or less. How the hell can a point fall inside a circle zero size???");
        }

        return this;
    }

    public void dashboard_draw(Canvas canvas, double robot_x, double robot_y) {

        canvas.setStrokeWidth(1);

        canvas.setStroke("#0000ff");

        for(int i = 0; i < points.size() - 1; i++) {

            CurvePoint seg_start = points.get(i);
            CurvePoint seg_end = points.get(i + 1);

            canvas.strokeLine(seg_start.y, -seg_start.x, seg_end.y, -seg_end.x);

        }

        for (CurvePoint point : points) {
            if (point.passed) {
                canvas.fillCircle(point.y, -point.x, 2);
            } else {
                canvas.strokeCircle(point.y, -point.x, 2);
            }
        }

        Pose lookahead_pose = get_lookahead_pose(robot_x, robot_y);


        canvas.setStroke("#00ff00");
        canvas.strokeCircle(robot_y, -robot_x, radius);

        canvas.setStroke("#ff0000");
        canvas.strokeLine(robot_y, -robot_x, lookahead_pose.y, -lookahead_pose.x);
        canvas.strokeCircle(lookahead_pose.y, -lookahead_pose.x, 1);

    }
}
