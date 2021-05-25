package cc.magickiat.crypto.strategy;

import cc.magickiat.crypto.common.dto.Candlestick;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BollingerBandStrategyTest {

    private static final BollingerBandStrategy strategy = new BollingerBandStrategy();

    @Test
    public void testCanBuy() throws Exception{
        List<Candlestick> candlesticks = CandlestickTestUtil.readCandleStick("/test-bb-buy.csv");
        TradeAction action = strategy.process(candlesticks);
        assertEquals(TradeType.BUY, action.getTradeType());
    }

    @Test
    public void testCanSell() throws Exception{
        List<Candlestick> candlesticks = CandlestickTestUtil.readCandleStick("/test-bb-sell.csv");
        TradeAction action = strategy.process(candlesticks);
        assertEquals(TradeType.SELL, action.getTradeType());
    }

    @Test
    public void testDoNothing() throws Exception{
        List<Candlestick> candlesticks = CandlestickTestUtil.readCandleStick("/test-bb-do-nothing.csv");
        TradeAction action = strategy.process(candlesticks);
        assertEquals(TradeType.DO_NOTHING, action.getTradeType());
    }

    @Test
    public void testCandleStickSizeIsOne_whenProcess_willBeDoNothing() throws Exception{
        List<Candlestick> candlesticks = new ArrayList<>(1);
        candlesticks.add(new Candlestick());

        TradeAction action = strategy.process(candlesticks);
        assertEquals(TradeType.DO_NOTHING, action.getTradeType());
    }
}