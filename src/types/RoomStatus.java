package types;

public enum RoomStatus {
    AVAILABLE, OCCUPIED, NOT_AVAILABLE;

    public static RoomStatus fromString(String status) {
        switch (status) {
            case "AVAILABLE":
                return AVAILABLE;
            case "OCCUPIED":
                return OCCUPIED;
            case "NOT_AVAILABLE":
                return NOT_AVAILABLE;
            default:
                return null;
        }
    }
}
