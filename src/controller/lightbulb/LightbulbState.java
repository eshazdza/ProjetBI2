package controller.lightbulb;

public enum LightbulbState {


    OFF {
        @Override
        LightbulbState switchLight() {
            System.out.println("turning on the light.");
            return ON;
        }
    },
    ON {
        @Override
        LightbulbState switchLight() {
            System.out.println("turn off the light.");
            return OFF;
        }
    };

    abstract LightbulbState switchLight();

}
