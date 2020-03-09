package views.LightBulb;

import controllers.LightbulbController;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

import java.util.EventListener;

public class LightbulbView implements EventListener {

    private GridPane gridPane;
    private Scene scene;

    private Label header;

    private LightbulbController lightbulbController;

    public LightbulbView(LightbulbController lightbulbController) {

        this.gridPane = new GridPane();
        this.scene = new Scene(gridPane);
        this.header = new Label("Create a new Lightbulb : ");
        this.lightbulbController = lightbulbController;
    }






}
