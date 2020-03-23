package controllers.trafficlight;

import controllers.lightbulb.LightbulbController;
import entities.lightbulb.Lightbulb;
import entities.trafficlight.TrafficLight;
import entities.triggerButton.SwitchButton;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Orientation;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import javax.annotation.processing.Generated;

public class TrafficlightCreatorController {


    @FXML
    private TrafficlightController trafficlightController;

    @FXML
    private StackPane trafficlight;

    @FXML
    private Pane rootPane;

    @FXML
    private SwitchButton switchButton;

    @FXML
    private Button manualButton;

    @FXML
    private Button autoButton;


    public void initData(TrafficLight trafficLight) {
        trafficlightController.initData(trafficLight, true);
    }

    public void handleSwitchButton() {
        switchButton.switchOnOff();
        switch (switchButton.getState()){
            case "ON":
                trafficlightController.runTrafficLight();
                manualButton.setDisable(false);
                autoButton.setDisable(false);
                break;
            case "OFF":
                trafficlightController.turnOffTrafficLight();
                manualButton.setDisable(true);
                autoButton.setDisable(true);
                break;
        }

    }

    public void handleModeButton(Event event) {
        String mode = ((Button) event.getSource()).getText().toUpperCase();
        switch (mode) {
            case "AUTO":
                autoButton.setDisable(true);
                manualButton.setDisable(false);
                trafficlightController.runAutoMode();
                break;
            case "MANUAL":
                manualButton.setDisable(true);
                autoButton.setDisable(false);
                trafficlightController.runManualMode();
                trafficlightController.enableSwitchPhase();
                break;
        }
        trafficlightController.setMode(mode);
    }


    public void saveTrafficLight() {
        System.out.println("save tl");
    }

    public void deleteTrafficLight() {
        System.out.println("delete");
    }

}
