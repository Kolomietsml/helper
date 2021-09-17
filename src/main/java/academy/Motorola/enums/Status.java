package academy.Motorola.enums;

public enum Status {

    OPEN("open"),
    SENT("sent"),
    COMPLETED("completed");

    private final String type;

    Status(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
