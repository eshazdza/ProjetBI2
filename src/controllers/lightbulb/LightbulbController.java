package controllers.lightbulb;

import entities.lightbulb.Lightbulb;
import entities.triggerButton.SwitchButton;
import javafx.fxml.FXML;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

public class LightbulbController {

    @FXML
    private Lightbulb lightbulb;

    @FXML
    private SwitchButton button;

    public void setColorAndFill(Color color) {
        lightbulb.setFill(color);
        lightbulb.setColor(color);
    }

    public void setColor(Color color) {
        lightbulb.setColor(color);
    }

    public void setFill(Paint paint) {
        lightbulb.setFill(paint);
    }

    public void switchBtn() {
        button.switchOnOff();
        lightbulb.performRequest();
    }

    public void setSize(double radius){
        lightbulb.setRadius(radius);
//        lightbulb.setLayoutX(5);
//        lightbulb.setLayoutY(5);
    }

    public void resizeClick(){

    }

}
