public enum TrafficLightState {

    OFF {
        @Override
        TrafficLightState turnOn() {
            System.out.println("Traffic light going in standby.");
            return STANDBY;
        }

        @Override
        TrafficLightState turnOff() {
            System.out.println("Already Off.");
            return OFF;
        }

        @Override
        TrafficLightState engageManualMode() {
            System.out.println("You must turn on the TrafficLight first");
            return OFF;
        }

        @Override
        TrafficLightState engageAutoMode() {
            System.out.println("You must turn on the TrafficLight first");
            return OFF;
        }

    },
    STANDBY {
        @Override
        TrafficLightState turnOn() {
            System.out.println("Already On.");
            return STANDBY;
        }

        @Override
        TrafficLightState turnOff() {
            System.out.println("turning the trafficLight off.");
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

    },

    MANUAL {
        @Override
        TrafficLightState turnOn() {
            System.out.println("Already On");
            return MANUAL;
        }

        @Override
        TrafficLightState turnOff() {
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
    },
    AUTO {
        @Override
        TrafficLightState turnOn() {
            System.out.println("Already On");
            return MANUAL;
        }

        @Override
        TrafficLightState turnOff() {
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
    };


    abstract TrafficLightState turnOn();

    abstract TrafficLightState turnOff();

    abstract TrafficLightState engageManualMode();

    abstract TrafficLightState engageAutoMode();

}
