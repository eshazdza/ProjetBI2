import java.util.ArrayList;

public class Factory {


    public static UniColorTrafficLight makeTrafficLight() {
        return new UniColorTrafficLight();
    }

    public static UniColorTrafficLight makeTrafficLight(Lightbulb lightbulb){
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
}
