package entities.intersection;

public enum  ControlledIntersectionState {
    OFF {
        @Override
        ControlledIntersectionState turnOn() {
            System.out.println("Traffic light going in standby.");
            return STANDBY;
        }

        @Override
        ControlledIntersectionState turnOff() {
            System.out.println("Already Off.");
            return OFF;
        }

        @Override
        ControlledIntersectionState engageManualMode() {
            System.out.println("You must turn on the entities.trafficlight.TrafficLight first");
            return OFF;
        }

        @Override
        ControlledIntersectionState engageAutoMode() {
            System.out.println("You must turn on the entities.trafficlight.TrafficLight first");
            return OFF;
        }

    },
    STANDBY {
        @Override
        ControlledIntersectionState turnOn() {
            System.out.println("Already On.");
            return STANDBY;
        }

        @Override
        ControlledIntersectionState turnOff() {
            System.out.println("turning the trafficLight off.");
            return OFF;
        }

        @Override
        ControlledIntersectionState engageManualMode() {
            System.out.println("engaging Manual Mode.");
            return MANUAL;
        }

        @Override
        ControlledIntersectionState engageAutoMode() {
            System.out.println("engaging Automatic Mode.");
            return AUTO;
        }

    },

    MANUAL {
        @Override
        ControlledIntersectionState turnOn() {
            System.out.println("Already On");
            return MANUAL;
        }

        @Override
        ControlledIntersectionState turnOff() {
            System.out.println("Going to standby");
            return STANDBY;
        }

        @Override
        ControlledIntersectionState engageManualMode() {
            System.out.println("Already in Manual Mode");
            return MANUAL;
        }

        @Override
        ControlledIntersectionState engageAutoMode() {
            System.out.println("Going to Automatic mode");
            return AUTO;
        }
    },
    AUTO {
        @Override
        ControlledIntersectionState turnOn() {
            System.out.println("Already On");
            return MANUAL;
        }

        @Override
        ControlledIntersectionState turnOff() {
            System.out.println("Going to standby");
            return STANDBY;
        }

        @Override
        ControlledIntersectionState engageManualMode() {
            System.out.println("Going to manual mode");
            return MANUAL;
        }

        @Override
        ControlledIntersectionState engageAutoMode() {
            System.out.println("already in auto");
            return AUTO;
        }
    };


    abstract ControlledIntersectionState turnOn();

    abstract ControlledIntersectionState turnOff();

    abstract ControlledIntersectionState engageManualMode();

    abstract ControlledIntersectionState engageAutoMode();


}
