package intersection;

import direction.Direction;

import java.util.ArrayList;

public class Intersection {
    private ArrayList<Direction> directions;
    private IntersectionState state;

    public Intersection(ArrayList<Direction> directions, IntersectionState state ) {
        this.directions = directions;
        this.state = state;
    }

    public Intersection(Direction direction1, Direction direction2, IntersectionState state){
        this.directions = new ArrayList<>();
        this.addDirection(direction1);
        this.addDirection(direction2);
        this.state = state;
    }

    @Override
    public String toString() {
        return "Intersection{" +
                "directions=" + directions +
                ", state=" + state +
                '}';
    }

    public void addDirection(Direction direction){
        this.directions.add(direction);
    }

    public IntersectionState getState() {
        return state.readState();
    }
}
