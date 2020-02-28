package intersection;

import direction.Direction;

import java.util.ArrayList;

public class ControlledIntersection extends Intersection {

    private ControlledIntersectionState state;


    /**
     * Constructor : Intersection is always OFF when created.
     * @param directions Direction List
     * @param state IntersectionState
     */
    public ControlledIntersection(ArrayList<Direction> directions, IntersectionState state) {
        super(directions, state);
        this.state = ControlledIntersectionState.OFF;
    }

    public ControlledIntersection(Direction direction1, Direction direction2, IntersectionState state){
        super(direction1, direction2, state);
        this.state = ControlledIntersectionState.OFF;
    }



}
