package org.firstinspires.ftc.teamcode.robots;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.components.Component;
import org.firstinspires.ftc.teamcode.components.Component;
import org.openftc.revextensions2.ExpansionHubEx;
import org.openftc.revextensions2.RevBulkData;

import java.util.ArrayList;

public class Robot {

    HardwareMap hwmap;
    public OpMode opmode;
    public LinearOpMode lopmode;

    ArrayList<Component> components = new ArrayList<>();

    public String name;

    boolean running = false;

    protected ExpansionHubEx expansion_hub_1;
    protected ExpansionHubEx expansion_hub_2;

    public RevBulkData bulk_data_1;
    public RevBulkData bulk_data_2;

    protected long last_update = 0;
    protected int update_freq = 0;

    protected int cycle = 0;

    Runnable update_thread;

    class UpdateThread implements Runnable {

        Robot robot;

        public UpdateThread(Robot robot) {
            super();
            this.robot = robot;
        }

        @Override
        public void run() {
            while(robot.running) {
                robot.update();
            }
        }
    }

    public Robot(OpMode opmode) { // FIRST THIS IS *YOUR* FAULT IT HAS TO BE THIS WAY AND I HATE IT AND YOU SHOULD BE ASHAMED
        if (opmode instanceof LinearOpMode) {
            this.lopmode = (LinearOpMode) opmode;
        } else {
            this.opmode = opmode;
        }

        this.hwmap  = opmode.hardwareMap;
        registerHardware(this.hwmap);
    }

    public void startup() {
        running = true;
        for (Component component : components) {
            component.startup();
        }
        update_thread = new UpdateThread(this);
        new Thread(update_thread).start();
    }

    public void shutdown() {
        running = false;
        for (Component component : components) {
            component.shutdown();
        }
    }

    public void update() {

        last_update = System.nanoTime();

        for (Component component : components) {
            component.update(opmode);
        }

        bulk_data_1 = expansion_hub_1.getBulkInputData();
        bulk_data_2 = expansion_hub_2.getBulkInputData();

        long update_duration = System.nanoTime()-last_update;
        update_freq = (update_duration/(double)1000000000) != 0 ? (int)(1/(update_duration/(double)1000000000)) : Integer.MAX_VALUE;

        cycle++;
    }

    public void updateTelemetry() {
        opmode.telemetry.addData("[RBT "+name+"]", components.size()+" components");
        opmode.telemetry.addData("FREQ", update_freq);

        for (Component component : components) {
            component.updateTelemetry(opmode.telemetry);
        }

        opmode.telemetry.update();
    }

    public void registerComponent(Component component) {
        component.registerHardware(hwmap);
        components.add(component);
    }

    public void registerHardware(HardwareMap hwmap) {
        expansion_hub_1 = hwmap.get(ExpansionHubEx.class, "Expansion Hub 1");
        expansion_hub_2 = hwmap.get(ExpansionHubEx.class, "Expansion Hub 2");
    }
}