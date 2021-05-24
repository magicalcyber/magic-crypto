package cc.magickiat.crypto.exchange;

import okhttp3.WebSocket;

public interface Exchange {
    WebSocket getWebSocketCandlestick();
}
