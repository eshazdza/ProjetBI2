package controllers.trafficlight;

import entities.trafficlight.TrafficLight;
import entities.triggerButton.SwitchButton;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import tools.ObjectIO;

import java.net.URL;
import java.util.ResourceBundle;

public class TrafficlightCreatorController implements Initializable {


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
    private Button switchPhaseButton;

    @FXML
    private Button autoButton;

    @FXML
    private Button panicButton;


    @FXML
    private TextField tlName;

    private String bulbSize = "large";

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initData();
    }


    public void initData() {
        tlName.focusedProperty().addListener(((observable, oldValue, newValue) -> {
            if (!newValue) {
                if (trafficlight != null) {
                    trafficlight.setName(tlName.getText());
                }
            }
        }));
    }

    public void initData(TrafficLight trafficLight) {
        this.initData();
        tlName.setText(trafficLight.getName());

        trafficlightController.initData(trafficLight, true, "large");
    }

    public void handleSwitchButton() {
        switchButton.switchOnOff();
        switch (switchButton.getState()) {
            case "ON":
                trafficlightController.runTrafficLight(bulbSize);
                manualButton.setDisable(false);
                autoButton.setDisable(false);
                panicButton.setDisable(false);
                break;
            case "OFF":
                trafficlightController.turnOffTrafficLight(bulbSize);
                manualButton.setDisable(true);
                autoButton.setDisable(true);
                panicButton.setDisable(true);
                switchPhaseButton.setDisable(true);

                break;
        }

    }

    public void handleModeButton(Event event) {
        String mode = ((Button) event.getSource()).getText().toUpperCase();
        switch (mode) {
            case "AUTO":
                if (trafficlightController.runAutoModeFromCreator(bulbSize)) {
                    autoButton.setDisable(true);
                    manualButton.setDisable(false);
                    switchPhaseButton.setDisable(true);
                    panicButton.setDisable(false);
                }
                break;
            case "MANUAL":
                if (trafficlightController.runManualMode(bulbSize)) {
                    manualButton.setDisable(true);
                    switchPhaseButton.setDisable(false);
                    autoButton.setDisable(false);
                    panicButton.setDisable(false);
                }
                break;
            case "PANIC":
                if (trafficlightController.runPanicMode(true, bulbSize)) {
                    panicButton.setDisable(true);
                    autoButton.setDisable(false);
                    manualButton.setDisable(false);
                    switchPhaseButton.setDisable(true);
                }
                break;
        }
        trafficlightController.setMode(mode);
    }

    public void switchPhase() {
        trafficlightController.switchPhase(bulbSize);
    }

    public void saveTrafficLight() {
//        By design the bulbs have to have been saved previously so we don't really need to check their existence in the file system at this point...
        if (trafficlightController.getTrafficLight().getName() == null || trafficlightController.getTrafficLight().getName().isBlank()) {
            System.out.println("you must naaaame meeee");
        } else {
            ObjectIO.save(trafficlightController.getTrafficLight());
        }
    }


    public void deleteTrafficLight() {
        ObjectIO.delete(trafficlightController.getTrafficLight());
    }

}
