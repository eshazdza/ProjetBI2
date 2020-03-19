package controllers.trafficlight;

import controllers.lightbulb.LightbulbController;
import entities.lightbulb.Lightbulb;
import entities.trafficlight.TrafficLight;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.input.*;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import tools.ObjectIO;

public class TrafficlightController {

    @FXML
    private VBox bulbContainer;

    private TrafficLight trafficLight;

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


                            deleteItem.setOnAction(event1 -> removeBulb(trafficLight, l));
                            contextMenu.getItems().addAll(moveUpItem, moveDownItem, deleteItem);
                            contextMenu.show(pane, event.getScreenX(), event.getScreenY());

                        }
                    }
                });
                System.out.println(l.getColor());
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
        System.out.println("drag entered");
        event.consume();
    }

    public void handleDragDrop(DragEvent event) {
        System.out.println("drag dropp");
        if (event.getDragboard().hasString()) {
            String fileName = (String) event.getDragboard().getContent(DataFormat.PLAIN_TEXT);
            Lightbulb lightbulb = ObjectIO.open(fileName);
            System.out.println(lightbulb);
            this.trafficLight.addLightBulb(lightbulb);
            this.initData(trafficLight);
        }
    }

    public void handleDragDetected() {
        System.out.println("drag detected");
    }

}
