package entities.intersection;

import entities.direction.Direction;
import entities.phase.Phase;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

import java.io.Serializable;
import java.util.ArrayList;

public class Intersection extends Pane implements Serializable {

    private ArrayList<Direction> directions;
    private ArrayList<Phase> phases = new ArrayList<>();
    private IntersectionState state;
    private String name;



    public Intersection() {
        this.directions = new ArrayList<>();
        this.state = IntersectionState.YOLO;
    }

    public Intersection(Direction direction) {

        this.directions = new ArrayList<>();
        this.directions.add(direction);

        if (direction.getStateString().equals("CONTROLLED")) {
            this.state = IntersectionState.CONTROLLED;
        } else {
            this.state = IntersectionState.YOLO;
        }
    }

    public Intersection(ArrayList<Direction> directions, IntersectionState state) {
        this.directions = directions;
        this.state = state;
    }

    public Intersection(Direction direction1, Direction direction2, IntersectionState state) {
        this.directions = new ArrayList<>();
        this.addDirection(direction1);
        this.addDirection(direction2);
        this.state = state;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Phase> getPhases() {
        return phases;
    }

    public void setPhases(ArrayList<Phase> phases) {
        this.phases = phases;
    }

    public void addPhase(Phase phase){
        this.phases.add(phase);
    }


    @Override
    public String toString() {
        return "Intersection{" +
                "directions=" + directions +
                ", state=" + state +
                '}';
    }

    public void addDirection(Direction direction) {
        this.directions.add(direction);
    }

    public void removeDirection(Direction direction){
        if ( this.directions.remove(direction)){
            System.out.println("suceess removed");
        }else{
            System.out.println("not found");
        }

    }

    public IntersectionState getState() {
        return state.readState();
    }

    public void setState(IntersectionState state) {
        this.state = state;
    }

    public ArrayList<Direction> getDirections() {
        return directions;
    }
}
