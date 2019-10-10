package org.firstinspires.ftc.teamcode.robot.components.debug;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;
import org.firstinspires.ftc.teamcode.robot.components.Component;
import org.firstinspires.ftc.teamcode.robot.robots.Robot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class PhoneCamera extends Component {

    private static final String TFOD_MODEL_ASSET = "Skystone.tflite";
    private static final String LABEL_FIRST_ELEMENT = "Stone";
    private static final String LABEL_SECOND_ELEMENT = "Skystone";


    private static final String VUFORIA_KEY = "AckXrr//////AAAAmaDBkb1xNkx0ru8YM4IE6J5E9LuPxKCWHq/SbgTltCRV7gAqSa7Hx3QK/klvy8AHSBTRdKjgzLOXcKrLmD2up9yd7j0thOSW6zeLw901mUdpqCJHP4uIFT57Nu94ermDVSe0a/l2PtOBSw2C2rSJevczyf4QuD/al9wATQ63yICvPKFhAduN3rZrFsyn4TJQYV97JqWAaURJ3FJTt7+xo43/A0zZxDwedBpQ8y3IxuX6STUClcMSlDWiwfjaKPlZA4TYq7e1GN02gNilVN0CYkPvJixPs+eKQW5ziQz8gujuIw0x6dfsaKMHZnU+jOdlpHt8v9hQX8CdCzui9mEB35VYOCl8LMUp3aE0c8pPOEep";
    //why all the gibberish? stupid useless code
    private VuforiaLocalizer vuforia;
    //what is a vuforia?
    private TFObjectDetector tfod;


    public PhoneCamera(Robot robot) {
        super(robot);
    }

    @Override
    public void registerHardware(HardwareMap hwmap) {
        super.registerHardware(hwmap);
        initVuforia();
        initTfod(hwmap);

        tfod.activate();
    }


    public class RecognitionPositionComparator implements Comparator<Recognition> {
        @Override
        public int compare(Recognition o1, Recognition o2) {
            return ((Float)o1.getLeft()).compareTo(o2.getLeft());
        }
    }

    public ArrayList<Boolean> findBlocks() {
        ArrayList<Boolean> recognized_blocks = new ArrayList<>();

        try {
            if (tfod != null) {
                List<Recognition> updated_recognitions = tfod.getUpdatedRecognitions();
                Collections.sort(updated_recognitions, new RecognitionPositionComparator());

                if (updated_recognitions != null) {
                    for (Recognition recognition : updated_recognitions) {
                        if (recognition != null) {
                            recognized_blocks.add(recognition.getLabel() == "Skystone");
                        }
                    }
                }
            }
        } catch (Exception e) {}
        return recognized_blocks;
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
        tfodParameters.minimumConfidence = 0.4;
        tfod = ClassFactory.getInstance().createTFObjectDetector(tfodParameters, vuforia);
        tfod.loadModelFromAsset(TFOD_MODEL_ASSET, LABEL_FIRST_ELEMENT, LABEL_SECOND_ELEMENT);
    }

    @Override
    public void shutdown() {
        super.shutdown();
        tfod.shutdown();
    }
}
