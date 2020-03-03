package controller.lightbulb;

import java.awt.*;

/**
 * controller.lightbulb.Lightbulb
 * We use state pattern for the controller.lightbulb since, while currently trivial, behavior could change in the future (ie. a smarter LightBulb could blink, change color, etc...)
 */
public class Lightbulb{

    private Color color;
    private LightbulbState state;

    /* *******************   CONSTRUCTORS         ******************* */

    /**
     * controller.lightbulb.Lightbulb color defaults to Orange if no color is defined.
     * Will always be off whenever we first "plug it in"
     */
    public Lightbulb() {
        this.color = Color.ORANGE;
        this.state = LightbulbState.OFF;
    }

    /**
     * @param color Color
     */
    public Lightbulb(Color color) {
        this.color = color;
        this.state = LightbulbState.OFF;
    }

    /* *******************   END   CONSTRUCTORS      ******************* */

  public void performRequest(){
      this.state = this.state.switchLight();
      System.out.println(this.getColor());
  }

    public Color getColor() {
        return color;
    }


}
