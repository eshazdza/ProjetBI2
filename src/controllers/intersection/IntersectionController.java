package controllers.intersection;

import controllers.direction.DirectionController;
import entities.direction.Direction;
import entities.direction.DirectionState;
import entities.intersection.Intersection;
import entities.intersection.IntersectionState;
import entities.phase.Phase;
import entities.trafficlight.TrafficLight;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

import java.util.ArrayList;

public class IntersectionController {

    @FXML
    private Intersection intersection;

    @FXML
    private Pane directionsContainer;

    private ArrayList<Phase> phases = new ArrayList<>();

    private int phasesCpt = 0;

    public void initData(Intersection intersection) {
        for (Direction d :
                intersection.getDirections()) {
            addDirection(d, 0, 0);
        }
    }

    public void addDirection(Direction direction, double x, double y) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/directions/direction.fxml"));
            final Direction pane = loader.load();
            DirectionController directionController = loader.getController();
            directionController.initData(direction, this);

            pane.setOnMouseClicked(event -> handleMouseClick(event, directionController, pane));

            directionsContainer.getChildren().add(pane);

            // Set position of the direction in the scene
            // If the direction has a set position
            if (direction.getPosX() != 0 || direction.getPosY() != 0) {
                pane.setLayoutX(direction.getPosX());
                pane.setLayoutY(direction.getPosY());
            } else {
                // If the direction has no set position : position it on mouse location
                pane.setLayoutX(x - intersection.getBoundsInParent().getMinX());
                pane.setLayoutY(y);
            }
            intersection.addDirection(direction);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void handleMouseClick(MouseEvent event, DirectionController directionController, Pane pane) {
        // Context Menu
        if (event.getButton() == MouseButton.SECONDARY) {
            ContextMenu contextMenu = new ContextMenu();

            // Lock Direction in place
            MenuItem lockItem = new MenuItem();
            if (directionController.getDirection().isLocked()) {
                lockItem.setText("Unlock");
            } else {
                lockItem.setText("Lock");
            }

            lockItem.setOnAction(event1 -> lockItem(directionController));

            // Add the direction to a phase
            Menu phaseMenu = new Menu("Add to phase");

            // New Phase
            MenuItem newPhaseItem = new MenuItem("New phase");
            newPhaseItem.setOnAction(event1 -> createNewPhase(directionController));
            phaseMenu.getItems().add(newPhaseItem);

            // Existing phases
            int i = 0;
            for (Phase p :
                    this.phases) {
                MenuItem existingPhaseItem = new MenuItem("Phase" + i);
                existingPhaseItem.setOnAction(event1 -> addDirToPhase(directionController, event1));
                phaseMenu.getItems().add(existingPhaseItem);
                i++;
            }

            // Remove from phase
            if (directionController.getBelongsToPhase()) {
                phaseMenu.disableProperty().setValue(true);

                MenuItem removePhaseItem = new MenuItem("Remove from phase");
                removePhaseItem.setOnAction(event1 -> removeFromPhase(directionController));
                contextMenu.getItems().add(removePhaseItem);
            }

            // Move direction to front
            MenuItem toFrontItem = new MenuItem("To Front");
            toFrontItem.setOnAction(event1 -> toFront(directionController));

            // Remove direction
            MenuItem removeDirectionItem = new MenuItem("Remove");
            removeDirectionItem.setOnAction(event1 -> removeDirection(event, directionController));

            contextMenu.getItems().addAll(lockItem, phaseMenu, toFrontItem, removeDirectionItem);
            contextMenu.show(pane, event.getScreenX(), event.getScreenY() + 160);
        }
    }

    private void removeFromPhase(DirectionController directionController) {
        for (Phase phase :
                phases) {
            if (phase.getDirections().indexOf(directionController.getDirection()) != -1) {
                phase.getDirections().remove(directionController.getDirection());
                phase.getDirectionControllers().remove(directionController);
                this.intersection.getDirections().get(this.intersection.getDirections().indexOf(directionController.getDirection())).setBelongsToPhase(false);
                directionController.setBelongsToPhase(false);
                break;
            }
        }
    }


