package entities.trafficlight;

import entities.lightbulb.Lightbulb;

import java.util.ArrayList;

public enum TrafficLightState {

    OFF {
        @Override
        TrafficLightState turnOn(ArrayList<Lightbulb> bulbs) {
            System.out.println("Traffic light going in standby.");

            bulbs.stream().skip(1).forEach((l) ->
                    l.performRequest()
            );

            return STANDBY;
        }

        @Override
        TrafficLightState turnOff(ArrayList<Lightbulb> bulbs) {
            System.out.println("Already Off.");
            return OFF;
        }

        @Override
        TrafficLightState engageManualMode() {
            System.out.println("You must turn on the entities.trafficlight.TrafficLight first");
            return OFF;
        }

        @Override
        TrafficLightState engageAutoMode() {
            System.out.println("You must turn on the entities.trafficlight.TrafficLight first");
            return OFF;
        }

        @Override
        String getStateString() {
            return "OFF";
        }

        //        ON FullON state we turn all the lights ON
        @Override
        TrafficLightState engageFullON(ArrayList<Lightbulb> bulbs) {
            for (Lightbulb l :
                    bulbs) {
                l.performRequest();
            }
            return FULLON;
        }
    },

    STANDBY {
        @Override
        TrafficLightState turnOn(ArrayList<Lightbulb> bulbs) {
            System.out.println("Already On.");
            return STANDBY;
        }

        @Override
        TrafficLightState turnOff(ArrayList<Lightbulb> bulbs) {
            System.out.println("turning the trafficLight off.");
            for (Lightbulb l :
                    bulbs) {
                l.performRequest("OFF");
            }
            return OFF;
        }

        @Override
        TrafficLightState engageManualMode() {
            System.out.println("engaging Manual Mode.");
            return MANUAL;
        }

        @Override
        TrafficLightState engageAutoMode() {
            System.out.println("engaging Automatic Mode.");
            return AUTO;
        }

        @Override
        String getStateString() {
            return "STANDBY";
        }

        @Override
        TrafficLightState engageFullON(ArrayList<Lightbulb> bulbs) {
            System.out.println("Goind to fullOn");
            return FULLON;
        }

    },

    MANUAL {
        @Override
        TrafficLightState turnOn(ArrayList<Lightbulb> bulbs) {
            System.out.println("Already On");
            return MANUAL;
        }

        @Override
        TrafficLightState turnOff(ArrayList<Lightbulb> bulbs) {
            System.out.println("Going to standby");
            return STANDBY;
        }

        @Override
        TrafficLightState engageManualMode() {
            System.out.println("Already in Manual Mode");
            return MANUAL;
        }

        @Override
        TrafficLightState engageAutoMode() {
            System.out.println("Going to Automatic mode");
            return AUTO;
        }

        @Override
        String getStateString() {
            return "MANUAL";
        }

        @Override
        TrafficLightState engageFullON(ArrayList<Lightbulb> bulbs) {
            System.out.println("Goind to fullOn");
            return FULLON;
        }
    },
    AUTO {
        @Override
        TrafficLightState turnOn(ArrayList<Lightbulb> bulbs) {
            System.out.println("Already On");
            return MANUAL;
        }

        @Override
        TrafficLightState turnOff(ArrayList<Lightbulb> bulbs) {
            System.out.println("Going to standby");
            return STANDBY;
        }

        @Override
        TrafficLightState engageManualMode() {
            System.out.println("Going to manual mode");
            return MANUAL;
        }

        @Override
        TrafficLightState engageAutoMode() {
            System.out.println("already in auto");
            return AUTO;
        }

        @Override
        String getStateString() {
            return "AUTO";
        }

        @Override
        TrafficLightState engageFullON(ArrayList<Lightbulb> bulbs) {
            System.out.println("Goind to fullOn");
            return FULLON;
        }
    },
    /**
     * FULL ON state
     * Every light is on
     * This state is used for the editor
     */
    FULLON {
        /**
         * We always go to STAND BY before turning ON
         * STANDBY mode means turning all lights OFF but the first one
         */
        @Override
        TrafficLightState turnOn(ArrayList<Lightbulb> bulbs) {

            bulbs.stream().skip(1).forEach((l) -> {
                l.performRequest();
            });

            return STANDBY;
        }

        @Override
        TrafficLightState turnOff(ArrayList<Lightbulb> bulbs) {
            return OFF;
        }

        @Override
        TrafficLightState engageManualMode() {
            System.out.println("engaging Manual Mode.");
            return MANUAL;
        }

        @Override
        TrafficLightState engageAutoMode() {
            System.out.println("engaging Automatic Mode.");
            return AUTO;
        }

        @Override
        String getStateString() {
            return "STANDBY";
        }

        @Override
        TrafficLightState engageFullON(ArrayList<Lightbulb> bulbs) {
            System.out.println("already in fullOn");
            return FULLON;
        }
    };


    abstract TrafficLightState turnOn(ArrayList<Lightbulb> bulbs);

    abstract TrafficLightState turnOff(ArrayList<Lightbulb> bulbs);

    abstract TrafficLightState engageManualMode();

    abstract TrafficLightState engageAutoMode();

    abstract TrafficLightState engageFullON(ArrayList<Lightbulb> bulbs);

    abstract String getStateString();

}
