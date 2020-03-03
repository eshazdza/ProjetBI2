package controller.trafficlight;

import controller.lightbulb.Lightbulb;

import java.util.ArrayList;

public class UniColorTrafficLight extends TrafficLight {


    /* *******************   CONSTRUCTORS         ******************* */

    public UniColorTrafficLight(ArrayList<Lightbulb> lightbulbs) {
        super(lightbulbs);
    }

    public UniColorTrafficLight(Lightbulb lightbulb) {
        super(lightbulb);
    }

    public UniColorTrafficLight() {
        super();
    }

    /* *******************   END   CONSTRUCTORS      ******************* */


    /**
     * @param lightbulb LightBulb
     */
    @Override
    public void addLightBulb(Lightbulb lightbulb) {
        if (this.lightbulbs.size() == 1) {
            System.out.println("cannot add anymore controller.lightbulb (max 1).");
        } else {
            super.addLightBulb(lightbulb);
        }
    }

    public void switchLight() {
        if (this.state == TrafficLightState.OFF) {
            System.out.println("system is off, cannot turn on the light");
        } else {
            this.lightbulbs.get(0).performRequest();
        }
    }

}
