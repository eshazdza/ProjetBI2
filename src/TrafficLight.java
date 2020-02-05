import java.awt.*;
import java.util.ArrayList;

public class TrafficLight {

    protected ArrayList<Lightbulb> lightbulbs;
    protected TrafficLightState state;

    /* *******************   CONSTRUCTORS         ******************* */

    public TrafficLight(ArrayList<Lightbulb> lightbulbs) {
        this.lightbulbs = lightbulbs;
        this.state = TrafficLightState.OFF;
    }

    public TrafficLight(Lightbulb lightbulb) {
        this.lightbulbs = new ArrayList<Lightbulb>();
        this.addLightBulb(lightbulb);
        this.state = TrafficLightState.OFF;
    }

    public TrafficLight(){
        this.lightbulbs = new ArrayList<Lightbulb>();
        this.addLightBulb(new Lightbulb(Color.RED));
        this.state = TrafficLightState.OFF;
    }

    /* *******************   END   CONSTRUCTORS      ******************* */

    public ArrayList<Lightbulb> getLightbulbs() {
        return lightbulbs;
    }

    public void setLightbulbs(ArrayList<Lightbulb> lightbulbs) {
        this.lightbulbs = lightbulbs;
    }

    public void addLightBulb(Lightbulb lightbulb){
         this.lightbulbs.add(lightbulb);
    }

    public void performRequest(String request){
        switch (request){
            case "STANDBY" :
                this.state = this.state.turnOn();
                break;
            case "OFF":
                this.state = this.state.turnOff();
                break;
            case "MANUAL":
                this.state = this.state.engageManualMode();
                break;
            case "AUTO" :
                this.state = this.state.engageAutoMode();
                break;
        }
    }


    @Override
    public String toString() {
        return "TrafficLight{" +
                "lightbulbs=" + lightbulbs +
                ", state=" + state +
                this.getClass() +
                '}';
    }

    public void switchLight(){};
}
