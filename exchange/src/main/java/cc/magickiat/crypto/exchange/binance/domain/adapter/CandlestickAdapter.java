package cc.magickiat.crypto.exchange.binance.domain.adapter;

import cc.magickiat.crypto.common.dto.Candlestick;
import cc.magickiat.crypto.exchange.binance.domain.event.BinanceCandlestickEvent;

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
}
