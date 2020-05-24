package controllers.trafficlight;

import controllers.direction.DirectionController;
import controllers.lightbulb.LightbulbController;
import entities.lightbulb.Lightbulb;
import entities.trafficlight.TrafficLight;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.effect.Light;
import javafx.scene.input.*;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import tools.AlertBox;
import tools.BlinkerThread;
import tools.ObjectIO;
import tools.PhaseSwitcherThread;

import java.util.ArrayList;
import java.util.Collections;


public class TrafficlightController {

    @FXML
    private VBox bulbContainer;

    @FXML
    private TrafficLight trafficLight;

    private String mode = "MANUAL";

    private LightbulbController bulbController;

    private BlinkerThread blinker;

    private PhaseSwitcherThread phaseSwitcher;

    private ArrayList<Lightbulb> panicSignals = new ArrayList<>();

    //    bulbsize : allows us to render the bulbs in different size depending on context
    private String bulbSize;

    //    LightbulbControllers : list of the controllers linked to added lightbulbs
    private ArrayList<LightbulbController> lightbulbControllers = new ArrayList<>();

    //    DirectionController : allow us to know if the TrafficlightController is instantiated from within a DirectionController and potentially reflect the changes
    private DirectionController directionController = null;

    /**
     * Initialize the view data
     *
     * @param trafficLight TrafficLight
     *                     The linked traffic light
     * @param editMode     boolean
     *                     Can the trafficlight be edited
     */
    public void initData(TrafficLight trafficLight, boolean editMode, String bulbSize) {
//        Clear the container and lists
        bulbContainer.getChildren().clear();
        lightbulbControllers.clear();

//        Initialize panel data
        this.trafficLight.setBinded(trafficLight.isBinded());
        this.trafficLight.setLightbulbs(trafficLight.getLightbulbs());
        this.trafficLight.setState(trafficLight.getStateString());
        this.trafficLight.setName(trafficLight.getName());

//        ****
//        this.trafficLight = trafficLight;       part of refactor; delete if refactor works
//        ***

        this.bulbSize = bulbSize;

//        ***
//        If we are in edit mode
//        We turn all the lights on if the traffic light is off
//        if (editMode && trafficLight.getStateString().equals("OFF")) {
//            trafficLight.performRequest("FULLON");
//        }
//        ***

//        If we are in edit mode
//        We turn all the lights on if the traffic light is off
        if (editMode && this.trafficLight.getStateString().equals("OFF")) {
            this.trafficLight.performRequest("FULLON");
        }

//        We add each lightbulb of the trafficlight to the view
        for (Lightbulb lightbulb :
                trafficLight.getLightbulbs()) {

            this.addLightbulb(lightbulb);
        }
    }

    private void addLightbulb(Lightbulb lightbulb) {
        try {
//            Load FXML view and related controller
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/lightbulbs/lightbulb.fxml"));
            final StackPane pane = loader.load();

            LightbulbController bulbController = loader.getController();

//            Set the color of the bulbs
            bulbController.setColor(lightbulb.getColor());
            bulbController.setFill(lightbulb.getFill());

//            Set the display size of the bulbs
            if (this.bulbSize.equals("small")) {
                bulbController.setSize(20);
            } else {
                bulbController.setSize(100);
            }

//            Context Menu
            pane.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    if (event.getButton() == MouseButton.SECONDARY) {
                        ContextMenu contextMenu = new ContextMenu();

                        MenuItem moveUpItem = new MenuItem("Move Up");
                        MenuItem moveDownItem = new MenuItem("Move Down");
                        MenuItem addPanicSignalItem = new MenuItem("Set as panic signal");
                        MenuItem removePanicSignalItem = new MenuItem("Unset as panic signal");
                        MenuItem deleteItem = new MenuItem("Remove");

                        moveUpItem.setOnAction(event1 -> moveUpBulb(trafficLight, lightbulb));
                        moveDownItem.setOnAction(event1 -> moveDownBulb(trafficLight, lightbulb));
                        addPanicSignalItem.setOnAction(event1 -> setAsPanic(lightbulb));
                        removePanicSignalItem.setOnAction(event1 -> unsetAsPanic(lightbulb));
                        deleteItem.setOnAction(event1 -> removeBulb(trafficLight, lightbulb));

                        contextMenu.getItems().addAll(moveUpItem, moveDownItem, addPanicSignalItem, removePanicSignalItem, deleteItem);
                        contextMenu.show(pane, event.getScreenX(), event.getScreenY());

                    }
                }
            });

