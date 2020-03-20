package entities.triggerButton;

public enum SwitchButtonState {
    OFF {
        @Override
        SwitchButtonState switchOnOff(SwitchButton button) {
            button.setText("Turn Off");
            return ON;
        }
        @Override
        String getState(){
            return "OFF";
        }
    },
    ON {
        @Override
        SwitchButtonState switchOnOff(SwitchButton button) {
            button.setText("Turn On");
            return OFF;
        }
        @Override
        String getState(){
            return "ON";
        }
    };

    abstract SwitchButtonState switchOnOff(SwitchButton button);
    abstract String getState();

}

