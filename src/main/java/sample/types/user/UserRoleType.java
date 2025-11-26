package sample.types.user;

/** ユーザー権限定数 */
public enum UserRoleType {
    NONE(0, "NONE"),
    USER(1, "USER"),
    ADMIN(9, "ADMIN");

    private final int value;
    private final String roleName;

    UserRoleType(int value, String roleName) {
        this.value = value;
        this.roleName = roleName;
    }

    public int getValue() {
        return value;
    }

    public String getRoleName() {
        return roleName;
    }

    public static String fromValue(int value) {
        for (UserRoleType role : values()) {
            if (role.getValue() == value) {
                return role.getRoleName();
            }
        }
        throw new IllegalArgumentException();
    }
}