//            Add the bulbs to the view
            bulbContainer.getChildren().add(pane);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * Set a bulb as a panic signal (will blink when traffic light goes in panic mode)
     *
     * @param lightbulb Lightbulb
     */
    public void setAsPanic(Lightbulb lightbulb) {
        panicSignals.add(lightbulb);
        lightbulb.setPanicSignal(true);
    }

    /**
     * Unset a bulb as a panic signal
     *
     * @param lightbulb Lightbulb
     */
    public void unsetAsPanic(Lightbulb lightbulb) {
        panicSignals.remove(lightbulb);
        lightbulb.setPanicSignal(false);
    }

    /**
     * Remove a bulb from a traffic light
     *
     * @param trafficLight TrafficLight
     * @param lightbulb    Lightbulb
     */
    public void removeBulb(TrafficLight trafficLight, Lightbulb lightbulb) {
        trafficLight.getLightbulbs().remove(lightbulb);
        this.initData(trafficLight, true, this.bulbSize);
    }

    /**
     * @param event DragEvent
     */
    public void setOnDragOver(DragEvent event) {
        event.acceptTransferModes(TransferMode.ANY);
        event.consume();
    }

    /**
     * @param event DragEvent
     */
    public void handleDragEntered(DragEvent event) {
        event.consume();
    }

    /**
     * @param event DragEvent
     */
    public void handleDragDrop(DragEvent event) {
        if (trafficLight.getStateString().equals("OFF") || trafficLight.getStateString().equals("FULLON")) {
            if (event.getDragboard().hasString()) {
                String fileName = (String) event.getDragboard().getContent(DataFormat.PLAIN_TEXT);
                if (fileName.contains(".lightbulb")) {
                    Lightbulb lightbulb = (Lightbulb) ObjectIO.open(fileName);
                    lightbulb.performRequest("ON");
                    this.trafficLight.addLightBulb(lightbulb);
                    this.initData(trafficLight, true, "large");
                }
            }
        }
    }

    /**
     * Move a bulb up in the TrafficLight's list and reload the view
     *
     * @param trafficLight TrafficLight
     * @param lightbulb    Lightbulb
     */
    public void moveUpBulb(TrafficLight trafficLight, Lightbulb lightbulb) {
        int index = trafficLight.getLightbulbs().indexOf(lightbulb);
        if (index > 0) {
            Collections.swap(trafficLight.getLightbulbs(), index, index - 1);
            this.initData(trafficLight, true, this.bulbSize);
        }
    }

    /**
     * Move a bulb down in the TrafficLight's list and reload the view
     *
     * @param trafficLight TrafficLight
     * @param lightbulb    Lightbulb
     */
    public void moveDownBulb(TrafficLight trafficLight, Lightbulb lightbulb) {
        int index = trafficLight.getLightbulbs().indexOf(lightbulb);
        if (index < (trafficLight.getLightbulbs().size() - 1)) {
            Collections.swap(trafficLight.getLightbulbs(), index, index + 1);
            this.initData(trafficLight, true, this.bulbSize);
        }
    }

    /**
     * Change the execution mode of the Trafficlight (AUTO OR MANUAL)
     *
     * @param mode String
     */
    public void setMode(String mode) {
        this.mode = mode;
    }

    /**
     * Turn the traffic light on
     */
    public void runTrafficLight(String bulbSize) {
        this.trafficLight.performRequest("STANDBY");
        this.initData(trafficLight, false, bulbSize);
    }

    /**
     * Turn the traffic light off
     */
    public void turnOffTrafficLight(String bulbSize) {
        if (blinker != null) {
            blinker.stopThread();
        }
        if (phaseSwitcher != null) {
            phaseSwitcher.stopThread();
        }
        this.trafficLight.performRequest("OFF");
        this.initData(trafficLight, true, bulbSize);
    }

    /**
     * Run the traffic light in automatic mode
     * runAutoModeFromCreator needs to implements a Thread runner
     * since it does not depends on a DirectionController or intersection
     * It also needs to update the various buttons affiliated to the TrafficlightCreator
     */
    public boolean runAutoModeFromCreator(String bulbSize) {
        this.trafficLight.performRequest("AUTO");
        if (this.trafficLight.performRequest("GET").equals("AUTO")) {
            if (blinker != null) {
                blinker.stopThread();
            }
            if (phaseSwitcher != null) {
                phaseSwitcher.stopThread();
            }

            this.initData(trafficLight, false, bulbSize);

            phaseSwitcher = new PhaseSwitcherThread(trafficLight.getLightbulbs(), this, trafficLight, bulbSize);
            phaseSwitcher.startThread();
            Thread backgroundThread = new Thread(phaseSwitcher);
            backgroundThread.setDaemon(true);
            backgroundThread.start();
            return true;
        }
        return false;
    }


    /**
     * Run the traffic light in manual mode
     */
    public boolean runManualMode(String bulbSize) {
        this.trafficLight.performRequest("MANUAL");
        if (this.trafficLight.performRequest("GET").equals("MANUAL")) {
            if (blinker != null) {
                blinker.stopThread();
            }
            if (phaseSwitcher != null) {
                phaseSwitcher.stopThread();
            }
            return true;
        }
        return false;
    }

    /**
     * Run the traffic light in panic mode
     *
     * @param fromCreator boolean : has the method been called from the traffic light creator/editor ?
     *                    NO : the traffic light depends on a DirectionController or intersection controller which will control the blinking of the lightbulbs
     *                    YES : the traffic light is in control of the blinking
     */
    public boolean runPanicMode(boolean fromCreator, String bulbSize) {

//        Check that a panic signal bulb has been chosen by the user
//        Run the blinking thread if yes
        if (panicSignals.size() == 0) {
            AlertBox.display("No Panic Signal", "You have not set an alert signal bulb.");
        } else {
            if (phaseSwitcher != null) {
                phaseSwitcher.stopThread();
            }
            this.trafficLight.performRequest("PANIC");
            if (this.trafficLight.performRequest("GET").equals("PANIC")) {
                this.initData(trafficLight, false, bulbSize);

                blinker = new BlinkerThread(panicSignals, this, trafficLight, bulbSize);
                blinker.startThread();

                Thread backgroundThread = new Thread(blinker);
                backgroundThread.setDaemon(true);
                backgroundThread.start();

                return true;
            }

        }

        return false;

    }


    /**
     * Switch from different phase when the traffic light is in manual mode
     */
    public void switchPhase(String bulbSize) {

//        IF the traffic light contains only one bulb, we switch it on/off
        if (trafficLight.getLightbulbs().size() == 1) {
            trafficLight.getLightbulbs().get(0).performRequest();
        } else {
//        If the first light of the list ("Last" of the sequence) is on,
//        We turn on the last of the list (next in the sequence).
            if (trafficLight.getLightbulbs().get(0).getStateString().equals("ON")) {
                trafficLight.getLightbulbs().get(0).performRequest("OFF");
                trafficLight.getLightbulbs().get(trafficLight.getLightbulbs().size() - 1).performRequest("ON");
            } else {
                for (Lightbulb l :
                        trafficLight.getLightbulbs()) {
                    if (l.getStateString().equals("ON")) {
                        l.performRequest("OFF");
                        trafficLight.getLightbulbs().get(trafficLight.getLightbulbs().indexOf(l) - 1).performRequest("ON");
                    }
                }
            }
        }
        this.initData(trafficLight, false, bulbSize);
    }

    public ArrayList<Lightbulb> getOnLights() {
        ArrayList<Lightbulb> onBulbs = new ArrayList<>();

        for (Lightbulb l :
                trafficLight.getLightbulbs()) {
            if (l.getStateString().equals("ON")) {
                onBulbs.add(l);
            }
        }
        return onBulbs;
    }

    public TrafficLight getTrafficLight() {
        return trafficLight;
    }

}
