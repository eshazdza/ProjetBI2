package entities.phase;

import controllers.direction.DirectionController;
import entities.direction.Direction;

import java.util.ArrayList;

public class Phase {
    private ArrayList<Direction> directions = new ArrayList<>();
    private String name;
    private ArrayList<DirectionController> directionControllers = new ArrayList<>();

    public ArrayList<Direction> getDirections() {
        return directions;
    }

    public void setDirections(ArrayList<Direction> directions) {
        this.directions = directions;
    }

    public void addDirection(Direction direction) {
        this.directions.add(direction);
    }

    public void removeDirection(Direction direction) {
        this.directions.remove(direction);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<DirectionController> getDirectionControllers() {
        return directionControllers;
    }

    public void setDirectionControllers(ArrayList<DirectionController> directionControllers) {
        this.directionControllers = directionControllers;
    }

    public void addDirectionController(DirectionController directionController) {
        this.directionControllers.add(directionController);
    }

    @Override
    public String toString() {
        return "Phase{" +
                "directions=" + directions +
                ", name='" + name + '\'' +
                '}';
    }
}
