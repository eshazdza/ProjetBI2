package controllers;

import entities.lightbulb.Lightbulb;
import views.LightBulb.LightbulbView;

import java.util.EventListener;

public class LightbulbController {

    private Lightbulb lightbulb = new Lightbulb();

    public LightbulbController(Lightbulb lightbulb) {
        this.lightbulb = lightbulb;
//        lightbulbView.addListener();
    }

    public void switchOnOff() {
        lightbulb.performRequest();
    }

    public void updateView() {

    }

    /* *************** LISTERNERS *************** */
    public void addListener(EventListener eventListener) {
        this.addListener(eventListener);
    }
    /* *************** END LISTERNER *************** */

}
