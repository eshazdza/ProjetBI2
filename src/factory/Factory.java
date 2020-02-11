package factory;

import direction.Direction;
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
    public static Intersection makeIntersection(ArrayList<Direction> directions) throws FactoryException {
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
                return new Intersection(directions, IntersectionState.YOLO);
            } else {
                return new Intersection(directions, IntersectionState.CONTROLLED);
            }
        }
    }

    /* *************** END INTERSECTION *************** */


}
