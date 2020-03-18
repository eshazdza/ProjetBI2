package entities.lightbulb;

public enum LightbulbState {


    OFF {
        @Override
        LightbulbState switchLight() {
            System.out.println("turning on the light.");
            return ON;
        }
        @Override
        String getStateString(){
            return "OFF";
        }
    },
    ON {
        @Override
        LightbulbState switchLight() {
            System.out.println("turn off the light.");
            return OFF;
        }
        @Override
        String getStateString(){
            return "ON";
        }
    };

    abstract LightbulbState switchLight();
    abstract String getStateString();

}
