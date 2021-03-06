package entities.trafficlight;

import entities.lightbulb.Lightbulb;

import java.util.ArrayList;

public class BiColorTrafficLight extends TrafficLight {

    public BiColorTrafficLight(ArrayList<Lightbulb> lightbulbs) {
        super(lightbulbs);
    }

    @Override
    public void addLightBulb(Lightbulb lightbulb) {
        if (this.lightbulbs.size() == 2) {
            System.out.println("cannot add anymore entities.lightbulb (max 2).");
        } else {
            super.addLightBulb(lightbulb);
        }
    }


}
