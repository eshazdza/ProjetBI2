package controllers.intersection;

import entities.direction.Direction;
import entities.intersection.Intersection;
import entities.intersection.IntersectionState;
import entities.triggerButton.SwitchButton;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.DataFormat;
import javafx.scene.input.DragEvent;
import javafx.scene.input.TransferMode;
import tools.ObjectIO;

public class IntersectionCreatorController {

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

    @FXML
    private Intersection intersection;

    @FXML
    private IntersectionController intersectionController = new IntersectionController();


    public void initData() {

        tlName.focusedProperty().addListener(((observable, oldValue, newValue) -> {
            if (!newValue) {
                intersection.setName(tlName.getText());
            }
        }));
    }

    public void initData(Intersection intersection) {
        tlName.setText(intersection.getName());
        this.initData();
        intersectionController.initData(intersection);
    }

    /* *******************   DRAG AND DROP         ******************* */

    /**
     * @param event DragEvent
     */
    public void setOnDragOver(DragEvent event) {
        event.acceptTransferModes(TransferMode.ANY);
        event.consume();
    }

    /**
     * @param event DragEvent
     */
    public void handleDragEntered(DragEvent event) {
        event.consume();
    }

    /**
     * @param event DragEvent
     */
    public void handleDragDrop(DragEvent event) {
        if (event.getDragboard().hasString()) {
            String fileName = (String) event.getDragboard().getContent(DataFormat.PLAIN_TEXT);
            if (fileName.contains(".direction")) {
                Direction direction = (Direction) ObjectIO.open(fileName);
                this.intersectionController.addDirection(direction, event.getX(), event.getY());
            }
        }
    }

    /* *******************   END   DRAG AND DROP      ******************* */


    public void handleSwitchButton() {
        System.out.println(intersection.getState());
    }

    public void handleModeButton() {

    }

    public void switchPhase() {

    }

    public void saveIntersection() {
        if (this.intersectionController.getIntersection().getName() == null || this.intersectionController.getIntersection().getName().isBlank()) {
            System.out.println("must name direction");
        } else {
            ObjectIO.save(this.intersectionController.getIntersection());
        }
    }

    public void deleteIntersection() {

    }
}
