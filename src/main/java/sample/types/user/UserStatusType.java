package sample.types.user;

/** ユーザー状態定数 */
public enum UserStatusType {
    TEMPORARY(0),
    REGISTERED(1);

    private final int value;

    UserStatusType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
