package by.kurlovich.musicshop.receiver;

public class CommonReceiver {
    private static final CommonReceiver instance = new CommonReceiver();

    public static CommonReceiver getInstance() {
        return instance;
    }

}
