package org.firstinspires.ftc.teamcode.robot.components.debug;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;
import org.firstinspires.ftc.teamcode.robot.components.Component;
import org.firstinspires.ftc.teamcode.robot.robots.Robot;

import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraRotation;
import org.openftc.easyopencv.OpenCvInternalCamera;
import org.openftc.easyopencv.OpenCvPipeline;

public class PhoneCamera extends Component {

    OpenCvCamera phone_camera;


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
        phone_camera.setPipeline(new SamplePipeline());
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
    }

    @Override
    public void shutdown() {
    }

    public void start_streaming() {
        phone_camera.startStreaming(320, 240, OpenCvCameraRotation.UPRIGHT);
    }

    public void stop_streaming() {
        phone_camera.stopStreaming();
    }

    class SamplePipeline extends OpenCvPipeline
    {
        @Override
        public Mat processFrame(Mat input)
        {
            Imgproc.rectangle(
                    input,
                    new Point(
                            input.cols()*(1f/4f),
                            input.rows()*(3f/4f)),

                    new Point(
                            input.cols()*(2f/4f),
                            input.rows()*(4f/4f)),
                    new Scalar(0, 255, 0), 4);

            Imgproc.rectangle(
                    input,
                    new Point(
                            input.cols()*(2f/4f),
                            input.rows()*(3f/4f)),

                    new Point(
                            input.cols()*(3f/4f),
                            input.rows()*(4f/4f)),
                    new Scalar(0, 0, 255), 4);

            return input;
        }
    }
}
