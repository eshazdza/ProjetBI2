package controller.direction;

import controller.trafficlight.TrafficLight;

import java.util.ArrayList;

public class Direction {

    private DirectionState state;
    private boolean hasPriority;
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
        if (!trafficLight.isBinded()) {
            this.trafficLights.add(trafficLight);
            trafficLight.setBinded(true);
            this.state = DirectionState.CONTROLLED;
        } else {
            throw new DirectionException("Traffic light is already binded to another controller.direction. Cannot add the traffic light.");
        }
    }

    public void removeTrafficLight(TrafficLight trafficLight) {

    }


    public boolean hasTrafficLight() {
        if (this.trafficLights.size() > 0) {
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return "Direction{" +
                "state=" + state +
                ", hasPriority=" + hasPriority +
                ", trafficLights=" + trafficLights +
                '}';
    }
}
