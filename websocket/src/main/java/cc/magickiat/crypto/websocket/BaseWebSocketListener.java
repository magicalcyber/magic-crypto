package cc.magickiat.crypto.websocket;

import cc.magickiat.crypto.websocket.exception.WebSocketException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;

import java.io.IOException;

@Slf4j
public class BaseWebSocketListener <T> extends WebSocketListener {
    private static final ObjectMapper mapper = new ObjectMapper();
    private final ObjectReader objectReader;
    private WebSocketCallback<T> callback;

    private boolean closing = false;

    public BaseWebSocketListener(WebSocketCallback<T> callback, Class<T> eventClass){
        this.callback = callback;
        this.objectReader = mapper.readerFor(eventClass);
        log.info("Init BaseWebSocketListener");
    }

    @Override
    public void onMessage(WebSocket webSocket, String text) {
        try{
            T event = objectReader.readValue(text);
            callback.onMessage(event);
        }catch (IOException ex){
            throw new WebSocketException(ex);
        }
    }

    @Override
    public void onClosing(WebSocket webSocket, int code,  String reason) {
        this.closing = true;
    }

    @Override
    public void onFailure( WebSocket webSocket,  Throwable t,  Response response) {
        if(!closing){
            callback.onFailure(t);
        }
    }
}
