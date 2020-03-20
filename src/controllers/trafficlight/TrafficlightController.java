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
import tools.ObjectIO;

import java.util.Collections;

public class TrafficlightController {

    @FXML
    private VBox bulbContainer;

    @FXML
    private Button switchPhaseButton;

    private TrafficLight trafficLight;

    private String mode = "MANUAL";


    public void initData(TrafficLight trafficLight) {
        bulbContainer.getChildren().clear();
        this.trafficLight = trafficLight;
//        We add each lightbulb of the traffic light to the view
        for (Lightbulb l :
                trafficLight.getLightbulbs()) {
            try {

                FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/lightbulbs/lightbulb.fxml"));
                final Pane pane = loader.load();

                LightbulbController bulbController = loader.getController();
                bulbController.setColor(l.getColor());

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
                bulbContainer.getChildren().add(pane);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    public void removeBulb(TrafficLight trafficLight, Lightbulb lightbulb) {
        trafficLight.getLightbulbs().remove(lightbulb);
        this.initData(trafficLight);
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
            this.initData(trafficLight);
        }
    }

    public void handleDragDetected() {
    }


    public void moveUpBulb(TrafficLight trafficLight, Lightbulb lightbulb) {
        int index = trafficLight.getLightbulbs().indexOf(lightbulb);
        if (index > 0) {
            Collections.swap(trafficLight.getLightbulbs(), index, index - 1);
            this.initData(trafficLight);
        }
    }

    public void moveDownBulb(TrafficLight trafficLight, Lightbulb lightbulb) {
        int index = trafficLight.getLightbulbs().indexOf(lightbulb);
        if (index < (trafficLight.getLightbulbs().size() - 1)) {
            Collections.swap(trafficLight.getLightbulbs(), index, index + 1);
            this.initData(trafficLight);
        }
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public void runTrafficLight() {
        this.trafficLight.performRequest("STANDBY");
    }

    public void turnOffTrafficLight() {
        this.trafficLight.performRequest("OFF");
    }

    public void runAutoMode() {
        this.trafficLight.performRequest("AUTO");
        if (this.trafficLight.performRequest("GET").equals("AUTO")){
            switchPhaseButton.setDisable(true);
        }
    }

    public void runManualMode() {
        this.trafficLight.performRequest("MANUAL");
    }

    public void enableSwitchPhase(){
        System.out.println("zndopqmd");
        System.out.println(this.trafficLight.performRequest("GET"));
        if (this.trafficLight.performRequest("GET").equals("MANUAL")){
            switchPhaseButton.setDisable(false);
        }
    }

    public void switchPhase() {
        System.out.println("switching phase");
    }


}
