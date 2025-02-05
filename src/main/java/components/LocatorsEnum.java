package components;

public enum LocatorsEnum {
    USERNAME("username"),
    EMAIL("email"),
    PASSWORD("password"),
    CONFIRM_PASSWORD("confirm_password"),
    BIRTHDATE("birthdate"),
    LANGUAGE_LEVEL("language_level"),
    OUTPUT("output");

    private final String value;
    LocatorsEnum(String value) { this.value = value; }
    public String getValue() { return this.value; }
}
