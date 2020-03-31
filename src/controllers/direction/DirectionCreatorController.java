package controllers.direction;

import controllers.trafficlight.TrafficlightController;
import entities.direction.Direction;
import entities.triggerButton.SwitchButton;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.DragEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;

public class DirectionCreatorController {

    @FXML
    private DirectionController directionController;

    @FXML
    private Direction direction;

    @FXML
    private AnchorPane rootPane;

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


    public void initData() {
        System.out.println("hello from direction creator COntrolleer");
    }


    public void handleSwitchButton() {
        if (directionController.hasTrafficlight()) {
            switchButton.switchOnOff();
            switch (switchButton.getState()) {
                case "ON":
                    directionController.runDirection();
                    manualButton.setDisable(false);
                    autoButton.setDisable(false);
                    panicButton.setDisable(false);
                    break;
                case "OFF":
                    directionController.stopDirection();
                    manualButton.setDisable(true);
                    autoButton.setDisable(true);
                    panicButton.setDisable(true);
                    break;
            }
        }else{
            System.out.println("had trafficlight");
        }
    }

    public void handleModeButton(Event event) {
        String mode = ((Button) event.getSource()).getText().toUpperCase();
        switch (mode) {
            case "AUTO":
                if (directionController.runAutoModeFromCreator()) {
                    autoButton.setDisable(true);
                    manualButton.setDisable(false);
                    switchPhaseButton.setDisable(true);
                    panicButton.setDisable(false);
                }
                break;
            case "MANUAL":
                if (directionController.runManualMode()) {
                    manualButton.setDisable(true);
                    switchPhaseButton.setDisable(false);
                    autoButton.setDisable(false);
                    panicButton.setDisable(false);
                }
                break;
            case "PANIC":
                if (directionController.runPanicMode(true)) {
                    panicButton.setDisable(true);
                    autoButton.setDisable(false);
                    manualButton.setDisable(false);
                    switchPhaseButton.setDisable(true);

                }
                break;
        }
        directionController.setMode(mode);
    }

    public void switchPhase(){
        directionController.switchPhase();
    }


    public void saveDirection() {

    }

    public void deleteDirection() {

    }

}
