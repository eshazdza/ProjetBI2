package views.LightBulb;

import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

public class LightbulbView {

    private GridPane gridPane ;
    private Scene scene;

    private Label header;

    public LightbulbView() {
        this.gridPane= new GridPane();
        this.scene = new Scene(gridPane);
        this.header = new Label("Create a new Lightbulb : ");
//        gridPane.add(header);
    }



}
