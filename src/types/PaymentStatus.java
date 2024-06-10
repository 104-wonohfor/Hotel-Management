package types;

public enum PaymentStatus {
    PAID, UNPAID;

    public static PaymentStatus fromString(String status) {
        switch (status) {
            case "PAID":
                return PAID;
            case "UNPAID":
                return UNPAID;
            default:
                return null;
        }
    }
}
