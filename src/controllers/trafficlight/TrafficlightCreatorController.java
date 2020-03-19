package controllers.trafficlight;

import controllers.lightbulb.LightbulbController;
import entities.lightbulb.Lightbulb;
import entities.trafficlight.TrafficLight;
import entities.triggerButton.SwitchButton;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Orientation;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class TrafficlightCreatorController {


    @FXML
    private TrafficlightController trafficlightController;

    @FXML
    private StackPane trafficlight;

    @FXML
    private Pane rootPane;

    @FXML
    private SwitchButton switchButton;


    public void initData(TrafficLight trafficLight) {
        trafficlightController.initData(trafficLight);
        System.out.println(switchButton.getText());
    }

    public void handleSwitchButton(){
        switchButton.switchOnOff();
    }

    public void saveTrafficLight() {
        System.out.println("save tl");
    }

    public void deleteTrafficLight() {
        System.out.println("delete");
    }


}
