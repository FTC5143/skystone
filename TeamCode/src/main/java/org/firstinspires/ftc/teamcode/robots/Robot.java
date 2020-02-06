package org.firstinspires.ftc.teamcode.robots;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
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

    Telemetry telemetry;

    public RevBulkData bulk_data_1;
    public RevBulkData bulk_data_2;

    protected long last_update = System.nanoTime();
    protected int update_freq = 0;

    public int cycle = 0;

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
                //robot.updateTelemetry();
            }
        }
    }

    public Robot(OpMode opmode) { // FIRST THIS IS *YOUR* FAULT IT HAS TO BE THIS WAY AND I HATE IT AND YOU SHOULD BE ASHAMED
        if (opmode instanceof LinearOpMode) {
            this.lopmode = (LinearOpMode) opmode;
            this.telemetry = this.lopmode.telemetry;
        } else {
            this.opmode = opmode;
            this.telemetry = this.opmode.telemetry;
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



        bulk_data_1 = expansion_hub_1.getBulkInputData();

        if(cycle % 10 == 0) {

            bulk_data_2 = expansion_hub_2.getBulkInputData();

        }


        for (Component component : components) {
            component.update(opmode);
        }


        if (cycle % 50 == 0) {

            updateTelemetry();

        }

        if (cycle % 20 == 0) {

            long update_duration = System.nanoTime()-last_update;
            update_freq = ((update_duration/(double)1000000000) * 20) != 0 ? (int)((1/(update_duration/(double)1000000000)) * 20) : Integer.MAX_VALUE;

            last_update = System.nanoTime();

        }

        cycle++;
    }

    public void updateTelemetry() {
        telemetry.addData("[RBT "+name+"]", components.size()+" components");
        telemetry.addData("FREQ", update_freq);

        for (Component component : components) {
            component.updateTelemetry(telemetry);
        }

        telemetry.update();
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