package entities.lightbulb;

import javafx.scene.paint.Color;


public enum LightbulbState {

    OFF {
        @Override
        LightbulbState switchLight(Lightbulb lightbulb) {
            lightbulb.setFill(lightbulb.getColor());
            return ON;
        }

        @Override
        LightbulbState turnON(Lightbulb lightbulb) {
            lightbulb.setFill(lightbulb.getColor());
            return ON;
        }

        @Override
        LightbulbState turnOFF(Lightbulb lightbulb) {
            System.out.println("Already OFF");
            return OFF;
        }


        @Override
        String getStateString() {
            return "OFF";
        }
    },

    ON {
        @Override
        LightbulbState switchLight(Lightbulb lightbulb) {
            lightbulb.setFill(Color.BLACK);
            return OFF;
        }

        @Override
        LightbulbState turnON(Lightbulb lightbulb) {
            System.out.println("Already ON");
            return ON;
        }

        @Override
        LightbulbState turnOFF(Lightbulb lightbulb) {
            lightbulb.setFill(Color.BLACK);
            return OFF;
        }

        @Override
        String getStateString() {
            return "ON";
        }
    };

    abstract LightbulbState switchLight(Lightbulb lightbulb);

    abstract LightbulbState turnON(Lightbulb lightbulb);

    abstract LightbulbState turnOFF(Lightbulb lightbulb);

    abstract String getStateString();

}