    private void createNewPhase(DirectionController directionController) {
        Phase phase = new Phase();
        phase.setName("Phase" + phasesCpt);
        phasesCpt++;
        phases.add(phase);
        addDirToPhase(directionController, phase.getName());
    }

    private void addDirToPhase(DirectionController directionController, ActionEvent event) {
        addDirToPhase(directionController, ((MenuItem) event.getSource()).getText());
    }

    private void addDirToPhase(DirectionController directionController, String name) {
        for (Phase phase :
                phases) {
            if (phase.getName().equals(name)) {
                this.intersection.getDirections().get(this.intersection.getDirections().indexOf(directionController.getDirection())).setBelongsToPhase(true);
                directionController.setBelongsToPhase(true);
                phase.addDirection(directionController.getDirection());
                phase.addDirectionController(directionController);
                break;
            }
        }
        this.updateControlledState();
    }


    private void removeDirection(MouseEvent event, DirectionController directionController) {
        directionsContainer.getChildren().remove(event.getSource());
        intersection.removeDirection((Direction) event.getSource());
        this.updateControlledState();
    }

    public void reflectDirectionAngle(double angle, Direction direction) {
        this.intersection.getDirections().get(this.intersection.getDirections().indexOf(direction)).setAngle(angle);

    }

    public void reflectDirectionAddTL(TrafficLight trafficLight, Direction direction) {
        this.intersection.getDirections().get(this.intersection.getDirections().indexOf(direction)).addTrafficLight(trafficLight);
        this.updateControlledState();
    }

    public void reflectDirectionRemoveTL(TrafficLight trafficLight, Direction direction) {
        this.intersection.getDirections().get(this.intersection.getDirections().indexOf(direction)).removeTrafficLight(trafficLight);
        this.updateControlledState();
    }

    public void reflectDirectionLock(boolean isLocked, Direction direction) {
        this.intersection.getDirections().get(this.intersection.getDirections().indexOf(direction)).setLocked(isLocked);
    }

    public void reflectDirectionPos(Direction direction, double layoutX, double layoutY) {
        this.intersection.getDirections().get(this.intersection.getDirections().indexOf(direction)).setPosX(layoutX);
        this.intersection.getDirections().get(this.intersection.getDirections().indexOf(direction)).setPosY(layoutY);
    }


    private void toFront(DirectionController directionController) {
        directionController.toFront();
    }

    private void lockItem(DirectionController directionController) {
        directionController.handleLock();
    }

    public Pane getContainer() {
        return this.directionsContainer;
    }

    public Intersection getIntersection() {
        return intersection;
    }

    public Phase findPhase(Direction direction) {
        Phase phase = null;
        for (Phase p :
                this.phases) {
            if (p.getDirections().indexOf(direction) != -1) {
                phase = p;
            }
        }
        return phase;
    }

    public void addTlToPhase(Phase phase, TrafficLight trafficLight) {
        for (DirectionController dc :
                phase.getDirectionControllers()) {

            this.intersection.getDirections().get(this.intersection.getDirections().indexOf(dc.getDirection())).addTrafficLight(trafficLight);
            dc.addTrafficLight(trafficLight);
        }

        this.updateControlledState();
    }

    public void removeTlFromPhase(Phase phase, TrafficLight trafficLight) {
        for (DirectionController dc :
                phase.getDirectionControllers()) {
            dc.removeTrafficLight(trafficLight);
        }
        this.updateControlledState();
    }

    public boolean checkControlledIntegrity() {

        for (Direction d :
                this.intersection.getDirections()) {
            if (d.getState() == DirectionState.UNCONTROLLED) {
                return false;
            }
        }

        return true;
    }

    public void updateControlledState() {
        if (this.checkControlledIntegrity()) {
            this.intersection.setState(IntersectionState.CONTROLLED);
        } else {
            this.intersection.setState(IntersectionState.YOLO);
        }
    }
}
