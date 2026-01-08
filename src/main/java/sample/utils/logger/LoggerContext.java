package sample.utils.logger;

/** ログコンテキスト */
public class LoggerContext {
    private static final ThreadLocal<Long> context = new ThreadLocal<>();

    /**
     * ログID設定
     * 
     * @param logId
     */
    public static void setLogId(Long logId) {
        context.set(logId);
    }

    /**
     * ログID取得
     * 
     * @return ログID
     */
    public static Long getLogId() {
        return context.get();
    }

    /**
     * ログIDクリア
     */
    public static void clear() {
        context.remove();
    }
}