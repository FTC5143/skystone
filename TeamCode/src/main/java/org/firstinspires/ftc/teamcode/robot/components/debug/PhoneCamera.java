package org.firstinspires.ftc.teamcode.robot.components.debug;

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

public class PhoneCamera extends Component {

    OpenCvCamera phone_camera;

    SamplePipeline stone_pipeline;

    public PhoneCamera(Robot robot) {
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
        if(stone_pipeline.left_mean != null) {
            telemetry.addData("LEFT RECT",
                    (int) stone_pipeline.left_mean.val[0] + " " +
                            (int) stone_pipeline.left_mean.val[1] + " " +
                            (int) stone_pipeline.left_mean.val[2] + " " +
                            (int) stone_pipeline.left_mean.val[3]
            );
        }
        if(stone_pipeline.right_mean != null) {
            telemetry.addData("RIGHT RECT",
                    (int) stone_pipeline.right_mean.val[0] + " " +
                            (int) stone_pipeline.right_mean.val[1] + " " +
                            (int) stone_pipeline.right_mean.val[2] + " " +
                            (int) stone_pipeline.right_mean.val[3]
            );
        }
    }

    @Override
    public void update(OpMode opmode) {
        super.update(opmode);

    }

    @Override
    public void shutdown() {
    }

    public void start_streaming() {
        phone_camera.startStreaming(320, 240, OpenCvCameraRotation.UPSIDE_DOWN);
    }

    public void stop_streaming() {
        phone_camera.stopStreaming();
    }

    class SamplePipeline extends OpenCvPipeline
    {

        Scalar left_mean;
        Scalar right_mean;

        @Override
        public Mat processFrame(Mat input)
        {
            int[] left_rect = {
                    (int)(input.cols()*(1f/4f)),
                    (int)(input.cols()*(1f/8f)),
                    (int)(input.cols()*(2f/4f)),
                    (int)(input.cols()*(1f/4f))
            };

            int[] right_rect = {
                    (int)(input.cols()*(2f/4f)),
                    (int)(input.cols()*(1f/8f)),
                    (int)(input.cols()*(3f/4f)),
                    (int)(input.cols()*(1f/4f))
            };

            Imgproc.rectangle(
                    input,
                    new Point(
                            left_rect[0],
                            left_rect[1]),

                    new Point(
                            left_rect[2],
                            left_rect[3]),
                    new Scalar(0, 255, 0), 2);

            Imgproc.rectangle(
                    input,
                    new Point(
                            right_rect[0],
                            right_rect[1]),

                    new Point(
                            right_rect[2],
                            right_rect[3]),
                    new Scalar(0, 0, 255), 2);

            Mat left_block = input.submat(left_rect[0], left_rect[2], left_rect[1], left_rect[3]);
            Mat right_block = input.submat(right_rect[0], right_rect[2], right_rect[1], right_rect[3]);


            left_mean = Core.mean(left_block);


            right_mean = Core.mean(right_block);

            return input;
        }
    }
}
