package controllers.trafficlight;

import controllers.lightbulb.LightbulbController;
import entities.lightbulb.Lightbulb;
import entities.trafficlight.TrafficLight;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.input.*;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import tools.ObjectIO;

import java.awt.*;
import java.util.Collections;

public class TrafficlightController {

    @FXML
    private VBox bulbContainer;

    @FXML
    private Button switchPhaseButton;

    private TrafficLight trafficLight;

    private String mode = "MANUAL";

    private LightbulbController bulbController;

    public void initData(TrafficLight trafficLight, boolean editMode) {

        bulbContainer.getChildren().clear();
        this.trafficLight = trafficLight;

//        If we are in edit mode (window is requested from TrafficLightCreatorController
//        We turn all the lights on if the traffic light is off
        if (editMode && trafficLight.getStateString().equals("OFF")) {
            trafficLight.performRequest("FULLON");
        }

//        We add each lightbulb of the traffic light to the view
        for (Lightbulb l :
                trafficLight.getLightbulbs()) {
            try {
//                Load FXML view and related controller
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/lightbulbs/lightbulb.fxml"));
                final Pane pane = loader.load();

                LightbulbController bulbController = loader.getController();

//                Set the color of the bulbs
                bulbController.setColor(l.getColor());
                bulbController.setFill(l.getFill());

//                Context Menu on each bulb
                pane.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        if (event.getButton() == MouseButton.SECONDARY) {
                            ContextMenu contextMenu = new ContextMenu();

                            MenuItem moveUpItem = new MenuItem("Move Up");
                            MenuItem moveDownItem = new MenuItem("Move Down");
                            MenuItem deleteItem = new MenuItem("Remove");

                            moveUpItem.setOnAction(event1 -> moveUpBulb(trafficLight, l));
                            moveDownItem.setOnAction(event1 -> moveDownBulb(trafficLight, l));
                            deleteItem.setOnAction(event1 -> removeBulb(trafficLight, l));

                            contextMenu.getItems().addAll(moveUpItem, moveDownItem, deleteItem);
                            contextMenu.show(pane, event.getScreenX(), event.getScreenY());

                        }
                    }
                });

//                Add the bulbs to the view
                bulbContainer.getChildren().add(pane);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void removeBulb(TrafficLight trafficLight, Lightbulb lightbulb) {
        trafficLight.getLightbulbs().remove(lightbulb);
        this.initData(trafficLight, true);
    }

    public void setOnDragOver(DragEvent event) {
        event.acceptTransferModes(TransferMode.ANY);
        event.consume();
    }

    public void handleDragEntered(DragEvent event) {
        event.consume();
    }

    public void handleDragDrop(DragEvent event) {
        if (event.getDragboard().hasString()) {
            String fileName = (String) event.getDragboard().getContent(DataFormat.PLAIN_TEXT);
            Lightbulb lightbulb = ObjectIO.open(fileName);
            this.trafficLight.addLightBulb(lightbulb);
            this.initData(trafficLight, true);
        }
    }

    public void handleDragDetected() {
    }


    public void moveUpBulb(TrafficLight trafficLight, Lightbulb lightbulb) {
        int index = trafficLight.getLightbulbs().indexOf(lightbulb);
        if (index > 0) {
            Collections.swap(trafficLight.getLightbulbs(), index, index - 1);
            this.initData(trafficLight, true);
        }
    }

    public void moveDownBulb(TrafficLight trafficLight, Lightbulb lightbulb) {
        int index = trafficLight.getLightbulbs().indexOf(lightbulb);
        if (index < (trafficLight.getLightbulbs().size() - 1)) {
            Collections.swap(trafficLight.getLightbulbs(), index, index + 1);
            this.initData(trafficLight, true);
        }
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public void runTrafficLight() {
        this.trafficLight.performRequest("STANDBY");
        this.initData(trafficLight, false);
    }

    public void turnOffTrafficLight() {
        switchPhaseButton.setDisable(true);
        this.trafficLight.performRequest("OFF");
        this.initData(trafficLight, true);
    }

    public void runAutoMode() {
        this.trafficLight.performRequest("AUTO");
        if (this.trafficLight.performRequest("GET").equals("AUTO")) {
            switchPhaseButton.setDisable(true);
        }
    }

    public void runManualMode() {
        this.trafficLight.performRequest("MANUAL");
    }

    public void enableSwitchPhase() {
        if (this.trafficLight.performRequest("GET").equals("MANUAL")) {
            switchPhaseButton.setDisable(false);
        }
    }

    public void switchPhase() {
        getBulbs();
    }

    public void getBulbs() {
        for (Lightbulb l :
                trafficLight.getLightbulbs()) {
            System.out.println(l);
        }


    }


}
