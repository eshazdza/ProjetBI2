package controllers;

import entities.lightbulb.Lightbulb;
import javafx.fxml.FXML;
import javafx.scene.paint.Color;

public class LightbulbController {

    @FXML
    private Lightbulb lightbulb = new Lightbulb();

    public void setColor(Color color) {
        System.out.println("set fill : " + color);
        lightbulb.setStyle("-fx-fill: blue");
        System.out.println(lightbulb.getStyle());
    }

}
