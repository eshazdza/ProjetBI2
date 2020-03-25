package Main;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import views.button.ButtonView;
import views.menu.EditMenu;
import views.menu.FileMenu;
import views.tools.ConfirmBox;

public class Main extends Application {
    private static Stage mainWindow;

    public static Stage getMainWindow() {
        return mainWindow;
    }


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        mainWindow = primaryStage;
        mainWindow.setTitle("Traffic Light Simulator");
        mainWindow.setOnCloseRequest(event -> {
            event.consume();
            closeProgram();
        });

        FileMenu fileMenu = new FileMenu("File");
        EditMenu editMenu = new EditMenu("Edit");


//        Add both menus to a border pane
        BorderPane borderPane = new BorderPane();
        MenuBar menuBar = new MenuBar(fileMenu, editMenu);

        VBox menuBox = new VBox(menuBar);

        borderPane.setTop(menuBox);


        Scene scene = new Scene(borderPane, 800, 600);
        scene.getStylesheets().add("theme/theme.css");
        mainWindow.setScene(scene);
        mainWindow.show();

    }

    private void closeProgram() {

        boolean answer = ConfirmBox.display("title", "Confirm Exit?");
        if (answer) {
            mainWindow.close();
        }
    }
}
