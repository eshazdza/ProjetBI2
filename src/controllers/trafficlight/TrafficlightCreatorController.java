package controllers.trafficlight;

import controllers.lightbulb.LightbulbController;
import entities.lightbulb.Lightbulb;
import entities.trafficlight.TrafficLight;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
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

    public void initData(TrafficLight trafficLight) {
        trafficlightController.initData(trafficLight);
    }

    public void runTrafficLight() {
        System.out.println("run");
    }

    public void saveTrafficLight() {
        System.out.println("save tl");
    }

    public void deleteTrafficLight() {
        System.out.println("delete");
    }


}
