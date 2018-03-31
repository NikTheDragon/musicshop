package by.kurlovich.musicshop.receiver;

public class ReceiverException extends Exception {
    private static final long serialVersionUID = 1L;

    public ReceiverException() {
        super();
    }

    public ReceiverException(String message) {
        super(message);
    }

    public ReceiverException(Exception e) {
        super(e);
    }

    public ReceiverException(String message, Exception e) {
        super(message, e);
    }
}
