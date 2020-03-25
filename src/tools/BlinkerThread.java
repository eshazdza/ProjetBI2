package tools;

import controllers.trafficlight.TrafficlightController;
import entities.lightbulb.Lightbulb;
import entities.trafficlight.TrafficLight;
import javafx.application.Platform;

import java.util.ArrayList;

public class BlinkerThread implements Runnable {

    private ArrayList<Lightbulb> lightbulbs;
    private TrafficlightController trafficlightController;
    private TrafficLight trafficLight;
    private boolean stopThread = false;

    public BlinkerThread(ArrayList<Lightbulb> lightbulbs, TrafficlightController trafficlightController, TrafficLight trafficLight) {
        this.lightbulbs = lightbulbs;
        this.trafficlightController = trafficlightController;
        this.trafficLight = trafficLight;
    }

    public void stopThread() {
        if (lightbulbs.size() > 0 && lightbulbs.get(0).getStateString().equals("OFF")) {
            for (Lightbulb l :
                    lightbulbs) {
                l.performRequest();
            }
            trafficlightController.initData(trafficLight, false);

        }
        this.stopThread = true;
    }


    public void startThread() {
        this.stopThread = false;
    }

    @Override
    public void run() {
        while (!stopThread) {
            try {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        for (Lightbulb l :
                                lightbulbs) {
                            l.performRequest();
                        }
                        trafficlightController.initData(trafficLight, false);
                    }
                });

                Thread.sleep(500);
                run();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
