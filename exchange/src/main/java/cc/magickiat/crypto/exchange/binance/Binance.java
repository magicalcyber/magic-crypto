package cc.magickiat.crypto.exchange.binance;

import cc.magickiat.crypto.common.dto.Candlestick;
import cc.magickiat.crypto.exchange.Exchange;
import cc.magickiat.crypto.exchange.binance.domain.event.BinanceCandlestickEvent;
import cc.magickiat.crypto.exchange.binance.callback.BinanceWebSocketCandlestickCallback;
import cc.magickiat.crypto.strategy.RsiStrategy;
import cc.magickiat.crypto.websocket.BaseWebSocket;
import cc.magickiat.crypto.websocket.BaseWebSocketListener;
import lombok.extern.slf4j.Slf4j;
import okhttp3.WebSocket;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class Binance implements Exchange {

    // TODO: Load api_key, secret
    // TODO: Check balance before buy/sell
    // TODO: Async buy/sell

    private BaseWebSocket baseWebSocket;

    public Binance(BaseWebSocket baseWebSocket) {
        this.baseWebSocket = baseWebSocket;
    }

    @Override
    public WebSocket getWebSocketCandlestick() {
        String url = "wss://stream.binance.com:9443/ws/btcbusd@kline_1m";
        log.info("URL = " + url);

        // TODO: Init candlestick data by using old data from exchange
        List<Candlestick> initCandlesticks = new ArrayList<>();
        BinanceWebSocketCandlestickCallback callback = new BinanceWebSocketCandlestickCallback(this, new RsiStrategy(), initCandlesticks);
        BaseWebSocketListener listener = new BaseWebSocketListener(callback, BinanceCandlestickEvent.class);
        return baseWebSocket.createWebSocket(url, listener);
    }

    @Override
    public void buy(Candlestick candlestick) {
        log.info("\t!!! BUY!!!, BUY!!!, BUY!!! >>>>> " + candlestick.toString());
    }

    @Override
    public void sell(Candlestick candlestick) {
        log.info("\t!!! SELL!!!, SELL!!!, SELL!!! >>>>> " + candlestick.toString());
    }
}
