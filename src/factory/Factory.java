package factory;

import direction.Direction;
import intersection.ControlledIntersection;
import intersection.Intersection;
import intersection.IntersectionState;
import lightbulb.Lightbulb;
import trafficlight.BiColorTrafficLight;
import trafficlight.TrafficLight;
import trafficlight.TriColorTrafficLight;
import trafficlight.UniColorTrafficLight;

import java.util.ArrayList;

public class Factory extends Throwable {

    /* *************** TRAFFIC LIGHT *************** */

    public static UniColorTrafficLight makeTrafficLight() {
        return new UniColorTrafficLight();
    }

    public static UniColorTrafficLight makeTrafficLight(Lightbulb lightbulb) {
        return new UniColorTrafficLight(lightbulb);
    }


    public static TrafficLight makeTrafficLight(ArrayList<Lightbulb> lightbulbs) {
        switch (lightbulbs.size()) {
            case 1:
                return new UniColorTrafficLight(lightbulbs);

            case 2:
                return new BiColorTrafficLight(lightbulbs);

            case 3:
                return new TriColorTrafficLight(lightbulbs);

        }
        return new TrafficLight();
    }

    /* *************** END TRAFFIC LIGHT *************** */


    /* *************** DIRECTION *************** */


    /* *************** END DIRECTION *************** */

    /* *************** INTERSECTION *************** */
    public static Intersection makeIntersectionFromList(ArrayList<Direction> directions) throws FactoryException {
        if (directions.size() < 2) {
            throw new FactoryException("Intersection must contain a least two directions.");
        } else {
//            check if every direction has a traffic light
            int trafficLight = 0;

            for (Direction direction :
                    directions) {
                if (direction.hasTrafficLight()) {
                    trafficLight++;
                }
            }

//            if at least one direction has a traffic light, every direction must have one.
            if (trafficLight > 0 && trafficLight != directions.size()) {
                throw new FactoryException("One direction does not have a traffic light. Cannot create intersection.");
            } else if (trafficLight == 0) {
//                If no Direction has a traffic light : the Intersection state is uncontrolled
                return new Intersection(directions, IntersectionState.YOLO);
            } else {
                return new ControlledIntersection(directions, IntersectionState.CONTROLLED);
            }
        }
    }


    /**
     * @param direction1 Direction
     * @param direction2 Direction
     * @return Intersection
     * @throws FactoryException
     */
    public static Intersection makeIntersection(Direction direction1, Direction direction2) throws FactoryException {
        if (direction1.hasTrafficLight()) {
            if (direction2.hasTrafficLight()) {
                return new ControlledIntersection(direction1, direction2, IntersectionState.CONTROLLED);
            } else {
                throw new FactoryException("One direction does not have a traffic light. Cannot create intersection.");
            }
        } else {
            if (direction2.hasTrafficLight()) {
                throw new FactoryException("One direction does not have a traffic light. Cannot create intersection.");
            } else {
                return new Intersection(direction1, direction2, IntersectionState.YOLO);
            }
        }
    }

    /* *************** END INTERSECTION *************** */


}
