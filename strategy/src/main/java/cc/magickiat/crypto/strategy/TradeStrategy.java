package cc.magickiat.crypto.strategy;

import cc.magickiat.crypto.common.dto.Candlestick;

import java.util.List;

public interface TradeStrategy {
    TradeAction process(List<Candlestick> candlesticks);
}
