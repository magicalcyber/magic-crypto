package cc.magickiat.crypto.exchange.callback;

import cc.magickiat.crypto.exchange.binance.domain.event.BinanceCandlestickEvent;
import cc.magickiat.crypto.websocket.WebSocketCallback;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BinanceWebSocketCandlestickCallback implements WebSocketCallback<BinanceCandlestickEvent> {
    @Override
    public void onMessage(BinanceCandlestickEvent message) {
        log.info(message.toString());
    }

    @Override
    public void onFailure(Throwable cause) {
        log.error(cause.getMessage(), cause);
    }
}
