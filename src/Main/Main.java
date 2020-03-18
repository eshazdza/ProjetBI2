package Main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import tools.ConfirmBox;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/views/main.fxml"));
        primaryStage.setTitle("Traffic Light Simulator");

        primaryStage.setOnCloseRequest(event -> {
            event.consume();
            closeProgram(primaryStage);
        });


        Scene scene = new Scene(root, 800, 600);
        scene.getStylesheets().add("assets/theme.css");

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void closeProgram(Stage primaryStage) {
        boolean answer = ConfirmBox.display("title", "Confirm Exit?");
        if (answer) {
            primaryStage.close();
        }
    }
}
