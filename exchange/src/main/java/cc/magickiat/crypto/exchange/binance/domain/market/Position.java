package cc.magickiat.crypto.exchange.binance.domain.market;

import cc.magickiat.crypto.common.dto.Candlestick;
import lombok.Data;

@Data
public class Position {
    private Candlestick candlestick;
}
