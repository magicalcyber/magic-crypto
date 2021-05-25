package cc.magickiat.crypto.exchange;

import cc.magickiat.crypto.common.dto.Candlestick;
import okhttp3.WebSocket;

public interface Exchange {
    WebSocket getWebSocketCandlestick();

    void buy(Candlestick candlestick);

    void sell(Candlestick candlestick);
}
