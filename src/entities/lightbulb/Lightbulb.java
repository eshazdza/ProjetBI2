package entities.lightbulb;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;


/**
 * entities.lightbulb.LightbulbController
 * We use state pattern for the entities.lightbulb since, while currently trivial, behavior could change in the future (ie. a smarter LightBulb could blink, change color, etc...)
 */
public class Lightbulb extends Circle implements Serializable {

    private static final Long serialVersionUID = 6529685098267757690L;

    private transient Color color;
    private transient LightbulbState state;

    /* *******************   CONSTRUCTORS         ******************* */

    /**
     * entities.lightbulb.LightbulbController color defaults to Orange if no color is defined.
     * Will always be off whenever we first "plug it in"
     */
    public Lightbulb() {
        super();
        this.color = Color.ORANGE;
        this.state = LightbulbState.OFF;
        this.setFill(Color.BLACK);
    }

    /**
     * @param color Color
     */
    public Lightbulb(Color color) {
        super();
        this.color = color;
        this.state = LightbulbState.OFF;
        this.setFill(Color.BLACK);
    }

    /* *******************   END   CONSTRUCTORS      ******************* */


    /* *******************   GETTERS AND SETTERS         ******************* */


    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public void setState(String state) {
        switch (state) {
            case "ON":
                this.state = LightbulbState.ON;
                break;
            case "OFF":
                this.state = LightbulbState.OFF;
                break;
        }
    }

    public String getStateString() {
        return this.state.getStateString();
    }

    /* *******************   END   GETTERS AND SETTERS      ******************* */

    public void performRequest() {
        this.state = this.state.switchLight(this);
    }

    public void performRequest(String request) {
        switch (request) {
            case "ON":
                this.state = this.state.turnON(this);
                break;
            case "OFF":
                this.state = this.state.turnOFF(this);
                break;
            default:
                System.out.println(this.getStateString());
                break;
        }
    }

    @Override
    public String toString() {
        return "Lightbulb{" +
                "color=" + color +
                ", state=" + state +
                '}';
    }

    private void readObject(ObjectInputStream ois) throws ClassNotFoundException, IOException {

        ois.defaultReadObject();

        double red = ois.readDouble();
        double green = ois.readDouble();
        double blue = ois.readDouble();
        double opacity = ois.readDouble();
        String state = ois.readLine();
        color = Color.color(red, green, blue, opacity);
        this.setState(state);

    }

    private void writeObject(ObjectOutputStream oos) throws IOException {

        oos.defaultWriteObject();
        oos.writeDouble(color.getRed());
        oos.writeDouble(color.getGreen());
        oos.writeDouble(color.getBlue());
        oos.writeDouble(color.getOpacity());
        oos.writeBytes(state.getStateString());

    }


}
