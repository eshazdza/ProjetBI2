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

    public Blinker(ArrayList<Lightbulb> lightbulbs, TrafficlightController trafficlightController, TrafficLight trafficLight) {
        this.lightbulbs = lightbulbs;
        this.trafficlightController = trafficlightController;
        this.trafficLight = trafficLight;
    }

    @Override
    public void run() {

        try
        {
            Platform.runLater(new Runnable()
            {
                @Override
                public void run()
                {
//                        System.out.println(status);
                    for (Lightbulb l :
                            lightbulbs) {
                        l.performRequest();
                    }
                    trafficlightController.initData(trafficLight, false);
                }
            });

            Thread.sleep(500);
            run();
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
    }
}
