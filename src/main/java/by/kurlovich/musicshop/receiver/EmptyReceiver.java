package by.kurlovich.musicshop.receiver;

public class EmptyReceiver {
    private static final EmptyReceiver instance = new EmptyReceiver();

    public static EmptyReceiver getInstance() {
        return instance;
    }

}
