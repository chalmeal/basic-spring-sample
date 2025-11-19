package sample.utils.exception;

/** 認証エラー例外 */
public class UnAuthorizedException extends RuntimeException {

    public UnAuthorizedException(String message) {
        super(message);
    }

}