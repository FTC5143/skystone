package org.firstinspires.ftc.teamcode.robots;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.canvas.Canvas;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.components.live.Dragger;
import org.firstinspires.ftc.teamcode.components.live.DriveTrain;
import org.firstinspires.ftc.teamcode.components.live.Feeder;
import org.firstinspires.ftc.teamcode.components.live.Lift;
import org.firstinspires.ftc.teamcode.components.live.OCVPhoneCamera;
import org.firstinspires.ftc.teamcode.systems.pathfollowing.Point;
import org.firstinspires.ftc.teamcode.util.DashboardUtil;

import java.util.ArrayList;

public class LiveRobot extends Robot {

    public DriveTrain       drive_train;
    public Lift             lift;
    public OCVPhoneCamera   phone_camera;
    public Dragger          dragger;
    public Feeder           feeder;

    FtcDashboard            dashboard;

    ArrayList<Point> robot_movement = new ArrayList<Point>();

    {
        name = "Boris";
    }


    public LiveRobot(OpMode opmode) {
        super(opmode);

        drive_train     = new DriveTrain(this);
        lift            = new Lift(this);
        phone_camera    = new OCVPhoneCamera(this);
        dragger         = new Dragger(this);
        feeder          = new Feeder(this);


        dashboard = FtcDashboard.getInstance();
    }

    @Override
    public void update() {
        super.update();
    }

    @Override
    public void updateTelemetry() {
        super.updateTelemetry();

        telemetry.addData("DIGITAL",
                "D10:"+(bulk_data_1.getDigitalInputState(0) ? 1 : 0)+
                        " D11:"+(bulk_data_1.getDigitalInputState(1) ? 1 : 0)+
                        " D12:"+(bulk_data_1.getDigitalInputState(2) ? 1 : 0)+
                        " D13:"+(bulk_data_1.getDigitalInputState(3) ? 1 : 0)+
                        " D14:"+(bulk_data_1.getDigitalInputState(4) ? 1 : 0)+
                        " D15:"+(bulk_data_1.getDigitalInputState(5) ? 1 : 0)+
                        " D16:"+(bulk_data_1.getDigitalInputState(6) ? 1 : 0)+
                        " D17:"+(bulk_data_1.getDigitalInputState(7) ? 1 : 0)+
                        " D20:"+(bulk_data_2.getDigitalInputState(0) ? 1 : 0)+
                        " D21:"+(bulk_data_2.getDigitalInputState(1) ? 1 : 0)+
                        " D22:"+(bulk_data_2.getDigitalInputState(2) ? 1 : 0)+
                        " D23:"+(bulk_data_2.getDigitalInputState(3) ? 1 : 0)+
                        " D24:"+(bulk_data_2.getDigitalInputState(4) ? 1 : 0)+
                        " D25:"+(bulk_data_2.getDigitalInputState(5) ? 1 : 0)+
                        " D26:"+(bulk_data_2.getDigitalInputState(6) ? 1 : 0)+
                        " D27:"+(bulk_data_2.getDigitalInputState(7) ? 1 : 0)
        );

        TelemetryPacket packet = new TelemetryPacket();

        Canvas canvas = packet.fieldOverlay();



        int offset_x = -33;
        int offset_y = -63;
        double offset_a = Math.PI/2;

        DashboardUtil.drawRobot(canvas, new Pose2d(drive_train.lcs.x+offset_x, drive_train.lcs.y+offset_y, drive_train.lcs.a+offset_a));


        /*
        robot_movement.add(new Point(drive_train.lcs.x+offset_x, drive_train.lcs.y+offset_y));
        DashboardUtil.drawPointList(canvas, robot_movement);
        */

        canvas.setStrokeWidth(1);
        canvas.setStroke("#0000ff");

        packet.put("x", drive_train.lcs.x);
        packet.put("y", drive_train.lcs.y);
        packet.put("a", drive_train.lcs.a);
        packet.put("le", drive_train.lcs.prev_le);
        packet.put("re", drive_train.lcs.prev_re);
        packet.put("ce", drive_train.lcs.prev_ce);
        packet.put("freq", update_freq);


        if (drive_train.current_path != null) {
            drive_train.current_path.dashboard_draw(canvas, drive_train.lcs.x, drive_train.lcs.y);
        }

        dashboard.sendTelemetryPacket(packet);
    }
}