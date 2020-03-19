package controllers;

import Main.Main;
import entities.lightbulb.Lightbulb;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.paint.Color;
import tools.ObjectIO;

import java.io.IOException;


public class LightbulbCreatorController {

    @FXML
    private ColorPicker colorPicker;

    @FXML
    private Lightbulb lightbulbDraw = new Lightbulb();

    @FXML
    private Button saveButton = new Button();

    @FXML
    private Button deleteButton = new Button();

    private MainController mainController;

    void initData(Lightbulb lightbulb, MainController controller) {
        lightbulbDraw.setColor(lightbulb.getColor());
        lightbulbDraw.setFill(lightbulb.getColor());
        this.mainController = controller;
    }


    void initData(MainController controller){
        lightbulbDraw.setColor(Color.BEIGE);
        lightbulbDraw.setFill(Color.BEIGE);
        this.mainController = controller;
    }

    public void pickColor() {
        Color color = colorPicker.getValue();
        lightbulbDraw.setColor(color);
        lightbulbDraw.setFill(color);
    }

    public void saveBulb() {
        ObjectIO.save(lightbulbDraw);
    }

    public void deleteBulb() {
        mainController.newLightbulb();
        ObjectIO.delete(lightbulbDraw);
    }


}
