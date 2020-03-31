package entities.direction;

public enum DirectionState {

    UNCONTROLLED {
        @Override
        public String toString() {
            return super.toString();
        }

        @Override
        String getStateString() {
            return "UNCONTROLLED";
        }
    },
    CONTROLLED {
        @Override
        public String toString() {
            return super.toString();
        }

        @Override
        String getStateString() {
            return "CONTROLLED";
        }
    };

    abstract String getStateString();
}
