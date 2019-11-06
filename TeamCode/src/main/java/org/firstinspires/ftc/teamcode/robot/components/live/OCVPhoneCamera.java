package org.firstinspires.ftc.teamcode.robot.components.live;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;
import org.firstinspires.ftc.teamcode.robot.components.Component;
import org.firstinspires.ftc.teamcode.robot.robots.Robot;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraRotation;
import org.openftc.easyopencv.OpenCvInternalCamera;
import org.openftc.easyopencv.OpenCvPipeline;

import java.util.Arrays;

import static org.opencv.core.CvType.CV_8UC1;

public class OCVPhoneCamera extends Component {

    OpenCvCamera phone_camera;

    SamplePipeline stone_pipeline;

    {
        name = "Phone Camera (OCV)";
    }

    public OCVPhoneCamera(Robot robot) {
        super(robot);
    }

    @Override
    public void registerHardware(HardwareMap hwmap) {
        int cameraMonitorViewId = hwmap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hwmap.appContext.getPackageName());
        phone_camera = new OpenCvInternalCamera(OpenCvInternalCamera.CameraDirection.BACK, cameraMonitorViewId);
    }

    @Override
    public void startup() {
        super.startup();

        phone_camera.openCameraDevice();

        stone_pipeline = new SamplePipeline();
        phone_camera.setPipeline(stone_pipeline);
    }

    @Override
    protected void updateTelemetry(Telemetry telemetry) {
        super.updateTelemetry(telemetry);
        telemetry.addData("FRAME", phone_camera.getFrameCount());
        telemetry.addData("FPS", String.format("%.2f", phone_camera.getFps()));
        telemetry.addData("TFT MS", phone_camera.getTotalFrameTimeMs());
        telemetry.addData("PT MS", phone_camera.getPipelineTimeMs());
        telemetry.addData("OT MS", phone_camera.getOverheadTimeMs());
        telemetry.addData("MAX FPS", phone_camera.getCurrentPipelineMaxFps());
        telemetry.addData("LEFT RECT", stone_pipeline.left_hue + " " + stone_pipeline.left_br);
        telemetry.addData("RIGHT RECT", stone_pipeline.right_hue + " " + stone_pipeline.right_br);
        telemetry.addData("PATTERN", stone_pipeline.pattern);

    }

    @Override
    public void update(OpMode opmode) {
        super.update(opmode);
    }

    @Override
    public void shutdown() {
    }

    public void start_streaming() {
        phone_camera.startStreaming(320, 240, OpenCvCameraRotation.UPRIGHT );
    }

    public int get_pattern() {
        return stone_pipeline.pattern;
    }

    public void stop_streaming() {
        phone_camera.stopStreaming();
    }

    class SamplePipeline extends OpenCvPipeline {
        int left_hue;
        int right_hue;

        int left_br;
        int right_br;

        int pattern;

        @Override
        public Mat processFrame(Mat input) {

            input.convertTo(input, CV_8UC1, 1, 10);

            int[] left_rect = {
                    (int) (input.cols() * (13f / 32f)),
                    (int) (input.rows() * (11f / 32f)),
                    (int) (input.cols() * (18f / 32f)),
                    (int) (input.rows() * (17f / 32f))
            };

            int[] right_rect = {
                    (int) (input.cols() * (13f / 32f)),
                    (int) (input.rows() * (17f / 32f)),
                    (int) (input.cols() * (18f / 32f)),
                    (int) (input.rows() * (24f / 32f))
            };

            Imgproc.rectangle(
                    input,
                    new Point(
                            left_rect[0],
                            left_rect[1]),

                    new Point(
                            left_rect[2],
                            left_rect[3]),
                    new Scalar(0, 255, 0), 1);

            Imgproc.rectangle(
                    input,
                    new Point(
                            right_rect[0],
                            right_rect[1]),

                    new Point(
                            right_rect[2],
                            right_rect[3]),
                    new Scalar(0, 0, 255), 1);

            Mat left_block = input.submat(left_rect[1], left_rect[3], left_rect[0], left_rect[2]);
            Mat right_block = input.submat(right_rect[1], right_rect[3], right_rect[0], right_rect[2]);


            Scalar left_mean = Core.mean(left_block);


            Scalar right_mean = Core.mean(right_block);

            left_hue = get_hue((int) left_mean.val[0], (int) left_mean.val[1], (int) left_mean.val[2]);
            right_hue = get_hue((int) right_mean.val[0], (int) right_mean.val[1], (int) right_mean.val[2]);
            left_br = get_brightness((int) left_mean.val[0], (int) left_mean.val[1], (int) left_mean.val[2]);
            right_br = get_brightness((int) right_mean.val[0], (int) right_mean.val[1], (int) right_mean.val[2]);

            if (left_br > 100 && right_br > 100) pattern = 1;
            else if (left_br > 100 && right_br < 100) pattern = 2;
            else if (left_br < 100 && right_br > 100) pattern = 3;
            else if (left_br < 100 && right_br < 100) {
                if (left_br > right_br) {
                    pattern = 1;
                } else if (left_br < right_br) {
                    pattern = 2;
                } else {
                    pattern = 3;
                }
            }

            return input;
        }
    }


    private int get_hue(int red, int green, int blue) {

        float min = Math.min(Math.min(red, green), blue);
        float max = Math.max(Math.max(red, green), blue);

        if (min == max) {
            return 0;
        }

        float hue = 0f;
        if (max == red) {
            hue = (green - blue) / (max - min);

        } else if (max == green) {
            hue = 2f + (blue - red) / (max - min);

        } else {
            hue = 4f + (red - green) / (max - min);
        }

        hue = hue * 60;
        if (hue < 0) hue = hue + 360;

        return Math.round(hue);
    }

    private int get_brightness(int red, int green, int blue) {
        return (int) (((double) (red + green + blue)) / 3);
    }
}