package entities.trafficlight;

import entities.lightbulb.Lightbulb;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

public class TrafficLight extends StackPane implements Serializable {

    protected ArrayList<Lightbulb> lightbulbs;
    protected transient TrafficLightState state;
    private boolean isBinded;

    // Must implement some sort of naming for identifiyng the object when saving or w/e but in the meantime
    private static int id=0;

    /* *******************   CONSTRUCTORS         ******************* */

    public TrafficLight(ArrayList<Lightbulb> lightbulbs) {
        this.lightbulbs = lightbulbs;
        this.state = TrafficLightState.OFF;
        this.isBinded = false;
        id++;
    }

    public TrafficLight(Lightbulb lightbulb) {
        this.lightbulbs = new ArrayList<Lightbulb>();
        this.addLightBulb(lightbulb);
        this.state = TrafficLightState.OFF;
        this.isBinded = false;
        id++;
    }

    public TrafficLight() {
        this.lightbulbs = new ArrayList<Lightbulb>();
        this.addLightBulb(new Lightbulb(Color.RED));
        this.state = TrafficLightState.OFF;
        this.isBinded = false;
        id++;
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

    public static int getTLId() {
        return id;
    }

    public String performRequest(String request) {
        switch (request) {
            case "STANDBY":
                this.state = this.state.turnOn(this.getLightbulbs());
                break;
            case "OFF":
                this.state = this.state.turnOff(this.getLightbulbs());
                break;
            case "MANUAL":
                this.state = this.state.engageManualMode();
                break;
            case "AUTO":
                this.state = this.state.engageAutoMode();
                break;
            case "FULLON":
                this.state = this.state.engageFullON(this.getLightbulbs());
                break;
            case "PANIC":
                this.state = this.state.engagePanicMode(this.getLightbulbs());
                break;
            default:
                return this.state.getStateString();

        }
        return this.state.getStateString();
    }

    public String getStateString() {
        return this.state.getStateString();
    }

    private void setState(String state) {
        switch (state){
            case "OFF":
                this.state =  TrafficLightState.OFF;
                break;
            case "MANUAL":
                this.state =  TrafficLightState.MANUAL;
                break;
            case "AUTO":
                this.state =  TrafficLightState.AUTO;
                break;
            case "FULLON":
                this.state =  TrafficLightState.FULLON;
                break;
            case "PANIC":
                this.state =  TrafficLightState.PANIC;
                break;
            default:this.state =  TrafficLightState.STANDBY;
        }
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

    private void readObject(ObjectInputStream ois) throws ClassNotFoundException, IOException {

        ois.defaultReadObject();

        String state = ois.readLine();
        this.setState(state);

    }

    private void writeObject(ObjectOutputStream oos) throws IOException {

        oos.defaultWriteObject();

        oos.writeBytes(state.getStateString());

    }


}
