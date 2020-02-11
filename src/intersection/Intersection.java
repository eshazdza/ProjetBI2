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


    @Override
    public String toString() {
        return "Intersection{" +
                "directions=" + directions +
                ", state=" + state +
                '}';
    }
}
