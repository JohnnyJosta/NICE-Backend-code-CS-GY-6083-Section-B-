package jtw.nice.exception;

public class TokenAuthExpiredException extends RuntimeException {
    public TokenAuthExpiredException(String message) {
        super(message);
    }
}
