package controllers.lightbulb;

import entities.lightbulb.Lightbulb;
import entities.triggerButton.SwitchButton;
import javafx.fxml.FXML;
import javafx.scene.paint.Color;

public class LightbulbController {

    @FXML
    private Lightbulb lightbulb;

    @FXML
    private SwitchButton button;

    public void setColor(Color color) {
        System.out.println("set fill : " + color);
        lightbulb.setFill(color);
        System.out.println(lightbulb.getStyle());
    }

    public void switchBtn(){
        button.switchOnOff();
    }

}
