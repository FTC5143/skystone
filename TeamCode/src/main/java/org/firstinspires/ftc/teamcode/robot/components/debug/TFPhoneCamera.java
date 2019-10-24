package org.firstinspires.ftc.teamcode.robot.components.debug;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;
import org.firstinspires.ftc.teamcode.robot.components.Component;
import org.firstinspires.ftc.teamcode.robot.robots.Robot;

import java.util.List;

public class TFPhoneCamera extends Component {

    private static final String TFOD_MODEL_ASSET = "Skystone.tflite";
    private static final String LABEL_FIRST_ELEMENT = "Stone";
    private static final String LABEL_SECOND_ELEMENT = "Skystone";


    private static final String VUFORIA_KEY =
            "AckXrr//////AAAAmaDBkb1xNkx0ru8YM4IE6J5E9LuPxKCWHq/SbgTltCRV7gAqSa7Hx3QK/klvy8AHSBTRdKjgzLOXcKrLmD2up9yd7j0thOSW6zeLw901mUdpqCJHP4uIFT57Nu94ermDVSe0a/l2PtOBSw2C2rSJevczyf4QuD/al9wATQ63yICvPKFhAduN3rZrFsyn4TJQYV97JqWAaURJ3FJTt7+xo43/A0zZxDwedBpQ8y3IxuX6STUClcMSlDWiwfjaKPlZA4TYq7e1GN02gNilVN0CYkPvJixPs+eKQW5ziQz8gujuIw0x6dfsaKMHZnU+jOdlpHt8v9hQX8CdCzui9mEB35VYOCl8LMUp3aE0c8pPOEep";

    private VuforiaLocalizer vuforia;
    private TFObjectDetector tfod;

    public TFPhoneCamera(Robot robot) {
        super(robot);
    }

    @Override
    public void startup() {
        super.startup();
        if (tfod != null) {
            tfod.activate();
        }
    }

    @Override
    public void shutdown() {
        super.shutdown();
        if (tfod != null) {
            tfod.shutdown();
        }
    }

    @Override
    public void update(OpMode opmode) {
        super.update(opmode);
        if (tfod != null) {
            List<Recognition> updatedRecognitions = tfod.getUpdatedRecognitions();
            if (updatedRecognitions != null) {
                robot.opmode.telemetry.addData("# Object Detected", updatedRecognitions.size());

                int i = 0;
                for (Recognition recognition : updatedRecognitions) {
                    robot.opmode.telemetry.addData(String.format("label (%d)", i), recognition.getLabel());
                    robot.opmode.telemetry.addData(String.format("  left,top (%d)", i), "%.03f , %.03f",
                            recognition.getLeft(), recognition.getTop());
                    robot.opmode.telemetry.addData(String.format("  right,bottom (%d)", i), "%.03f , %.03f",
                            recognition.getRight(), recognition.getBottom());
                }
                robot.opmode.telemetry.update();
            }
        }
    }

    @Override
    public void registerHardware(HardwareMap hwmap) {
        super.registerHardware(hwmap);

        initVuforia();

        if (ClassFactory.getInstance().canCreateTFObjectDetector()) {
            initTfod(hwmap);
        }
    }

    private void initVuforia() {
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters();

        parameters.vuforiaLicenseKey = VUFORIA_KEY;
        parameters.cameraDirection = VuforiaLocalizer.CameraDirection.BACK;

        vuforia = ClassFactory.getInstance().createVuforia(parameters);
    }

    private void initTfod(HardwareMap hwmap) {
        int tfodMonitorViewId = hwmap.appContext.getResources().getIdentifier(
                "tfodMonitorViewId", "id", hwmap.appContext.getPackageName());
        TFObjectDetector.Parameters tfodParameters = new TFObjectDetector.Parameters(tfodMonitorViewId);
        tfodParameters.minimumConfidence = 0.6;
        tfod = ClassFactory.getInstance().createTFObjectDetector(tfodParameters, vuforia);
        tfod.loadModelFromAsset(TFOD_MODEL_ASSET, LABEL_FIRST_ELEMENT, LABEL_SECOND_ELEMENT);
    }
}
