package cc.magickiat.crypto.strategy;

import cc.magickiat.crypto.common.dto.Candlestick;
import com.tictactec.ta.lib.Core;
import com.tictactec.ta.lib.MInteger;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class RsiStrategy implements TradeStrategy {
    private static final int RSI_PERIOD = 14;
    private static final int UPPER_BOUND = 80;
    private static final int LOWER_BOUND = 20;

    private Core taCore = new Core();

    @Override
    public TradeAction process(List<Candlestick> candlesticks) {
        TradeAction action = new TradeAction();
        action.setTradeType(TradeType.DO_NOTHING);

        if (candlesticks == null || candlesticks.isEmpty()) {
            return action;
        }

        int beginIndex = 0;
        int endIndex = candlesticks.size() - 1;
        double[] closedPrices = getClosedPrice(candlesticks);
        double[] tempOutput = new double[closedPrices.length];
        double[] output = createOutputArray(closedPrices.length);

        MInteger begin = new MInteger();
        MInteger length = new MInteger();


        taCore.rsi(beginIndex, endIndex, closedPrices, RSI_PERIOD, begin, length, tempOutput);

        for (int i = RSI_PERIOD; i < tempOutput.length ; i++) {
            output[i] = tempOutput[i - RSI_PERIOD];
        }

        double lastRsi = output[output.length - 1];

        if(lastRsi > UPPER_BOUND){
            action.setTradeType(TradeType.SELL);
        }else if(lastRsi < LOWER_BOUND && lastRsi > -1){
            action.setTradeType(TradeType.BUY);
        }else {
            action.setTradeType(TradeType.DO_NOTHING);
        }

        return action;
    }

}
