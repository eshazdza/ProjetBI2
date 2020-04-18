package controllers.direction;


import controllers.trafficlight.TrafficlightController;
import entities.direction.Direction;
import entities.direction.DirectionException;
import entities.trafficlight.TrafficLight;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.input.DataFormat;
import javafx.scene.input.DragEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import tools.ObjectIO;

import java.io.IOException;
import java.util.ArrayList;

public class DirectionController {

    @FXML
    private Direction direction;

    @FXML
    private VBox trafficlightContainer;

    @FXML
    private Rectangle road;

    private ArrayList<TrafficlightController> trafficlightControllers =new ArrayList<>();

    private String mode = "MANUAL";

    private String bulbSize = "small";

    public void initData() {
    }

    public void initData(Direction direction) {
        trafficlightContainer.getChildren().clear();
        trafficlightControllers.clear();
        this.direction = direction;

        for (TrafficLight trafficLight :
                direction.getTrafficLights()) {
            try {

                FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/trafficlights/trafficlight.fxml"));
                final Pane pane = loader.load();

                TrafficlightController trafficlightController = loader.getController();
                trafficlightController.initData(trafficLight, true, bulbSize);
                trafficlightControllers.add(trafficlightController);
                int i = 1;
                for (TrafficlightController tl :
                        trafficlightControllers) {

                    System.out.println(i);
                    i++;
                }

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
                for (TrafficLight tl:
                     getTrafficlights()) {
                    tl.performRequest("OFF");
                }
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
            if (!tl.runPanicMode(true, bulbSize)) {
                return false;
            }
        }
        return true;
    }

    public boolean switchPhase() {
        for (TrafficlightController tl :
                this.trafficlightControllers) {
            tl.switchPhase(bulbSize);
            System.out.println("switch phase");
        }
        return true;
    }

    public ArrayList<TrafficLight> getTrafficlights() {
        return direction.getTrafficLights();
    }

    public boolean hasTrafficlight() {
        return direction.hasTrafficLight();
    }

}
