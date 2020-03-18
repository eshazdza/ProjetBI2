package controllers;

import com.sun.javafx.scene.control.LabeledText;
import entities.lightbulb.Lightbulb;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import tools.ConfirmBox;
import tools.ObjectIO;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.rmi.server.ExportException;
import java.util.ResourceBundle;

public class MainController implements Initializable {


    private Stage stage;

    @FXML
    private Parent mainRoot;

    @FXML
    private Pane actionWindow;

    private Pane actionContent;

    @FXML
    private VBox assetsWindow;

    @FXML
    private Lightbulb lightbulbDraw;

    /* *******************   MENU ACTIONS         ******************* */
    /* NEW */

    public void newLightbulb() {
        stage = (Stage) mainRoot.getScene().getWindow();

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/lightbulbCreator.fxml"));
            actionContent = loader.load();
            LightbulbCreatorController controller = loader.getController();
            controller.initData(this);

            actionWindow.getChildren().clear();
            actionWindow.getChildren().add(actionContent);

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void newLightbulb(String bulbName) {
        stage = (Stage) mainRoot.getScene().getWindow();

        Lightbulb lightbulb = ObjectIO.open(bulbName);

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/lightbulbCreator.fxml"));
            actionContent = loader.load();
            LightbulbCreatorController controller = loader.getController();
            controller.initData(lightbulb);

            actionWindow.getChildren().clear();
            actionWindow.getChildren().add(actionContent);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public void newTrafficLight() {
        System.out.println("tl");
    }

    public void newDirection() {
        System.out.println("direction");
    }

    public void newIntersection() {
        System.out.println("intersection");
    }

    /* END  NEW */

    /* OPEN */
    public void openFile() {
        System.out.println("openfile");
    }
    /* END  OPEN */

    /* SAVE */
    public void saveFile() {
        System.out.println("savefile");
    }
    /* END  SAVE */


    /* CLOSE */
    public void closeApp() {
        boolean answer = ConfirmBox.display("Quit?", "Confirm Exit?");
        if (answer) {
            Platform.exit();
        }
    }
    /* END  CLOSE */

    /* *******************   END   MENU ACTIONS      ******************* */


    /* *******************   ASSETS MENU         ******************* */

    public void buildMenu() {
        assetsWindow.getChildren().clear();
        TreeItem<String> rootItem = new TreeItem<>("Assets");
        rootItem.setExpanded(true);

        TreeItem<String> lightbulbsItem = new TreeItem<>("Light bulbs");
        TreeItem<String> trafficLightItem = new TreeItem<>("Traffic Light");
        TreeItem<String> directionsItem = new TreeItem<>("Directions");
        TreeItem<String> intersectionItem = new TreeItem<>("Intersections");

        /*
         * Load the light bulbs files name and add them to the menu
         */
        File[] lightbulbsFiles = ObjectIO.getFilesNameFromDir("src\\assets\\objects\\lightbulbs\\");

        for (int i = 0; i < lightbulbsFiles.length; i++) {
            TreeItem<String> bulb = new TreeItem<>(lightbulbsFiles[i].getName());
            lightbulbsItem.getChildren().add(bulb);
        }


        rootItem.getChildren().addAll(lightbulbsItem, trafficLightItem, directionsItem, intersectionItem);
        TreeView<String> assetsMenu = new TreeView<>(rootItem);

        Button refreshButton = new Button("Refresh Assets");

        refreshButton.setOnAction(event -> {
            this.buildMenu();
        });

        assetsWindow.getChildren().addAll(assetsMenu, refreshButton);

        assetsMenu.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2) {
                    TreeItem<String> item = assetsMenu.getSelectionModel().getSelectedItem();
                    System.out.println("Selected Text : " + item.getValue());

                    if (item.getValue().contains(".lightbulb")) {
                        newLightbulb(item.getValue());
                    }

                    if (item.getValue().contains(".trafficlight")) {
                        newTrafficLight();
                    }

                    if (item.getValue().contains(".direction")) {
                        newDirection();
                    }

                    if (item.getValue().contains(".intersection")) {
                        newIntersection();
                    }

                }
            }
        });
    }

    /* *******************   END   ASSETS MENU      ******************* */


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        buildMenu();
    }
}
