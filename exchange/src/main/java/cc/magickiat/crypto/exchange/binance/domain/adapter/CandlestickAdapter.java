package cc.magickiat.crypto.exchange.binance.domain.adapter;

import cc.magickiat.crypto.common.dto.Candlestick;
import cc.magickiat.crypto.exchange.binance.domain.event.BinanceCandlestickEvent;

import java.util.List;
import java.util.stream.Collectors;

public class CandlestickAdapter {

    public static Candlestick convertCandlestickEvent(BinanceCandlestickEvent event){
        Candlestick candlestick = new Candlestick();
        candlestick.setClose(event.getClose());
        candlestick.setOpen(event.getOpen());
        candlestick.setHigh(event.getHigh());
        candlestick.setLow(event.getLow());
        candlestick.setBarFinal(event.getBarFinal());
        candlestick.setCloseTime(event.getCloseTime());
        return candlestick;
    }

    public static Candlestick convertCandlestickBinance(com.binance.api.client.domain.market.Candlestick event){
        Candlestick candlestick = new Candlestick();
        candlestick.setClose(event.getClose());
        candlestick.setOpen(event.getOpen());
        candlestick.setHigh(event.getHigh());
        candlestick.setLow(event.getLow());
        candlestick.setCloseTime(event.getCloseTime());
        return candlestick;
    }

    public static List<Candlestick> convertBinanceCandlestick(List<com.binance.api.client.domain.market.Candlestick> candlesticks) {
        return candlesticks.stream().map(CandlestickAdapter::convertCandlestickBinance).collect(Collectors.toList());
    }
}
