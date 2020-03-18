package controllers;

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

    void initData(Lightbulb lightbulb) {
        lightbulbDraw.setColor(lightbulb.getColor());
        lightbulbDraw.setFill(lightbulb.getColor());
        System.out.println(lightbulbDraw);
    }

    void initData(MainController controller){
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
