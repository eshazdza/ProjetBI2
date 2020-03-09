package factory;


import entities.direction.Direction;
import entities.intersection.ControlledIntersection;
import entities.intersection.Intersection;
import entities.intersection.IntersectionState;
import entities.lightbulb.Lightbulb;
import entities.trafficlight.BiColorTrafficLight;
import entities.trafficlight.TrafficLight;
import entities.trafficlight.TriColorTrafficLight;
import entities.trafficlight.UniColorTrafficLight;

import java.util.ArrayList;

public class Factory extends Throwable {


    /* *************** LIGHTBULB *************** */
    public static Lightbulb makeLightbulb(){
        return new Lightbulb();
    }
    /* ************ END LIGHTBULB *************** */

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
    public static Direction makeControlledDirectionFromList(boolean hasPriority, ArrayList<TrafficLight> trafficLights) {
        if (trafficLights.size() > 0) {
            return new Direction(hasPriority, trafficLights);
        } else {
            return new Direction(hasPriority);
        }
    }

    public static Direction makeUncontrolledDirection(boolean hasPriority) {
        return new Direction(hasPriority);
    }

    public static Direction makeUncontrolledDirection() {
        return new Direction();
    }

    /* *************** END DIRECTION *************** */

    /* *************** INTERSECTION *************** */
    public static Intersection makeIntersectionFromList(ArrayList<Direction> directions) throws FactoryException {
        if (directions.size() < 2) {
            throw new FactoryException("Intersection must contain a least two directions.");
        } else {
//            check if every entities.direction has a traffic light
            int trafficLight = 0;

            for (Direction direction :
                    directions) {
                if (direction.hasTrafficLight()) {
                    trafficLight++;
                }
//                TODO : handle priority
            }

//            if at least one entities.direction has a traffic light, every entities.direction must have one.
            if (trafficLight > 0 && trafficLight != directions.size()) {
                throw new FactoryException("One entities.direction does not have a traffic light. Cannot create entities.intersection.");
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
//        TODO : handle priority
        if (direction1.hasTrafficLight()) {
            if (direction2.hasTrafficLight()) {
                return new ControlledIntersection(direction1, direction2, IntersectionState.CONTROLLED);
            } else {
                throw new FactoryException("One entities.direction does not have a traffic light. Cannot create entities.intersection.");
            }
        } else {
            if (direction2.hasTrafficLight()) {
                throw new FactoryException("One entities.direction does not have a traffic light. Cannot create entities.intersection.");
            } else {
                return new Intersection(direction1, direction2, IntersectionState.YOLO);
            }
        }
    }

    /* *************** END INTERSECTION *************** */


}
