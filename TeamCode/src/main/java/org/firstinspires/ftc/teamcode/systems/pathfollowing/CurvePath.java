package org.firstinspires.ftc.teamcode.systems.pathfollowing;

import java.util.ArrayList;

import static java.lang.Math.*;
import static org.firstinspires.ftc.teamcode.util.MathUtil.signum;

public class CurvePath {

    ArrayList<CurvePoint> points;

    public boolean reverse = true;

    public Point get_lookahead_point(double robot_x, double robot_y) {

        // Default to going to the last point in the path if we aren't intersecting with any line
        Point lookahead_point = last_point().pos;

        // Iterate through all the points in the path, and find our last intersection
        for(int i = 0; i < points.size() - 1; i++) {
            CurvePoint seg_start    = points.get(i);
            CurvePoint seg_end      = points.get(i + 1);

            Point p1 = new Point(seg_start.pos.x - robot_x, seg_start.pos.y - robot_y);
            Point p2 = new Point(seg_end.pos.x - robot_x, seg_end.pos.y - robot_y);

            double dx = p2.x - p1.x;
            double dy = p2.y - p1.y;

            double d = sqrt(dx * dx + dy * dy);
            double D = p1.x * p2.y - p2.x * p1.y;

            // If the discriminant is zero or the points are equal, there is no intersection
            double discriminant = seg_start.follow_radius * seg_start.follow_radius * d * d - D * D;
            if (discriminant < 0 || p1.equals(p2)) continue;

            double x1 = (D * dy + signum(dy) * dx * sqrt(discriminant)) / (d * d);
            double x2 = (D * dy - signum(dy) * dx * sqrt(discriminant)) / (d * d);

            double y1 = (-D * dx + abs(dy) * sqrt(discriminant)) / (d * d);
            double y2 = (-D * dx - abs(dy) * sqrt(discriminant)) / (d * d);

            boolean valid_intersection_1 = min(p1.x, p2.x) < x1 && x1 < max(p1.x, p2.x) || min(p1.y, p2.y) < y1 && y1 < max(p1.y, p2.y);
            boolean valid_intersection_2 = min(p1.x, p2.x) < x2 && x2 < max(p1.x, p2.x) || min(p1.y, p2.y) < y2 && y2 < max(p1.y, p2.y);

            if(valid_intersection_1 || valid_intersection_2) lookahead_point = null;

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

            double end_x = last_point.pos.x;
            double end_y = last_point.pos.y;

            // If we're close enough to the end, use that as our follow point
            if (sqrt((end_x - robot_x) * (end_x - robot_x) + (end_y - robot_y) * (end_y - robot_y)) <= last_point.follow_radius) {
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

}
