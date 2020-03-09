package entities.intersection;

import entities.direction.Direction;
import entities.triggerButton.TriggerButton;

import java.util.ArrayList;

public class ControlledIntersection extends Intersection {

    private ControlledIntersectionState state;
    private TriggerButton triggerButton;

    /**
     * Constructor : Intersection is always OFF when created.
     *
     * @param directions Direction List
     * @param state      IntersectionState
     */
    public ControlledIntersection(ArrayList<Direction> directions, IntersectionState state) {
        super(directions, state);
        this.state = ControlledIntersectionState.OFF;
        this.triggerButton = new TriggerButton();
    }

    /**
     * @param direction1 Direction
     * @param direction2 Direction
     * @param state      IntersectionState
     */
    public ControlledIntersection(Direction direction1, Direction direction2, IntersectionState state) {
        super(direction1, direction2, state);
        this.state = ControlledIntersectionState.OFF;
        this.triggerButton = new TriggerButton();
    }



}
