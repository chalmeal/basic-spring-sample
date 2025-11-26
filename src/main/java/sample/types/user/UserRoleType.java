package sample.types.user;

/** ユーザー権限定数 */
public enum UserRoleType {
    ADMIN(0, "ADMIN"),
    USER(1, "USER");

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