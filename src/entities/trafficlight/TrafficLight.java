package entities.trafficlight;

import entities.lightbulb.Lightbulb;

import java.util.ArrayList;

import javafx.scene.paint.Color;

public class TrafficLight {

    protected ArrayList<Lightbulb> lightbulbs;
    protected TrafficLightState state;
    protected boolean isBinded;

    /* *******************   CONSTRUCTORS         ******************* */

    public TrafficLight(ArrayList<Lightbulb> lightbulbs) {
        this.lightbulbs = lightbulbs;
        this.state = TrafficLightState.OFF;
        this.isBinded = false;
    }

    public TrafficLight(Lightbulb lightbulb) {
        this.lightbulbs = new ArrayList<Lightbulb>();
        this.addLightBulb(lightbulb);
        this.state = TrafficLightState.OFF;
        this.isBinded = false;
    }

    public TrafficLight() {
        this.lightbulbs = new ArrayList<Lightbulb>();
        this.addLightBulb(new Lightbulb(Color.RED));
        this.addLightBulb(new Lightbulb(Color.BLUE));
        this.state = TrafficLightState.OFF;
        this.isBinded = false;
    }

    /* *******************   END   CONSTRUCTORS      ******************* */

    public ArrayList<Lightbulb> getLightbulbs() {
        return lightbulbs;
    }

    public void setLightbulbs(ArrayList<Lightbulb> lightbulbs) {
        this.lightbulbs = lightbulbs;
    }

    public void addLightBulb(Lightbulb lightbulb) {
        this.lightbulbs.add(lightbulb);
    }

    public String performRequest(String request) {
        switch (request) {
            case "STANDBY":
                this.state = this.state.turnOn();
                System.out.println(this.state.getStateString());
                break;
            case "OFF":
                this.state = this.state.turnOff();
                System.out.println(this.state.getStateString());
                break;
            case "MANUAL":
                this.state = this.state.engageManualMode();
                System.out.println(this.state.getStateString());
                break;
            case "AUTO":
                this.state = this.state.engageAutoMode();
                System.out.println(this.state.getStateString());
                break;
            default:
                return this.state.getStateString();

        }
        return this.state.getStateString();
    }

    public boolean isBinded() {
        return isBinded;
    }

    public void setBinded(boolean binded) {
        isBinded = binded;
    }


    @Override
    public String toString() {
        return "entities.trafficlight.TrafficLight{" +
                "lightbulbs=" + lightbulbs +
                ", state=" + state +
                this.getClass() +
                '}';
    }

    public void switchLight() {
    }

    ;
}
