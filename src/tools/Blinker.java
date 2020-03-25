package tools;

import controllers.trafficlight.TrafficlightController;
import entities.lightbulb.Lightbulb;
import entities.trafficlight.TrafficLight;
import javafx.application.Platform;

import java.util.ArrayList;

public class Blinker implements Runnable {

    private ArrayList<Lightbulb> lightbulbs;
    private TrafficlightController trafficlightController;
    private TrafficLight trafficLight;
    private boolean stopThread = false;

    public Blinker(ArrayList<Lightbulb> lightbulbs, TrafficlightController trafficlightController, TrafficLight trafficLight) {
        this.lightbulbs = lightbulbs;
        this.trafficlightController = trafficlightController;
        this.trafficLight = trafficLight;
    }

    public void stopThread() {
        if (lightbulbs.get(0).getStateString().equals("OFF")) {
            try {
                Thread.sleep(499);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
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
