package cc.magickiat.crypto.exchange.binance;

import cc.magickiat.crypto.common.dto.Candlestick;
import cc.magickiat.crypto.exchange.Exchange;
import cc.magickiat.crypto.exchange.binance.domain.adapter.CandlestickAdapter;
import cc.magickiat.crypto.exchange.binance.domain.event.BinanceCandlestickEvent;
import cc.magickiat.crypto.exchange.binance.callback.BinanceWebSocketCandlestickCallback;
import cc.magickiat.crypto.exchange.binance.domain.market.Position;
import cc.magickiat.crypto.strategy.BollingerBandStrategy;
import cc.magickiat.crypto.strategy.RsiStrategy;
import cc.magickiat.crypto.websocket.BaseWebSocket;
import cc.magickiat.crypto.websocket.BaseWebSocketListener;
import com.binance.api.client.BinanceApiClientFactory;
import com.binance.api.client.BinanceApiRestClient;
import com.binance.api.client.domain.market.CandlestickInterval;
import lombok.extern.slf4j.Slf4j;
import okhttp3.WebSocket;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class Binance implements Exchange {

    // TODO: Check balance before buy/sell
    // TODO: Async buy/sell
    // TODO: Add stop-loss/take profit

    private static final String API_KEY = System.getenv("BINANCE_API_KEY");
    private static final String API_SECRET = System.getenv("BINANCE_API_SECRET");

    private BaseWebSocket baseWebSocket;

    private BinanceApiRestClient binanceClient;

    private Position position;

    public Binance(BaseWebSocket baseWebSocket) {
        this.baseWebSocket = baseWebSocket;

        BinanceApiClientFactory factory = BinanceApiClientFactory.newInstance(API_KEY, API_SECRET);
        this.binanceClient = factory.newRestClient();
    }

    @Override
    public WebSocket getWebSocketCandlestick() {
        String url = "wss://stream.binance.com:9443/ws/btcbusd@kline_1m";
        log.info("URL = " + url);

        // TODO: Init candlestick data by using old data from exchange
        List<com.binance.api.client.domain.market.Candlestick> candlesticks = binanceClient.getCandlestickBars("BTCBUSD", CandlestickInterval.ONE_MINUTE);
        candlesticks.remove(candlesticks.size() - 1); // TODO: Make sense to remove unclosed candlestick
        List<Candlestick> initCandlesticks = CandlestickAdapter.convertBinanceCandlestick(candlesticks);
        BinanceWebSocketCandlestickCallback callback = new BinanceWebSocketCandlestickCallback(this, new BollingerBandStrategy(), initCandlesticks);
        BaseWebSocketListener listener = new BaseWebSocketListener(callback, BinanceCandlestickEvent.class);
        return baseWebSocket.createWebSocket(url, listener);
    }

    @Override
    public void buy(Candlestick candlestick) {
        if(position == null) {
            log.info("\t!!! BUY!!!, BUY!!!, BUY!!! >>>>> " + candlestick.toString());
            position = new Position();
            position.setCandlestick(candlestick);
        }else {
            log.info("\t!!! ALREADY IN POSITION");
        }

    }

    @Override
    public void sell(Candlestick candlestick) {
        if(position != null) {
            log.info("\t!!! SELL!!!, SELL!!!, SELL!!! >>>>> " + candlestick.toString());
            position = null;
        }
    }
}
