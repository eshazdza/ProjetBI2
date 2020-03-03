package views.menu;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;

public class NewMenu extends Menu {

    private MenuItem newLightBulb;
    private MenuItem newTrafficLight;
    private MenuItem newDirection;
    private MenuItem newIntersection;

    public NewMenu(String text) {
        super(text);

        this.newLightBulb = new MenuItem("LightBulb");
        this.newTrafficLight = new MenuItem("Traffic Light");
        this.newDirection = new MenuItem("Direction");
        this.newIntersection = new MenuItem("Intersection");

        this.getItems().addAll(newLightBulb, newTrafficLight, newDirection, newIntersection);

        setActionEvents();
    }

    public void setActionEvents() {
        newLightBulb.setOnAction(e -> {
            System.out.println("new Lightbulb");
        });

        newTrafficLight.setOnAction(e -> {
            System.out.println("new Trafficl Light");
        });

        newDirection.setOnAction(e -> {
            System.out.println("new Direction");
        });

        newIntersection.setOnAction(e -> {
            System.out.println("new Intersection");
        });

    }


}
