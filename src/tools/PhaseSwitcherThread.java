package tools;

import controllers.trafficlight.TrafficlightController;
import entities.lightbulb.Lightbulb;
import entities.trafficlight.TrafficLight;
import javafx.application.Platform;

import java.util.ArrayList;

public class PhaseSwitcherThread implements Runnable {

    private ArrayList<Lightbulb> lightbulbs;
    private TrafficlightController trafficlightController;
    private TrafficLight trafficLight;
    private Lightbulb currentBulb;
    private int i = 0;
    private boolean stopThread = false;

    public PhaseSwitcherThread(ArrayList<Lightbulb> lightbulbs, TrafficlightController trafficlightController, TrafficLight trafficLight) {
//        this.lightbulbs = lightbulbs;
        this.lightbulbs = trafficLight.getLightbulbs();
        this.trafficlightController = trafficlightController;
        this.trafficLight = trafficLight;
    }

    public void stopThread() {
        this.stopThread = true;
    }

    public void startThread() {
        this.stopThread = false;
    }

    @Override
    public void run() {
        if (this.lightbulbs.size() > 1) {

            while (!stopThread) {
                // Can only handle one ON bulb at a time
                currentBulb = trafficlightController.getOnLights().get(0);
                i = lightbulbs.indexOf(currentBulb);
                while (i >= 0) {
                    if (stopThread){
                        break;;
                    }
                    currentBulb = lightbulbs.get(i);
                    try {
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
//                            Turn off the bulb
                                currentBulb.performRequest("OFF");

//                            Turn on the next one (back to the first one if we reached the end of the list)
                                i--;
                                if (i == -1) {
                                    i = lightbulbs.size() - 1;
                                }

                                lightbulbs.get(i).performRequest("ON");

//                                Refresh the view
                                trafficlightController.initData(trafficLight, false);

                            }   // End run
                        });     // End Platform.runLater

                        Thread.sleep(500);

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }           // End try/catch
                }               // End while
            }               // End while
        } else {
            System.out.println("you need at least two bulbs mate");
        }                   // End if/else
    }
}

