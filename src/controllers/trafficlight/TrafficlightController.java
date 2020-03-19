package controllers.trafficlight;

import controllers.lightbulb.LightbulbController;
import entities.lightbulb.Lightbulb;
import entities.trafficlight.TrafficLight;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class TrafficlightController {

    @FXML
    private VBox bulbContainer;

    public void initData(TrafficLight trafficLight) {
        bulbContainer.getChildren().clear();

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
                            MenuItem deleteItem = new MenuItem("Remove");

                            deleteItem.setOnAction(event1 -> removeBulb(trafficLight, l));
                            contextMenu.getItems().add(deleteItem);
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

}
