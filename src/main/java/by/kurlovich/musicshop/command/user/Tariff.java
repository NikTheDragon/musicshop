package by.kurlovich.musicshop.command.user;

public enum Tariff {
    MIN("min", 1), OPT("opt", 10), MAX("max", 50);

    Tariff(String value, int points) {
        this.points = points;
        this.value = value;
    }

    private int points;
    private String value;

    public int getPoints() {
        return points;
    }

    public static Tariff fromString(String text) {
        for (Tariff tariff : Tariff.values()) {
            if (tariff.value.equalsIgnoreCase(text)) {
                return tariff;
            }
        }
        return null;
    }
}
