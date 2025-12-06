package sample.utils.exception;

/** リソース既存例外 */
public class ResourceExistsException extends BadRequestException {

    public ResourceExistsException() {
        super();
    }

    public ResourceExistsException(String message) {
        super(message);
    }

    public ResourceExistsException(String message, String key) {
        super(String.format(message + "[%s]", key));
    }

}