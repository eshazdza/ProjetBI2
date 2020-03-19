package controllers.lightbulb;

import entities.lightbulb.Lightbulb;
import javafx.fxml.FXML;
import javafx.scene.paint.Color;

public class LightbulbController {

    @FXML
    private Lightbulb lightbulb;

    public void setColor(Color color) {
        System.out.println("set fill : " + color);
        lightbulb.setFill(color);
        System.out.println(lightbulb.getStyle());
    }

}
