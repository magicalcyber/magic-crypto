package cc.magickiat.crypto.strategy;

import cc.magickiat.crypto.common.dto.Candlestick;
import com.tictactec.ta.lib.Core;
import com.tictactec.ta.lib.MAType;
import com.tictactec.ta.lib.MInteger;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class BollingerBandStrategy implements TradeStrategy {
    private static final int BB_PERIOD = 20;
    private static final double OPT_IN_DEV_UP = 2.0;
    private static final double OPT_IN_DEV_DOWN = 2.0;
    private static final MAType OPT_IN_MA_TYPE = MAType.Sma;
    public static final int INDEX_BB_UPPER_BAND = 0;
    public static final int INDEX_BB_MID_BAND = 1;
    public static final int INDEX_BB_LOWER_BAND = 2;


    private Core taCore = new Core();

    @Override
    public TradeAction process(List<Candlestick> candlesticks) {
        TradeAction action = new TradeAction();
        action.setTradeType(TradeType.DO_NOTHING);

        if (candlesticks == null || candlesticks.isEmpty() || candlesticks.size() < 2) {
            return action;
        }

        int beginIndex = 0;
        int endIndex = candlesticks.size() - 1;
        MInteger begin = new MInteger();
        MInteger length = new MInteger();


        double[] closedPrices = getClosedPrice(candlesticks);
        double[] tempOutputUpperBand = new double[closedPrices.length];
        double[] tempOutputMidBand = new double[closedPrices.length];
        double[] tempOutputLowerBand = new double[closedPrices.length];

        double[] resultUpperBand = createOutputArray(closedPrices.length);
        double[] resultMidBand = createOutputArray(closedPrices.length);
        double[] resultLowerBand = createOutputArray(closedPrices.length);

        taCore.bbands(beginIndex, endIndex, closedPrices, BB_PERIOD, OPT_IN_DEV_UP, OPT_IN_DEV_DOWN, OPT_IN_MA_TYPE, begin, length, tempOutputUpperBand, tempOutputMidBand, tempOutputLowerBand);

        for (int i = BB_PERIOD - 1; i < closedPrices.length; i++) {
            int tempResultIndex = i - BB_PERIOD + 1;
            resultUpperBand[i] = tempOutputUpperBand[tempResultIndex];
            resultMidBand[i] = tempOutputMidBand[tempResultIndex];
            resultLowerBand[i] = tempOutputLowerBand[tempResultIndex];
        }

        double[][] output = {new double[closedPrices.length], new double[closedPrices.length], new double[closedPrices.length]};
        for (int i = 0; i < closedPrices.length; i++) {
            output[INDEX_BB_UPPER_BAND][i] = resultUpperBand[i];
            output[INDEX_BB_MID_BAND][i] = resultMidBand[i];
            output[INDEX_BB_LOWER_BAND][i] = resultLowerBand[i];

            log.debug(String.format("output upper [%d] >>> %.4f\toutput lower [%d] >>> %.4f", i, resultUpperBand[i], i, resultLowerBand[i]));
        }

        double lastClosedPrice = closedPrices[closedPrices.length - 1];
        double prevLastClosedPrice = closedPrices[closedPrices.length - 2];


        boolean sell = canSell(lastClosedPrice, prevLastClosedPrice, output, closedPrices.length);
        if (sell) {
            action.setTradeType(TradeType.SELL);
            log.info(">>> CAN SELL!!! {}", candlesticks.get(candlesticks.size() - 1));
        } else {
            boolean buy = canBuy(lastClosedPrice, prevLastClosedPrice, output, closedPrices.length);
            if (buy) {
                action.setTradeType(TradeType.BUY);
                log.info(">>> CAN BUY!!! {}", candlesticks.get(candlesticks.size() - 1));
            }
        }

        return action;
    }

    private boolean canBuy(double lastClosedPrice, double prevLastClosedPrice, double[][] output, int priceLength) {
        double lastBbLower = output[INDEX_BB_LOWER_BAND][priceLength - 1];
        double prevLastBbLower = output[INDEX_BB_LOWER_BAND][priceLength - 1];

        log.debug(String.format("Can buy lastClosedPrice = %.4f, lastBbLower = %.4f, prevLastClosedPrice = %.4f, prevLastBbLower = %.4f", lastClosedPrice, lastBbLower, prevLastClosedPrice, prevLastBbLower));
        return lastClosedPrice > lastBbLower && prevLastClosedPrice < prevLastBbLower;
    }

    private boolean canSell(double lastClosedPrice, double prevClosedPrice, double[][] output, int priceLength) {
        double lastBbUpper = output[INDEX_BB_UPPER_BAND][priceLength - 1];
        double prevLastBbUpper = output[INDEX_BB_UPPER_BAND][priceLength - 1];

        log.debug(String.format("Can sell lastClosedPrice = %.4f, lastBbUpper = %.4f, prevClosedPrice = %.4f, prevLastBbUpper = %.4f", lastClosedPrice, lastBbUpper, prevClosedPrice, prevLastBbUpper));

        return lastClosedPrice < lastBbUpper && prevClosedPrice > prevLastBbUpper;
    }


}
