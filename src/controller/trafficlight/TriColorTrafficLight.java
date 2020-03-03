package controller.trafficlight;

import controller.lightbulb.Lightbulb;

import java.util.ArrayList;

public class TriColorTrafficLight extends TrafficLight {

    public TriColorTrafficLight(ArrayList<Lightbulb> lightbulbs) {
        super(lightbulbs);
    }

    @Override
    public void addLightBulb(Lightbulb lightbulb) {
        if (this.lightbulbs.size() == 3) {
            System.out.println("cannot add anymore controller.lightbulb (max 3).");
        } else {
            super.addLightBulb(lightbulb);
        }
    }
}
