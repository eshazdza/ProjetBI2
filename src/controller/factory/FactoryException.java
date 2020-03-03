package controller.factory;

public class FactoryException extends Throwable {
    private String message;

    public FactoryException(String message) {
        super(message);
    }
}
