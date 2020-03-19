package entities.triggerButton;

import javafx.scene.control.Button;

public class SwitchButton extends Button {

    private SwitchButtonState state;

    public SwitchButton() {
        super();
        this.state = SwitchButtonState.OFF;
        this.setText("Turn On");
    }

    public void switchOnOff() {
        this.state = this.state.switchOnOff(this);
    }

}
