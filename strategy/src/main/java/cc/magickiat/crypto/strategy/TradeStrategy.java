package cc.magickiat.crypto.strategy;

import cc.magickiat.crypto.common.dto.Candlestick;

import java.util.List;

public interface TradeStrategy {
    TradeAction process(List<Candlestick> candlesticks);

    default double[] getClosedPrice(List<Candlestick> candlesticks) {
        double[] result = new double[candlesticks.size()];
        for (int i = 0; i < candlesticks.size(); i++) {
            result[i] = Double.valueOf(candlesticks.get(i).getClose());
        }
        return result;
    }

    default double[] createOutputArray(int outputSize){
        double[] output = new double[outputSize];

        for (int i = 0; i < output.length; i++) {
            output[i] = -1;
        }

        return output;
    }
}
