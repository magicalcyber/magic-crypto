package cc.magickiat.crypto.websocket;

public interface WebSocketCallback<T> {
    void onMessage(T message);
    void onFailure(Throwable cause);
}
