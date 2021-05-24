package cc.magickiat.crypto.cli;

import cc.magickiat.crypto.exchange.ExchangeFactory;
import lombok.extern.slf4j.Slf4j;
import okhttp3.WebSocket;

@Slf4j
public class MagicCrypto {
    public static void main(String[] args) {
        WebSocket webSocket = ExchangeFactory.getInstance().getBinanceInstance().getWebSocketCandlestick();

        Runtime.getRuntime().addShutdownHook(new Thread(() -> webSocket.close(1000, "bye!")));
    }
}
