package entities.trafficlight;

import entities.lightbulb.Lightbulb;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;

import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

public class TrafficLight extends StackPane implements Serializable, Cloneable {

    protected ArrayList<Lightbulb> lightbulbs;
    protected transient TrafficLightState state;
    private boolean isBinded;

    private String name = null;

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

    public void setState(String state) {
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

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }


    @Override
    public String toString() {
        return "TrafficLight{" +
                "lightbulbs=" + lightbulbs +
                ", state=" + state +
                ", isBinded=" + isBinded +
                ", name='" + name + '\'' +
                '}';
    }

    private void readObject(ObjectInputStream ois) throws ClassNotFoundException, IOException {

        ois.defaultReadObject();

        this.setState("OFF");

    }

    private void writeObject(ObjectOutputStream oos) throws IOException {

        oos.defaultWriteObject();

        oos.writeBytes(state.getStateString());

    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TrafficLight that = (TrafficLight) o;
        return isBinded == that.isBinded &&
                Objects.equals(lightbulbs, that.lightbulbs) &&
                Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(lightbulbs, state, isBinded, name);
    }
}
