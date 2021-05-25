package cc.magickiat.crypto.exchange.binance.callback;

import cc.magickiat.crypto.common.dto.Candlestick;
import cc.magickiat.crypto.exchange.Exchange;
import cc.magickiat.crypto.exchange.binance.domain.adapter.CandlestickAdapter;
import cc.magickiat.crypto.exchange.binance.domain.event.BinanceCandlestickEvent;
import cc.magickiat.crypto.strategy.TradeAction;
import cc.magickiat.crypto.strategy.TradeStrategy;
import cc.magickiat.crypto.strategy.TradeType;
import cc.magickiat.crypto.websocket.WebSocketCallback;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class BinanceWebSocketCandlestickCallback implements WebSocketCallback<BinanceCandlestickEvent> {

    private final TradeStrategy tradeStrategy;
    private final List<Candlestick> candlesticks = new ArrayList<>();
    private final Exchange exchange;

    public BinanceWebSocketCandlestickCallback(Exchange exchange, TradeStrategy tradeStrategy, List<Candlestick> initCandlesticks){
        this.tradeStrategy = tradeStrategy;
        this.exchange = exchange;
        if(initCandlesticks != null && !initCandlesticks.isEmpty()){
            candlesticks.addAll(initCandlesticks);
        }
    }

    @Override
    public void onMessage(BinanceCandlestickEvent message) {
        if(message.getBarFinal()){
            log.info("Candlestick was closed");
            log.info("\t" + message.toString());
            Candlestick candlestick = CandlestickAdapter.convertCandlestickEvent(message);
            candlesticks.add(candlestick);

            TradeAction tradeAction = tradeStrategy.process(candlesticks);

            // TODO: buy sell with async
            switch (tradeAction.getTradeType()){
                case BUY: exchange.buy(candlestick); break;
                case SELL: exchange.sell(candlestick); break;
                default: log.info("\tDo nothing...");
            }
        }
    }

    @Override
    public void onFailure(Throwable cause) {
        log.error(cause.getMessage(), cause);
    }
}
