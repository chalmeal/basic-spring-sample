package sample.utils.exception;

/** リソース既存例外 */
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