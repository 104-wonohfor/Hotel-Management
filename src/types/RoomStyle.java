package types;

public enum RoomStyle {
    STANDARD, DELUXE, FAMILY_SUITE, BUSINESS_SUITE;

    public static RoomStyle fromString(String style) {
        switch (style) {
            case "STANDARD":
                return STANDARD;
            case "DELUXE":
                return DELUXE;
            case "FAMILY_SUITE":
                return FAMILY_SUITE;
            case "BUSINESS_SUITE":
                return BUSINESS_SUITE;
            default:
                return null;
        }
    }
}
