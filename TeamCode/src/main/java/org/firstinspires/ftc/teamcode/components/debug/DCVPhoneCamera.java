package org.firstinspires.ftc.teamcode.components.debug;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.rewriting.LessBadSkystoneDetector;
import org.firstinspires.ftc.teamcode.components.Component;
import org.firstinspires.ftc.teamcode.robots.Robot;

import java.util.Locale;

import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraRotation;
import org.openftc.easyopencv.OpenCvInternalCamera;


public class DCVPhoneCamera extends Component {

    private OpenCvCamera phone_camera;
    private LessBadSkystoneDetector skystone_detector;

    public DCVPhoneCamera(Robot robot) {
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

        skystone_detector = new LessBadSkystoneDetector();
        phone_camera.setPipeline(skystone_detector);
    }

    @Override
    public void updateTelemetry(Telemetry telemetry) {
        super.updateTelemetry(telemetry);

        telemetry.addData("SP X", skystone_detector.getScreenPosition().x);
        telemetry.addData("SP Y", skystone_detector.getScreenPosition().y);
        telemetry.addData("FRAME", phone_camera.getFrameCount());
        telemetry.addData("FPS", String.format(Locale.US, "%.2f", phone_camera.getFps()));
        telemetry.addData("TF MS", phone_camera.getTotalFrameTimeMs());
        telemetry.addData("PT MS", phone_camera.getPipelineTimeMs());
        telemetry.addData("OT MS", phone_camera.getOverheadTimeMs());
        telemetry.addData("MAX FPS", phone_camera.getCurrentPipelineMaxFps());
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
}
