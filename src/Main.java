import java.util.ArrayList;

public class Main {
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


    }
}
