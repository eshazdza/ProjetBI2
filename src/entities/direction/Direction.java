package entities.direction;

import entities.phase.Phase;
import entities.trafficlight.TrafficLight;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;

//public class Direction extends AnchorPane implements Serializable {
public class Direction extends StackPane implements Serializable {

    private DirectionState state;
    private boolean hasPriority;
    private String name;
    private double angle = 0;
    private double posX = 0;
    private double posY = 0;
    private boolean locked;
    private boolean belongsToPhase = false;

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

    public void addTrafficLight(TrafficLight trafficLight)
//            throws DirectionException
    {
//        TODO :
//        You have to be able to use the same "model" of traffic light on different directions.
//        So this doesn't really make sense except for the fact that the Same tl can't be used twice
//        SO we need to copy it without the bind status if it's already bound.
//        if (!trafficLight.isBinded()) {
//            this.trafficLights.add(trafficLight);
//            trafficLight.setBinded(true);
//            this.state = DirectionState.CONTROLLED;
//        } else {
//            throw new DirectionException("Traffic light is already binded to another entities.DirectionController. Cannot add the traffic light.");
//        }

        this.trafficLights.add(trafficLight);
        this.state = DirectionState.CONTROLLED;
    }

    public boolean removeTrafficLight(TrafficLight trafficLight) {
        if (trafficLights.remove(trafficLight)) {
            if (trafficLights.size() < 1) {
                this.state = DirectionState.UNCONTROLLED;
            }
            System.out.println("removed");
            System.out.println("traffc light from direction");
            for (TrafficLight tl :
                    trafficLights) {
                System.out.println(tl);
            }
            return true;
        }
        return false;
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

    public double getAngle() {
        return angle;
    }

    public void setAngle(double angle) {
        this.angle = angle;
        if (this.angle == 360.0) {
            this.angle = 0.0;
        }
    }

    public double getPosX() {
        return posX;
    }

    public void setPosX(double posX) {
        this.posX = posX;
    }

    public double getPosY() {
        return posY;
    }

    public void setPosY(double posY) {
        this.posY = posY;
    }

    public boolean isLocked() {
        return locked;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    public boolean isBelongsToPhase() {
        return belongsToPhase;
    }

    public void setBelongsToPhase(boolean belongsToPhase) {
        this.belongsToPhase = belongsToPhase;
    }

    public boolean hasTrafficLight() {
        if (this.trafficLights.size() > 0) {
            return true;
        }
        return false;
    }

    public void setState(DirectionState state) {
        this.state = state;
    }

    public DirectionState getState() {
        return state;
    }

    public String getStateString() {
        return this.state.getStateString();
    }

    public boolean isHasPriority() {
        return hasPriority;
    }

    public void setHasPriority(boolean hasPriority) {
        this.hasPriority = hasPriority;
    }

    public void setTrafficLights(ArrayList<TrafficLight> trafficLights) {
        this.trafficLights = trafficLights;
    }

    public ArrayList<Direction> getIntersectsWith() {
        return intersectsWith;
    }

    public void setIntersectsWith(ArrayList<Direction> intersectsWith) {
        this.intersectsWith = intersectsWith;
    }

    @Override
    public String toString() {
        return "Direction{" +
                "state=" + state +
                ", hasPriority=" + hasPriority +
                ", name='" + name + '\'' +
                ", angle=" + angle +
                ", locked=" + locked +
                ", belongsToPhase=" + belongsToPhase +
                ", trafficLights=" + trafficLights +
                ", intersectsWith=" + intersectsWith +
                '}';
    }

    private void readObject(ObjectInputStream ois) throws ClassNotFoundException, IOException {
        ois.defaultReadObject();

    }

    private void writeObject(ObjectOutputStream oos) throws IOException {
        oos.defaultWriteObject();

        oos.writeBytes(state.getStateString());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Direction direction = (Direction) o;
        return hasPriority == direction.hasPriority &&
                Double.compare(direction.angle, angle) == 0 &&
                locked == direction.locked &&
                belongsToPhase == direction.belongsToPhase &&
                state == direction.state &&
                Objects.equals(name, direction.name) &&
                Objects.equals(trafficLights, direction.trafficLights) &&
                Objects.equals(intersectsWith, direction.intersectsWith);
    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(state, hasPriority, name, angle, locked, belongsToPhase, phase, trafficLights, intersectsWith);
//    }
}
