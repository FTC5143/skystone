package org.firstinspires.ftc.teamcode.opmodes.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

import org.firstinspires.ftc.teamcode.opmodes.LiveAutoBase;

import static org.firstinspires.ftc.teamcode.constants.AutonomousConst.BLUE;
import static org.firstinspires.ftc.teamcode.constants.AutonomousConst.FAR;

@Autonomous(name="Two Stone Auto", group="autonomous")
@Disabled
public class TwoStoneAuto extends LiveAutoBase {

    int pattern;

    protected static int COLOR = BLUE;
    protected static int PARK = FAR;
    protected static boolean FOUNDATION = false;

    @Override
    public void on_init() {

        // All of this stuff is computer vision, remember our pattern for later
        robot.phone_camera.start_streaming(COLOR);

        robot.drive_train.color = COLOR;

        while(!isStarted()) {
            pattern = robot.phone_camera.get_pattern(COLOR);

            // Displaying the pattern on the phone for debug purposes
            telemetry.addData("PATTERN", pattern);
            telemetry.update();
        }

    }

    @Override
    public void on_start() {

        if (pattern == 1) {
            
            
            robot.drive_train.odo_move(5, 30, -Math.PI / 2, 1);

            robot.drive_train.odo_move(5, 40, -Math.PI / 2, 0.75, 0.5, 0.02);

            robot.feeder.spin(1);

            robot.drive_train.odo_move(-4, 40, -Math.PI / 2, 0.5);

        }
        else if (pattern == 2) {

            robot.drive_train.odo_move(13, 30, -Math.PI / 2, 1);

            robot.drive_train.odo_move(13, 40, -Math.PI / 2, 0.75, 0.5, 0.02);

            robot.feeder.spin(1);

            robot.drive_train.odo_move(4, 40, -Math.PI / 2, 0.5);

        } else if (pattern == 3) {
            robot.drive_train.odo_move(2, 30, -Math.PI / 2, 1);

            robot.drive_train.odo_move(4, 41, -Math.PI / 2, 1);

            robot.drive_train.odo_move(4, 27, -Math.PI / 2, 1);

            robot.drive_train.odo_move(-4, 27, -Math.PI / 2, 1);

            robot.drive_train.odo_move(-4, 40, -Math.PI / 2, 0.75, 0.5, 0.02);

            robot.feeder.spin(1);

            robot.drive_train.odo_move(-12, 40, -Math.PI / 2, 0.5);
        }

        // Wait for block to be intook, or 1 second
        resetStartTime(); while (!robot.lift.block_detector.isPressed() && getRuntime() < 1) {} 
        
        // Grab the stone after we intake it so it doesn't fall out
        robot.lift.grab();

        // Stop spinning the intake since we have the stone
        robot.feeder.spin(0);

        
        // Only do this if we are dragging the foundation
        if (FOUNDATION == true) {

            // Start driving under the bridge
            robot.drive_train.odo_move(6, 25, -Math.PI/2, 1);

            // Drive past under the bridge
            robot.drive_train.odo_move(52, 25, -Math.PI/2, 1);

            // Start bringing up the lift
            robot.lift.elevate(4);

            // While the lift goes up, drive up to the foundation
            robot.drive_train.odo_move(78, 28, -Math.PI, 1);

            // When we reach the foundation, extend the extension
            robot.lift.extend();

            // Drive a little bit farther, so we make sure we're up against the foundation
            robot.drive_train.odo_move(78, 34, -Math.PI, 0.6);

            // Turn the stone in the grabber 180 degrees
            robot.lift.turn(2);

            // Grab on to the foundation to begin dragging it
            robot.dragger.grab();

            // Wait a little bit to let the draggers go down
            sleep(500);

            // Bring the lift down a little bit so the stone doesn't bounce
            robot.lift.elevate(-1);

            // As we are bringing the lift down, turn and drag the foundation into the corner
            robot.drive_train.odo_move(76, 12, -Math.PI/2, 1, 1, 0.03, 2.5);

            // When we get there, we can drop the stone on the foundation
            robot.lift.release();

            // Give it some time to fall
            sleep(500);

            // Turn the grabber, retract the extension, and release the foundation grabbers all at the same time
            robot.lift.turn(-2);

            robot.lift.retract();

            robot.dragger.release();

            // Move left a bit
            robot.drive_train.odo_move(76, 25, -Math.PI/2, 1, 1, 0.03, 1.5);

            // As we move left bring the lift down
            robot.lift.min_lift();

        } else {

            robot.drive_train.odo_move(6, 25, Math.PI/2, 1);

            robot.drive_train.odo_move(52, 25, Math.PI / 2, 0.5);

            robot.feeder.spin(-1);


            robot.drive_train.odo_move(48, 25, Math.PI/2, 0.5);

            robot.feeder.spin(0);

        }


        if (pattern == 1) {

            // If we are doing the foundation we're already turned the right way so don't turn around just to turn around again
            if (FOUNDATION != true) {
                robot.drive_train.odo_move(6, 25, Math.PI/2, 1);
            }

            robot.drive_train.odo_move(-16, 25, -Math.PI/2, 1);

            robot.feeder.spin(1);

            robot.drive_train.odo_move(-18, 40, -Math.PI / 2, 0.5);

            robot.drive_train.odo_move(-26, 40, -Math.PI / 2, 1);

        }

        else if (pattern == 2) {

            // If we are doing the foundation we're already turned the right way so don't turn around just to turn around again
            if (FOUNDATION != true) {
                robot.drive_train.odo_move(6, 25, Math.PI/2, 1);
            }

            robot.drive_train.odo_move(-12, 25, -Math.PI/2, 1);

            robot.feeder.spin(1);

            robot.drive_train.odo_move(-12, 40, -Math.PI / 2, 0.5);

            robot.drive_train.odo_move(-20, 40, -Math.PI / 2, 1);

        }

        else if (pattern == 3) {

            // If we are doing the foundation we're already turned the right way so don't turn around just to turn around again
            if (FOUNDATION == true) {
                robot.drive_train.odo_move(8, 25, -Math.PI/2, 1);
            } else {
                robot.drive_train.odo_move(8, 25, Math.PI/2, 1);
            }

            robot.drive_train.odo_move(-6, 25, Math.PI/2, 1);

            robot.feeder.spin(1);

            robot.drive_train.odo_move(-6, 50, Math.PI / 2, 0.5);

            robot.drive_train.odo_move(6, 50, Math.PI / 2, 1);

        }

        // Wait for the stone to be intook, or 1 second
        resetStartTime(); while (!robot.lift.block_detector.isPressed() && getRuntime() < 1) {} 
        
        // Once we have the stone stop spinning the intake
        robot.feeder.spin(0);

        // Grab the stone so it doesn't come out of the robot while we drive
        robot.lift.grab();

        // Only do the following if we have previously moved the foundation
        if (FOUNDATION == true) {

            // Move under bridge
            robot.drive_train.odo_move(10, 25, -Math.PI/2, 1);

            // Begin driving to the foundation
            robot.drive_train.odo_move(52, 25, -Math.PI/2, 1);

            // Start raising the lift
            robot.lift.elevate(4);

            // As we raise finish driving to the foundation
            robot.drive_train.odo_move(76, 20, -Math.PI/2, 1, 1, 0.03, 3);

            // Once we reach the foundation, extend the extension
            robot.lift.extend();

            // Wait a little bit to let the extension be all the way out
            sleep(500);

            // Turn the stone 180 degrees
            robot.lift.turn(2);

            // Give it time to turn
            sleep(500);

            // Bring the lift down a little bit so it doesn't bounce when we drop it
            robot.lift.elevate(-1);

            // Give the lift a little time to come down
            sleep(500);

            // Drop the stone on the foundation
            robot.lift.release();

            // Give it some time to fall
            sleep(500);

            
            // Retract the extension, turn the grabber back in, and drop the lift all at the same time
            robot.lift.retract();

            robot.lift.turn(-2);

            robot.lift.min_lift();

        } else {

            // If the foundation wasn't moved earlier we are just doing to deliver the stone, not place it
            
            robot.drive_train.odo_move(10, 25, Math.PI/2, 1);

            robot.drive_train.odo_move(52, 25, Math.PI/2, 1);

            // Run the intake backwards to spit it out
            robot.feeder.spin(-1);

            // Drive backwards a bit so the stone hits the intake wheels
            robot.drive_train.odo_move(48, 25, Math.PI / 2, 0.5);

            // Once the stone is out stop spinning
            robot.feeder.spin(0);

        }


        // Park on the tape at the end
        if (PARK == FAR) {

            if (FOUNDATION == true) {
                robot.drive_train.odo_move(36, 25, -Math.PI / 2, 1);
            } else {
                robot.drive_train.odo_move(36, 25, Math.PI / 2, 1);
            }

        }

    }

    @Override
    public void on_stop() {

    }
}
