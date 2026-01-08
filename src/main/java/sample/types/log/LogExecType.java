package sample.types.log;

/** ログ実行種別 */
public enum LogExecType {
    API(1, "API"),
    BATCH(2, "BATCH"),
    OTHER(9, "OTHER");

    private final int value;
    private final String typeName;

    LogExecType(int value, String typeName) {
        this.value = value;
        this.typeName = typeName;
    }

    public int getValue() {
        return value;
    }

    public String getTypeName() {
        return typeName;
    }

    public static String fromValue(int value) {
        for (LogExecType type : values()) {
            if (type.getValue() == value) {
                return type.getTypeName();
            }
        }
        throw new IllegalArgumentException();
    }

}
