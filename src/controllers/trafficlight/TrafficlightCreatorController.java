package controllers.trafficlight;

import entities.trafficlight.TrafficLight;
import entities.triggerButton.SwitchButton;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

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

    @FXML
    private Button panicButton;


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
                panicButton.setDisable(false);
                break;
            case "OFF":
                trafficlightController.turnOffTrafficLight();
                manualButton.setDisable(true);
                autoButton.setDisable(true);
                panicButton.setDisable(true);
                break;
        }

    }

    public void handleModeButton(Event event) {
        String mode = ((Button) event.getSource()).getText().toUpperCase();
        switch (mode) {
            case "AUTO":
                autoButton.setDisable(true);
                manualButton.setDisable(false);
                panicButton.setDisable(false);
                trafficlightController.runAutoModeFromCreator();
                break;
            case "MANUAL":
                manualButton.setDisable(true);
                autoButton.setDisable(false);
                panicButton.setDisable(false);
                trafficlightController.runManualMode();
                trafficlightController.enableSwitchPhase();
                break;
            case "PANIC":
                panicButton.setDisable(true);
                autoButton.setDisable(false);
                manualButton.setDisable(false);
                trafficlightController.runPanicMode(true);
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
