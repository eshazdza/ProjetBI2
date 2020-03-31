package controllers.direction;


import controllers.trafficlight.TrafficlightController;
import entities.direction.Direction;
import entities.direction.DirectionException;
import entities.lightbulb.Lightbulb;
import entities.trafficlight.TrafficLight;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.input.DataFormat;
import javafx.scene.input.DragEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import tools.ObjectIO;

import javax.xml.crypto.Data;
import java.io.IOException;
import java.util.ArrayList;

public class DirectionController {

    @FXML
    private Direction direction;

    @FXML
    private StackPane trafficlightContainer;

    private TrafficlightController trafficlightController;

    private String mode = "MANUAL";

    public void initData() {
    }

    public void initData(Direction direction) {
        trafficlightContainer.getChildren().clear();
        this.direction = direction;


        for (TrafficLight trafficLight :
                direction.getTrafficLights()) {
            try {

                FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/trafficlights/trafficlight.fxml"));
                final Pane pane = loader.load();

                trafficlightController = loader.getController();
                pane.setMaxSize(50, 100);

                trafficlightController.initData(trafficLight, false, "small");

                trafficlightContainer.getChildren().add(pane);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


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
        System.out.println("drag dropped");
        if (event.getDragboard().hasString()) {
            String fileName = (String) event.getDragboard().getContent(DataFormat.PLAIN_TEXT);
            if (fileName.contains(".trafficlight")) {
                TrafficLight trafficLight = (TrafficLight) ObjectIO.open(fileName);
                trafficLight.performRequest("FULLON");
                System.out.println(direction);
                try {
                    this.direction.addTrafficLight(trafficLight);
                    this.initData(direction);
                } catch (DirectionException e) {
                    e.getStackTrace();
                }
            }
        }
    }


    public void runDirection() {
        if (direction.getStateString().equals("CONTROLLED")) {
            trafficlightController.runTrafficLight();
        }
    }

    public void stopDirection() {
        if (direction.getStateString().equals("CONTROLLED")) {
            trafficlightController.turnOffTrafficLight();
        }
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public boolean runAutoModeFromCreator() {
        if (trafficlightController.runAutoModeFromCreator()) {
            return true;
        }
        return false;
    }

    public boolean runManualMode() {
        if (trafficlightController.runManualMode()) {
            return true;
        }
        return false;
    }

    public boolean runPanicMode(boolean fromCreator) {
        if (trafficlightController.runPanicMode(true)) {
            return true;
        }
        return false;
    }

    public void switchPhase() {
        trafficlightController.switchPhase();
    }

    public ArrayList<TrafficLight> getTrafficlights() {
        return direction.getTrafficLights();
    }

    public boolean hasTrafficlight() {
        return direction.hasTrafficLight();
    }

}
