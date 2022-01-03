package subota.max.core.confirmation;

public enum ConfirmationType {
    EMAIL_CONFIRMATION,
    MOBILE_CONFIRMATION;

    public static ConfirmationType valueOfIgnoreCase(String value) {
        return valueOf(value.toUpperCase());
    }
}
