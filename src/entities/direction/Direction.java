package entities.direction;

import entities.trafficlight.TrafficLight;
import javafx.scene.layout.AnchorPane;

import java.util.ArrayList;

public class Direction extends AnchorPane {

    private DirectionState state;
    private boolean hasPriority;
    private String name;
    private ArrayList<TrafficLight> trafficLights = new ArrayList<>();
    private ArrayList<Direction> intersectsWith = new ArrayList();

    public Direction(boolean hasPriority, ArrayList trafficLights) {
        this.state = DirectionState.UNCONTROLLED;
        this.hasPriority = hasPriority;
        this.trafficLights = trafficLights;
    }

    public Direction(boolean hasPriority) {
        this.state = DirectionState.UNCONTROLLED;
        this.hasPriority = hasPriority;
    }

    public Direction() {
        this.state = DirectionState.UNCONTROLLED;
        this.hasPriority = false;
    }

    public void addTrafficLight(TrafficLight trafficLight) throws DirectionException {
//        TODO :
//        You have to be able to use the same "model" of traffic light on different directions.
//        So this doesn't really make sense except for the fact that the Same tl can't be used twice
//         SO we need to copy it without the bind status if it's already bound.
        if (!trafficLight.isBinded()) {
            this.trafficLights.add(trafficLight);
            trafficLight.setBinded(true);
            this.state = DirectionState.CONTROLLED;
        } else {
            throw new DirectionException("Traffic light is already binded to another entities.DirectionController. Cannot add the traffic light.");
        }
    }

    public void removeTrafficLight(TrafficLight trafficLight) {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<TrafficLight> getTrafficLights() {
        return trafficLights;
    }

    public boolean hasTrafficLight() {
        if (this.trafficLights.size() > 0) {
            return true;
        }
        return false;
    }

    public String getStateString(){
        return this.state.getStateString();
    }

    @Override
    public String toString() {
        return "DirectionController{" +
                "state=" + state +
                ", hasPriority=" + hasPriority +
                ", trafficLights=" + trafficLights +
                '}';
    }
}
