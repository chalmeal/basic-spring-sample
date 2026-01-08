package sample.types.log;

/** ログ結果種別 */
public enum LogResultType {
    FAILURE(0, "FAILURE"),
    SUCCESS(1, "SUCCESS"),
    WARNING(2, "WARNING"),
    INFO(3, "INFO"),
    PROCESSING(9, "PROCESSING");

    private final int value;
    private final String resultName;

    LogResultType(int value, String resultName) {
        this.value = value;
        this.resultName = resultName;
    }

    public int getValue() {
        return value;
    }

    public String getResultName() {
        return resultName;
    }

    public static String fromValue(int value) {
        for (LogResultType result : values()) {
            if (result.getValue() == value) {
                return result.getResultName();
            }
        }
        throw new IllegalArgumentException();
    }
}
