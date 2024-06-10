package types;

public enum BookingStatus {
    REQUESTED, CONFIRMED, CHECKED_IN, CHECKED_OUT, CANCELLED;

    public static BookingStatus fromString(String status) {
        switch (status) {
            case "REQUESTED":
                return REQUESTED;
            case "CONFIRMED":
                return CONFIRMED;
            case "CHECKED_IN":
                return CHECKED_IN;
            case "CHECKED_OUT":
                return CHECKED_OUT;
            case "CANCELLED":
                return CANCELLED;
            default:
                return null;
        }
    }
}
