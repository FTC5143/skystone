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
import org.firstinspires.ftc.teamcode.util.DashboardUtil;

public class LiveRobot extends Robot {

    public DriveTrain       drive_train;
    public Lift             lift;
    public OCVPhoneCamera   phone_camera;
    public Dragger          dragger;
    public Feeder           feeder;

    FtcDashboard            dashboard;

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

        TelemetryPacket packet = new TelemetryPacket();

        Canvas canvas = packet.fieldOverlay();

        DashboardUtil.drawRobot(canvas, new Pose2d(drive_train.lcs.y, -drive_train.lcs.x, drive_train.lcs.a));

        canvas.setStrokeWidth(1);
        canvas.setStroke("#0000ff");

        canvas.strokeLine(-72, -drive_train.lcs.x, 72, -drive_train.lcs.x);
        canvas.strokeLine(drive_train.lcs.y, -72, drive_train.lcs.y, 72);

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