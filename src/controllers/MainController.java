package controllers;

import controllers.direction.DirectionCreatorController;
import controllers.intersection.IntersectionCreatorController;
import controllers.lightbulb.LightbulbCreatorController;
import controllers.trafficlight.TrafficlightCreatorController;
import entities.direction.Direction;
import entities.intersection.Intersection;
import entities.lightbulb.Lightbulb;
import entities.trafficlight.TrafficLight;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.input.*;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;
import tools.ConfirmBox;
import tools.ObjectIO;

import java.io.File;
import java.io.IOException;
import java.net.URL;
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
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/lightbulbs/lightbulbCreator.fxml"));
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

        Lightbulb lightbulb = (Lightbulb) ObjectIO.open(bulbName);

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/lightbulbs/lightbulbCreator.fxml"));
            actionContent = loader.load();
            LightbulbCreatorController controller = loader.getController();
            controller.initData(lightbulb, this);

            actionWindow.getChildren().clear();
            actionWindow.getChildren().add(actionContent);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public void newTrafficLight() {
        newTrafficLight(null);
    }

    public void newTrafficLight(String trafficlightName) {
        stage = (Stage) mainRoot.getScene().getWindow();
        TrafficLight trafficLight = new TrafficLight();

        if (trafficlightName != null) {
            trafficLight = (TrafficLight) ObjectIO.open(trafficlightName);
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/trafficlights/trafficlightCreator.fxml"));
            actionContent = loader.load();

            TrafficlightCreatorController controller = loader.getController();

            controller.initData(trafficLight);

            actionWindow.getChildren().clear();
            actionWindow.getChildren().add(actionContent);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void newDirection() {
        newDirection(null);
    }

    public void newDirection(String directionName) {
        stage = (Stage) mainRoot.getScene().getWindow();
        Direction direction = new Direction();
        if (directionName != null) {
            direction = (Direction) ObjectIO.open(directionName);
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/directions/directionCreator.fxml"));
            actionContent = loader.load();

            // Set Action content min size
            actionContent.setMinHeight(actionWindow.getHeight());
            actionContent.setMinWidth(actionWindow.getWidth());

            // Resize the action content to match the parent (action window) size
            actionWindow.heightProperty().addListener(new ChangeListener<Number>() {

                @Override
                public void changed(ObservableValue<? extends Number> arg0,
                                    Number arg1, Number arg2) {
                    actionContent.setPrefHeight(arg2.doubleValue());
                }
            });

            actionWindow.widthProperty().addListener(new ChangeListener<Number>() {

                @Override
                public void changed(ObservableValue<? extends Number> arg0,
                                    Number arg1, Number arg2) {
                    actionContent.setPrefWidth(arg2.doubleValue());
                }
            });

            actionWindow.getChildren().clear();
            actionWindow.getChildren().add(actionContent);

            DirectionCreatorController controller = loader.getController();

            if (directionName != null) {
                controller.initData(direction);
            } else {
                controller.initData();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void newIntersection() {
        stage = (Stage) mainRoot.getScene().getWindow();
        Intersection intersection = new Intersection();

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/intersection/intersectionCreator.fxml"));
            actionContent = loader.load();

            IntersectionCreatorController controller = loader.getController();
            controller.initData();

            actionWindow.getChildren().clear();
            actionWindow.getChildren().addAll(actionContent);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void newIntersection(String intersectionName) {
        stage = (Stage) mainRoot.getScene().getWindow();
        Intersection intersection = new Intersection();

        if (intersectionName != null) {
            intersection = (Intersection) ObjectIO.open(intersectionName);
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/intersection/intersectionCreator.fxml"));
            actionContent = loader.load();

            IntersectionCreatorController controller = loader.getController();

            controller.initData(intersection);

            actionWindow.getChildren().clear();
            actionWindow.getChildren().add(actionContent);
        } catch (Exception e) {
            e.printStackTrace();
        }
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
        File[] trafficlightFiles = ObjectIO.getFilesNameFromDir("src\\assets\\objects\\trafficlights\\");
        File[] directionsFiles = ObjectIO.getFilesNameFromDir("src\\assets\\objects\\directions\\");
        File[] intersectionsFiles =  ObjectIO.getFilesNameFromDir("src\\assets\\objects\\intersections\\");

        for (int i = 0; i < lightbulbsFiles.length; i++) {
            TreeItem<String> bulb = new TreeItem<>(lightbulbsFiles[i].getName());
            lightbulbsItem.getChildren().add(bulb);
        }

        for (int i = 0; i < trafficlightFiles.length; i++) {
            TreeItem<String> trafficlight = new TreeItem<>(trafficlightFiles[i].getName());
            trafficLightItem.getChildren().add(trafficlight);
        }

        for (int i = 0; i < directionsFiles.length; i++) {
            TreeItem<String> direction = new TreeItem<>(directionsFiles[i].getName());
            directionsItem.getChildren().add(direction);
        }

        for (int i = 0; i < intersectionsFiles.length; i++) {
            TreeItem<String> intersection = new TreeItem<>(intersectionsFiles[i].getName());
            intersectionItem.getChildren().add(intersection);
        }


        rootItem.getChildren().addAll(lightbulbsItem, trafficLightItem, directionsItem, intersectionItem);
        TreeView<String> assetsMenu = new TreeView<>(rootItem);
//        TreeView<String> assetsMenu = new TreeView<>();

        assetsMenu.setCellFactory(new Callback<TreeView<String>, TreeCell<String>>() {
            @Override
            public TreeCell<String> call(TreeView<String> param) {
                TreeCell<String> treeCell = new TreeCell<>() {
                    protected void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (!empty && item != null) {
                            setText(item);
                            setGraphic(getTreeItem().getGraphic());
                        } else {
                            setText(null);
                            setGraphic(null);
                        }
                    }
                };

                treeCell.setOnDragDetected(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        try {
                            String targetName = ((Text) event.getTarget()).getText();
                            if (targetName.contains(".lightbulb") || targetName.contains(".trafficlight") || targetName.contains(".direction")) {
                                Dragboard db = treeCell.startDragAndDrop(TransferMode.ANY);
                                ClipboardContent content = new ClipboardContent();
                                content.putString(targetName);
                                db.setContent(content);
                                event.consume();
                            }

                        } catch (ClassCastException e) {
                            e.getStackTrace();
                        }
                    }
                });
                return treeCell;
            }
        });


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

                    if (item.getValue().contains(".lightbulb")) {
                        newLightbulb(item.getValue());
                    }

                    if (item.getValue().contains(".trafficlight")) {
                        newTrafficLight(item.getValue());
                    }

                    if (item.getValue().contains(".direction")) {
                        newDirection(item.getValue());
                    }

                    if (item.getValue().contains(".intersection")) {
                        newIntersection(item.getValue());
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
