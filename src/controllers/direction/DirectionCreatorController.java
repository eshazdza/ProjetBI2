package controllers.direction;

import entities.direction.Direction;
import entities.triggerButton.SwitchButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import tools.ObjectIO;

import java.net.URL;
import java.util.ResourceBundle;


public class DirectionCreatorController implements Initializable {
    @FXML
    private TextField tlName;

    @FXML
    private HBox creatorContainer;

    @FXML
    private Pane rootPane;

    @FXML
    private Pane drawingBoard;

    @FXML
    private SwitchButton switchButton;

    @FXML
    private Button manualButton;

    @FXML
    private Button autoButton;

    @FXML
    private Button panicButton;

    @FXML
    private Button switchPhaseButton;

    private Direction direction = null;

    private DirectionController directionController;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initData();
    }

    public void initData() {
        tlName.focusedProperty().addListener(((observable, oldValue, newValue) -> {
            if (!newValue) {
                if (direction != null) {
                    direction.setName(tlName.getText());
                }
            }
        }));
    }

    public void initData(Direction direction) {
        this.initData();
        tlName.setText(direction.getName());

        createAndAddDirection(null);
        directionController.initData(direction);
    }

    public void handleSwitchButton(ActionEvent actionEvent) {
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
                    switchPhaseButton.setDisable(true);
                    break;
            }
        }

    }

    public void handleModeButton(ActionEvent actionEvent) {
        String mode = ((Button) actionEvent.getSource()).getText().toUpperCase();
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

    public void switchPhase(ActionEvent actionEvent) {
        directionController.switchPhase();
    }

    public void saveDirection(ActionEvent actionEvent) {
        if (this.directionController.getDirection().getName() == null || this.directionController.getDirection().getName().isBlank()) {
            System.out.println("must name direction");
        } else {
            ObjectIO.save(this.directionController.getDirection());
        }
    }

    public void deleteDirection(ActionEvent actionEvent) {
        ObjectIO.delete(directionController.getDirection());
    }

    public void handleMouseClicked(MouseEvent event) {
//        We can only create and edit a single direction at a time in the creator.
        if (direction == null) {
            if (event.getButton().equals(MouseButton.PRIMARY)) {
//        Create and add a Direction to the drawing board.
                createAndAddDirection(event);

                tlName.focusedProperty().addListener(((observable, oldValue, newValue) -> {
                    if (!newValue) {
                        direction.setName(tlName.getText());
                    }
                }));
            }
        }
    }

    public void createAndAddDirection(MouseEvent event) {

        double posX;
        double posY;

        if (event == null) {
            posX = 100;
            posY = 100;
        } else {
            posX = event.getX();
            posY = event.getY();
        }
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/directions/direction.fxml"));
            direction = loader.load();

            directionController = loader.getController();

            direction.setLayoutX(posX);
            direction.setLayoutY(posY);

            drawingBoard.getChildren().add(direction);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
