package cc.magickiat.crypto.websocket.exception;

public class WebSocketException extends RuntimeException {
    public WebSocketException() {
        super();
    }

    public WebSocketException(String message) {
        super(message);
    }

    public WebSocketException(Throwable cause) {
        super(cause);
    }

    public WebSocketException(String message, Throwable cause) {
        super(message, cause);
    }
}
