package sample.utils.exception;

/** CSV出力例外 */
public class CsvExportException extends RuntimeException {

    public CsvExportException(String message, Throwable cause) {
        super(message, cause);
    }

}