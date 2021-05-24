package cc.magickiat.crypto.websocket;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.WebSocket;

import java.util.concurrent.TimeUnit;

public class BaseWebSocket {

    private final OkHttpClient client;

    public BaseWebSocket() {
        client = new OkHttpClient.Builder()
                .connectTimeout(1, TimeUnit.MINUTES)
                .writeTimeout(1, TimeUnit.MINUTES)
                .readTimeout(1, TimeUnit.MINUTES)
                .build();
    }

    public WebSocket createWebSocket(String url, BaseWebSocketListener<?> listener) {
        Request request = new Request.Builder().url(url).build();
        return client.newWebSocket(request, listener);
    }
}
