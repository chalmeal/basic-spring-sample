package sample.utils.exception;

/**
 * 既に存在するリソースに関する例外
 */
public class ExistsResourceException extends RuntimeException {

    public ExistsResourceException() {
        super();
    }

    public ExistsResourceException(String message) {
        super(message);
    }

    public ExistsResourceException(String message, String key) {
        super(String.format(message + "[%s]", key));
    }

}