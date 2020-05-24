package controllers.direction;


import controllers.intersection.IntersectionController;
import controllers.trafficlight.TrafficlightController;
import entities.direction.Direction;
import entities.phase.Phase;
import entities.trafficlight.TrafficLight;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.input.*;
import javafx.scene.layout.HBox;
import javafx.scene.transform.Rotate;
import tools.ObjectIO;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class DirectionController implements Initializable {
    @FXML
    private HBox trafficLightContainer;

    @FXML
    private Direction direction;

    private ArrayList<TrafficlightController> trafficlightControllers = new ArrayList<>();

    private String bulbSize = "small";

    private double x, y;
    private String mode;

    //    IntersectionController : allows us to know if the DirectionController is instantiated from within an IntersectionController and potentially reflect the changes
    private IntersectionController intersectionController = null;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
//
    }

    public void initData(Direction direction) {


//        Clear the containers and lists
        trafficLightContainer.getChildren().clear();
        trafficlightControllers.clear();

//        Initialize panel data
        this.direction.setState(direction.getState());
        this.direction.setBelongsToPhase(direction.isBelongsToPhase());
        this.direction.setHasPriority(direction.isHasPriority());
        this.direction.setName(direction.getName());
        this.direction.setAngle(direction.getAngle());
        this.direction.setLocked(direction.isLocked());

//        Update panel display angle
        if (this.direction.getAngle() != 0) {
            double pivotX = this.direction.getWidth() / 2;
            double pivotY = this.direction.getHeight() / 2;
            Rotate rotate = new Rotate(this.direction.getAngle(), pivotX, pivotY);
            this.direction.getTransforms().add(rotate);

        }

//        Add the traffic light from the direction to the controlled traffic light list and the view
        for (TrafficLight trafficLight :
                direction.getTrafficLights()) {

            addTrafficLight(trafficLight);
        }
    }

    public void initData(Direction direction, IntersectionController intersectionController) {
        this.intersectionController = intersectionController;
        this.initData(direction);
    }

    public void addTrafficLight(TrafficLight trafficLight) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/trafficlights/trafficlight.fxml"));
            final TrafficLight pane = loader.load();

            TrafficlightController trafficlightController = loader.getController();
            trafficlightController.initData(trafficLight, true, "small");

            Rotate rotate = new Rotate(90);
            pane.getTransforms().add(rotate);

            pane.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    if (event.getButton() == MouseButton.SECONDARY) {
                        ContextMenu contextMenu = new ContextMenu();

                        MenuItem deleteItem = new MenuItem("Remove Trafficlight");

                        deleteItem.setOnAction(event1 -> removeTrafficLight(event));

                        contextMenu.getItems().addAll(deleteItem);
                        contextMenu.show(pane, event.getScreenX(), event.getScreenY() + 128);
                    }
                }
            });

            trafficLightContainer.getChildren().add(pane);
            direction.addTrafficLight(trafficLight);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void removeTrafficLight(MouseEvent event) {
        stopDirection();

        if (this.intersectionController != null) {
            this.intersectionController.reflectDirectionRemoveTL((TrafficLight) event.getSource(), this.direction);

            if (this.direction.isBelongsToPhase()) {
                Phase phase = this.intersectionController.findPhase(this.direction);
                for (Direction d :
                        phase.getDirections()) {
                    System.out.println(d);
                }
                this.intersectionController.removeTlFromPhase(phase, (TrafficLight) event.getSource());
            }
        }

        trafficLightContainer.getChildren().remove(event.getSource());
        direction.removeTrafficLight((TrafficLight) event.getSource());

    }

    public void removeTrafficLight(TrafficLight trafficLight) {
        stopDirection();
        trafficLightContainer.getChildren().remove(trafficLight);
        direction.removeTrafficLight(trafficLight);
    }

    /**
     * onDirectionPressed
     *
     * @param event MouseEvent
     */
    public void onDirectionPressed(MouseEvent event) {
        x = event.getSceneX();
        y = event.getSceneY();
    }

    /**
     * onDirectionDragDetected
     *
     * @param event MouseEvent
     */
    public void onDirectionDragDetected(MouseEvent event) {

    }

    /**
     * onDirectionDragged
     * Reposition the direction pane in the container
     *
     * @param event MouseEvent
     */
    public void onDirectionDragged(MouseEvent event) {

        if (!direction.isLocked()) {
//        Check for left click
            if (event.isPrimaryButtonDown()) {
//               Update the position of the direction
                double deltaX = event.getSceneX() - x;
                double deltaY = event.getSceneY() - y;

                direction.setLayoutX(direction.getLayoutX() + deltaX);
                direction.setLayoutY(direction.getLayoutY() + deltaY);

                x = event.getSceneX();
                y = event.getSceneY();

                if (intersectionController != null) {
                    this.intersectionController.reflectDirectionPos(direction, direction.getLayoutX(), direction.getLayoutY());
                }

                direction.setPosX(direction.getLayoutX());
                direction.setPosY(direction.getLayoutY());


            } else if (event.isSecondaryButtonDown()) {
//            Rotate the direction
                double pivotX = direction.getWidth() / 2;
                double pivotY = direction.getHeight() / 2;
                double angle = 45;
                Rotate rotate = new Rotate(angle, pivotX, pivotY);
                direction.getTransforms().add(rotate);

                // If the DirectionController is instantiated from within an intersection, the changes made to the direction need to be reflected in the IntersectionController
                if (this.intersectionController != null) {
                    intersectionController.reflectDirectionAngle(direction.getAngle() + 45, direction);
                }

                direction.setAngle(direction.getAngle() + 45);
            }
        }
    }

    /**
     * onDirectionReleased
     *
     * @param event MouseEvent
     */
    public void onDirectionReleased(MouseEvent event) {
    }


    /**
     * setOnDragOver
     *
     * @param event DragEvent
     */
    public void setOnDragOver(DragEvent event) {
        event.acceptTransferModes(TransferMode.ANY);
        event.consume();
    }

    /**
     * handleDragEntered
     *
     * @param event DragEvent
     */
    public void handleDragEntered(DragEvent event) {
        event.consume();
    }

    /**
     * handleDragDrop
     *
     * @param event DragEvent
     */
    public void handleDragDrop(DragEvent event) {
        if (event.getDragboard().hasString()) {
            String fileName = (String) event.getDragboard().getContent(DataFormat.PLAIN_TEXT);
            if (fileName.contains(".trafficlight")) {
                TrafficLight trafficLight = (TrafficLight) ObjectIO.open(fileName);
                for (TrafficLight tl :
                        getTrafficlights()) {
                    tl.performRequest("OFF");
                }

                if (this.intersectionController != null) {
//                    this.intersectionController.reflectDirectionAddTL(trafficLight, this.direction);

                    // If the direction belongs to a phase, we add the traffic light to every direction of the phase
                    if (this.direction.isBelongsToPhase()) {
                        // Find the phase
                        Phase phase = this.intersectionController.findPhase(this.direction);
                        // For each direction of the phase, we add the traffic light and reflect the change in the intersection controller
                        this.intersectionController.addTlToPhase(phase, trafficLight);
                        return;
                    }

                    this.intersectionController.reflectDirectionAddTL(trafficLight, this.direction);

                }

                this.addTrafficLight(trafficLight);
            }
        }
    }

    public void runDirection() {
        if (direction.getStateString().equals("CONTROLLED")) {

            if (direction.getStateString().equals("CONTROLLED")) {
                for (TrafficlightController tl :
                        this.trafficlightControllers) {
                    tl.runTrafficLight(bulbSize);
                }
            }
        }
    }

    public void stopDirection() {
        if (direction.getStateString().equals("CONTROLLED")) {
            for (TrafficlightController tl :
                    this.trafficlightControllers) {
                tl.turnOffTrafficLight(bulbSize);
            }
        }
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public boolean runAutoModeFromCreator() {
        for (TrafficlightController tl :
                this.trafficlightControllers) {
            if (!tl.runAutoModeFromCreator(bulbSize)) {
                return false;
            }
        }
        return true;
    }


    public boolean runManualMode() {
        for (TrafficlightController tl :
                this.trafficlightControllers) {
            if (!tl.runManualMode(bulbSize)) {
                return false;
            }
        }
        return true;
    }

    public boolean runPanicMode(boolean fromCreator) {
        for (TrafficlightController tl :
                this.trafficlightControllers) {
            if (!tl.runPanicMode(fromCreator, bulbSize)) {
                return false;
            }
        }
        return true;
    }

    public boolean switchPhase() {
        for (TrafficlightController tl :
                this.trafficlightControllers) {
            tl.switchPhase(bulbSize);
        }
        return true;
    }

    public ArrayList<TrafficLight> getTrafficlights() {
        return direction.getTrafficLights();
    }

    public boolean hasTrafficlight() {
        return direction.hasTrafficLight();
    }

    public Direction getDirection() {
        return direction;
    }

    public void handleLock() {
        if (intersectionController != null) {
            intersectionController.reflectDirectionLock(!direction.isLocked(), direction);
        }

        direction.setLocked(!direction.isLocked());
    }

    public void toFront() {
        direction.toFront();
    }

    public void setBelongsToPhase(boolean belongs) {
        direction.setBelongsToPhase(belongs);
    }

    public boolean getBelongsToPhase() {
        return direction.isBelongsToPhase();
    }
}
