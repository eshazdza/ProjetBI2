package views.menu;

import entities.lightbulb.Lightbulb;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import factory.Factory;
import javafx.scene.layout.GridPane;
import Main.Main;

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
            createLightbulb();
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

    public void createLightbulb(){
        Lightbulb lightbulb = Factory.makeLightbulb();
    }

}
