package tools;

import controllers.trafficlight.TrafficlightController;
import entities.lightbulb.Lightbulb;
import entities.trafficlight.TrafficLight;

import java.util.ArrayList;

public class Blinker implements Runnable {

    private ArrayList<Lightbulb> lightbulbs;
    private TrafficlightController trafficlightController;
    private TrafficLight trafficLight;

    public Blinker(ArrayList<Lightbulb> lightbulbs, TrafficlightController trafficlightController, TrafficLight trafficLight) {
        this.lightbulbs = lightbulbs;
        this.trafficlightController = trafficlightController;
        this.trafficLight = trafficLight;
    }

    public void start() {
        Thread blinkerThread = new Thread(this);
        blinkerThread.start();
    }

    @Override
    public void run() {

        for (Lightbulb l :
                this.lightbulbs) {
            l.performRequest();
        }
        trafficlightController.initData(trafficLight, false);
        try {
            Thread.sleep(1000);
            this.run();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
