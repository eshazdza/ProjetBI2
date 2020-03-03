
import controller.direction.Direction;
import controller.direction.DirectionException;
import controller.factory.Factory;
import controller.factory.FactoryException;
import controller.intersection.Intersection;
import controller.lightbulb.Lightbulb;
import controller.trafficlight.TrafficLight;
import java.util.ArrayList;

public class MainV1 {
    public static void main(String[] args) {
        Lightbulb lightbulb = new Lightbulb();
        Lightbulb lightbulb2 = new Lightbulb();
        Lightbulb lightbulb3 = new Lightbulb();

        lightbulb.performRequest();
        lightbulb.performRequest();
        lightbulb.performRequest();
        lightbulb.performRequest();
        lightbulb.performRequest();
        lightbulb.performRequest();

        ArrayList<Lightbulb> lightbulbs = new ArrayList<>();
        lightbulbs.add(lightbulb);
//        lightbulbs.add(lightbulb2);
//        lightbulbs.add(lightbulb3);

        TrafficLight trafficLight = Factory.makeTrafficLight(lightbulbs);

//    trafficLight.addLightBulb(lightbulb3);

        trafficLight.performRequest("OFF");
        trafficLight.performRequest("STANDBY");
        trafficLight.performRequest("STANDBY");
        trafficLight.performRequest("OFF");
        trafficLight.performRequest("MANUAL");
        trafficLight.performRequest("AUTO");
        System.out.println();
        trafficLight.performRequest("STANDBY");
        trafficLight.performRequest("MANUAL");
        trafficLight.performRequest("AUTO");
        trafficLight.performRequest("MANUAL");
        trafficLight.performRequest("OFF");
        trafficLight.performRequest("OFF");
        trafficLight.performRequest("STANDBY");


        System.out.println();

        trafficLight.switchLight();

        System.out.println(trafficLight);

        Direction direction = Factory.makeUncontrolledDirection();

        System.out.println(direction);
        try {
            direction.addTrafficLight(trafficLight);
        } catch (DirectionException e) {
            System.out.println(e.getMessage());
        }

        Direction direction1 = Factory.makeUncontrolledDirection();
        TrafficLight trafficLight1 = new TrafficLight();

        try {
            direction1.addTrafficLight(trafficLight1);
        } catch (DirectionException e) {
            System.out.println(e.getMessage());
        }

        ArrayList<Direction> directions = new ArrayList<>();
        directions.add(direction);
        directions.add(direction1);

        try {
            Intersection intersection = Factory.makeIntersectionFromList(directions);
            System.out.println(intersection.getState());
        } catch (FactoryException e) {
            System.out.println(e.getMessage());
        }

        Direction direction2 = Factory.makeUncontrolledDirection();
        Direction direction3 = Factory.makeUncontrolledDirection();

        try {
            Intersection intersection2 = Factory.makeIntersection(direction2, direction3);
            System.out.println(intersection2.getState());
        } catch (FactoryException e) {
            System.out.println(e.getMessage());
        }

    }
}
