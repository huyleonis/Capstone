package hackathon.fpt.ktk.util;

public enum BeaconType {

    BEACON_PAYMENT(1, "BEACON_PAYMENT"),
    BEACON_RESULT(2, "BEACON_RESULT"),
    BEACON_OTHER(0, "Other");

    private int type;
    private String name;

    private BeaconType(int type, String name) {
        this.type = type;
        this.name = name;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
