package cc.magickiat.crypto.strategy;

import cc.magickiat.crypto.common.dto.Candlestick;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class RsiStrategyTest {

    private final RsiStrategy strategy = new RsiStrategy();

    @Test
    public void testCandlestickIsNull_whenRsiProcess_willBeTradeActionDoNothing() {
        TradeAction action = strategy.process(null);
        assertEquals(TradeType.DO_NOTHING, action.getTradeType());
    }

    @Test
    public void testCandlestickIsLowerThan14_whenRsiProcess_willBeTradeActionDoNothing() {
        List<Candlestick> candlesticks = new ArrayList<>();
        for (int i = 0; i < 13; i++) {
            Candlestick candlestick = new Candlestick();
            candlestick.setClose(i + ".55");
            candlesticks.add(candlestick);
        }

        TradeAction action = strategy.process(candlesticks);
        assertEquals(TradeType.DO_NOTHING, action.getTradeType());
    }

    @Test
    public void testRsiOverUpperBound_whenRsiProcess_willBeTradeActionSell() throws Exception {
        List<Candlestick> candlesticks = CandlestickTestUtil.readCandleStick("/test-rsi-over-80.csv");
        TradeAction action = strategy.process(candlesticks);
        assertEquals(TradeType.SELL, action.getTradeType());
    }

    @Test
    public void testRsiUnderLowerBound_whenRsiProcess_willBeTradeActionBuy() throws Exception {
        List<Candlestick> candlesticks = CandlestickTestUtil.readCandleStick("/test-rsi-under-20.csv");
        TradeAction action = strategy.process(candlesticks);
        assertEquals(TradeType.BUY, action.getTradeType());
    }
}