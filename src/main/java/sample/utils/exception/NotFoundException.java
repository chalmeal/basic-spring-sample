package sample.utils.exception;

/** リソース未発見例外 */
public class NotFoundException extends RuntimeException {

    public NotFoundException(String message) {
        super(message);
    }

    public NotFoundException(String message, String key) {
        super(String.format(message + "[%s]", key));
    }

}