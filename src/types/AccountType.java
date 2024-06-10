package types;

public enum AccountType {
    GUEST, MANAGER, RECEPTIONIST;

    public static AccountType fromString(String type) {
        switch (type) {
            case "GUEST":
                return GUEST;
            case "MANAGER":
                return MANAGER;
            case "RECEPTIONIST":
                return RECEPTIONIST;
            default:
                return null;
        }
    }
}
