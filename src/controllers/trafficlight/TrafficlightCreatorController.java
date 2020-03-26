package controllers.trafficlight;

import entities.trafficlight.TrafficLight;
import entities.triggerButton.SwitchButton;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import tools.ObjectIO;

public class TrafficlightCreatorController {


    @FXML
    private TrafficlightController trafficlightController;

    @FXML
    private TrafficLight trafficlight;

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
        switch (switchButton.getState()) {
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
                if (trafficlightController.runAutoModeFromCreator()) {
                    autoButton.setDisable(true);
                    manualButton.setDisable(false);
                    panicButton.setDisable(false);
                }
                break;
            case "MANUAL":
                if (trafficlightController.runManualMode()) {
                    manualButton.setDisable(true);
                    autoButton.setDisable(false);
                    panicButton.setDisable(false);
                    trafficlightController.enableSwitchPhase();
                }
                break;
            case "PANIC":
                if (trafficlightController.runPanicMode(true)) {
                    panicButton.setDisable(true);
                    autoButton.setDisable(false);
                    manualButton.setDisable(false);
                }
                break;
        }
        trafficlightController.setMode(mode);
    }


    public void saveTrafficLight() {
//        By design the bulbs have to have been saved previously so we don't really need to check their existence in the file system at this point...
        ObjectIO.save(trafficlightController.getTrafficLight());
    }

    public void deleteTrafficLight() {
        System.out.println("delete");
    }

}
