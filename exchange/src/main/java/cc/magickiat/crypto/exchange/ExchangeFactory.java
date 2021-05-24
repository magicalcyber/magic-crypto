package cc.magickiat.crypto.exchange;

import cc.magickiat.crypto.exchange.binance.Binance;
import cc.magickiat.crypto.websocket.BaseWebSocket;

public class ExchangeFactory {
    private BaseWebSocket baseWebSocket;
    private static ExchangeFactory instance;

    private ExchangeFactory() {
        baseWebSocket = new BaseWebSocket();
    }

    public static ExchangeFactory getInstance() {
        if (instance == null) {
            instance = new ExchangeFactory();
        }

        return instance;
    }

    public Exchange getBinanceInstance() {
        return new Binance(baseWebSocket);
    }
}
