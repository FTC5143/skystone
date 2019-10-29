package org.firstinspires.ftc.teamcode.util;

import android.view.KeyEvent;
import android.view.MotionEvent;

import com.qualcomm.robotcore.hardware.Gamepad;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

///~~~ Less bad Gamepad, brought to you by qwertyquerty ~~~///
//~ Probably very badly written but you're free to improve ~//

public class LessBadGamepad extends Gamepad {
    /// This wouldn't be so bad if Gampead code itself wasn't so bad ///

    /// EXAMPLE USAGE ///

    /*
     * gamepad1.on_press(KeyEvent.KEYCODE_BUTTON_A, new Runnable() {
     *     @Override
     *     public void run() {
     *         robot.shutdown();
     *     }
     * });
     */

    public HashMap<Integer, ArrayList<Runnable>> press_hooks = new HashMap<Integer, ArrayList<Runnable>>();
    public HashMap<Integer, ArrayList<Runnable>> release_hooks = new HashMap<Integer, ArrayList<Runnable>>();

    // Hook a callback to be called when a key is pressed
    public void on_press(Integer key, Runnable callback) {
        if (press_hooks.get(key) == null) {
            press_hooks.put(key, new ArrayList<Runnable>());
        }
        if (!press_hooks.get(key).contains(callback)) {
            press_hooks.get(key).add(callback);
        }
    }

    // Hook a callback to be called when a key is released
    public void on_release(Integer key, Runnable callback) {
        if (release_hooks.get(key) == null) {
            release_hooks.put(key, new ArrayList<Runnable>());
        }
        if (!release_hooks.get(key).contains(callback)) {
            release_hooks.get(key).add(callback);
        }
    }

    // Handle the calling logic on every KeyEvent
    public void update(android.view.KeyEvent event) {
        super.update(event);

        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            if (press_hooks.get(event.getKeyCode()) != null) {
                for (Runnable callback : press_hooks.get(event.getKeyCode())) {
                    callback.run();
                }
            }
        } else if (event.getAction() == KeyEvent.ACTION_UP) {
            if (release_hooks.get(event.getKeyCode()) != null) {
                for (Runnable callback : release_hooks.get(event.getKeyCode())) {
                    callback.run();
                }
            }
        }
    }
}
