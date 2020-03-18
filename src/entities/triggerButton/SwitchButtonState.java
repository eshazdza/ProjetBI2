package entities.triggerButton;

import javafx.scene.control.Button;


public enum SwitchButtonState {
    OFF {
        @Override
        SwitchButtonState switchOnOff(Button button) {
            button.setText("Turn Off");
            return ON;
        }
    },
    ON {
        @Override
        SwitchButtonState switchOnOff(Button button) {
            button.setText("Turn On");
            return OFF;
        }
    };

    abstract SwitchButtonState switchOnOff(Button button);

}

