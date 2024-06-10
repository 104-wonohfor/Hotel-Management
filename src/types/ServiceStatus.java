package types;

public enum ServiceStatus {
    REQUESTED, CONFIRMED, COMPLETED, CANCELLED;

    public static ServiceStatus fromString(String status) {
        switch (status) {
            case "REQUESTED":
                return REQUESTED;
            case "CONFIRMED":
                return CONFIRMED;
            case "COMPLETED":
                return COMPLETED;
            case "CANCELLED":
                return CANCELLED;
            default:
                return null;
        }
    }
}
