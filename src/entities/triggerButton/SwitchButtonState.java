package entities.triggerButton;

public enum SwitchButtonState {
    OFF {
        @Override
        SwitchButtonState switchOnOff(SwitchButton button) {
            button.setText("Turn Off");
            return ON;
        }
    },
    ON {
        @Override
        SwitchButtonState switchOnOff(SwitchButton button) {
            button.setText("Turn On");
            return OFF;
        }
    };

    abstract SwitchButtonState switchOnOff(SwitchButton button);

}

