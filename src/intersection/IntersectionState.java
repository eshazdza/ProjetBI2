package intersection;

public enum IntersectionState {
    YOLO {
        @Override IntersectionState readState(){
            System.out.println("we are in yolo mode");
            return YOLO;
        }

        @Override
        public String toString() {
            return super.toString();
        }
    },
    CONTROLLED {
        @Override IntersectionState readState(){
            System.out.println("we are in controlled mode");
            return CONTROLLED;
        }
    };

    abstract IntersectionState readState();
}
