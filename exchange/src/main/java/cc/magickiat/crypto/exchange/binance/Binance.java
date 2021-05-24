package cc.magickiat.crypto.exchange.binance;

import cc.magickiat.crypto.exchange.Exchange;
import cc.magickiat.crypto.exchange.binance.domain.event.BinanceCandlestickEvent;
import cc.magickiat.crypto.exchange.callback.BinanceWebSocketCandlestickCallback;
import cc.magickiat.crypto.websocket.BaseWebSocket;
import cc.magickiat.crypto.websocket.BaseWebSocketListener;
import lombok.extern.slf4j.Slf4j;
import okhttp3.WebSocket;

@Slf4j
public class Binance implements Exchange {

    private BaseWebSocket baseWebSocket;

    public Binance(BaseWebSocket baseWebSocket) {
        this.baseWebSocket = baseWebSocket;
    }

    @Override
    public WebSocket getWebSocketCandlestick() {
        String url = "wss://stream.binance.com:9443/ws/btcbusd@kline_1m";
        log.info("URL = " + url);
        BinanceWebSocketCandlestickCallback callback = new BinanceWebSocketCandlestickCallback();
        BaseWebSocketListener listener = new BaseWebSocketListener(callback, BinanceCandlestickEvent.class);
        return baseWebSocket.createWebSocket(url, listener);
    }
}
