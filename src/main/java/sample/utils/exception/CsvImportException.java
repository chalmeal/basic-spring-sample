package sample.utils.exception;

/** CSV取込例外 */
public class CsvImportException extends RuntimeException {

    public CsvImportException(String message) {
        super(message);
    }

    public CsvImportException(String message, Throwable cause) {
        super(message, cause);
    }

}
