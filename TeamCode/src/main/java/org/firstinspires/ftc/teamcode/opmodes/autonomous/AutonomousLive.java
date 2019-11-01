package org.firstinspires.ftc.teamcode.opmodes.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.constants.AutonomousConst;
import org.firstinspires.ftc.teamcode.robot.robots.LiveRobot;

import static org.firstinspires.ftc.teamcode.constants.AutonomousConst.*;

@Autonomous(name="Live Auto", group="autonomous")
@Disabled
public class AutonomousLive extends LinearOpMode {

    LiveRobot robot;

    protected static int COLOR;
    protected static int SIDE;

    protected static boolean TAPE;
    protected static boolean FOUNDATION;
    protected static boolean SKYSTONE;

    int pattern;

    @Override
    public void runOpMode() throws InterruptedException {

        robot = new LiveRobot(this);
        robot.startup();

        if (SKYSTONE) {
            robot.phone_camera.start_streaming();
            while(!isStarted()) {

                pattern = robot.phone_camera.get_pattern();

                telemetry.addData("PATTERN", pattern);
                telemetry.update();
            }
        }

        waitForStart();

        if (SKYSTONE) {
            robot.phone_camera.stop_streaming();
            switch(pattern) {
                case(1): {
                    robot.drive_train.encoder_drive(-1, 0, 0, 4, 1);
                }
                case(2): {
                    robot.drive_train.encoder_drive(1,0,0,4,1);
                }
                case(3): {
                    robot.drive_train.encoder_drive(1, 0, 0, 12, 1);
                }
            }
            robot.drive_train.encoder_drive(0,1,0, 20, 1);
            sleep(300);
            robot.dragger.grab();
            sleep(300);
            robot.drive_train.encoder_drive(0,-1,0, 8, 1);
            sleep(300);
            robot.dragger.release();
        }


        robot.shutdown();
    }
}
