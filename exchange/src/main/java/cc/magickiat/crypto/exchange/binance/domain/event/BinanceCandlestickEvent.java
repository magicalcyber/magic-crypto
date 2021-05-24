package cc.magickiat.crypto.exchange.binance.domain.event;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;
import lombok.ToString;

@JsonDeserialize(using = BinanceCandlestickEventDeserializer.class)
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@ToString
public class BinanceCandlestickEvent {
    private String eventType;

    private long eventTime;

    private String symbol;

    private Long openTime;

    private String open;

    private String high;

    private String low;

    private String close;

    private String volume;

    private Long closeTime;

    private String intervalId;

    private Long firstTradeId;

    private Long lastTradeId;

    private String quoteAssetVolume;

    private Long numberOfTrades;

    private String takerBuyBaseAssetVolume;

    private String takerBuyQuoteAssetVolume;

    private Boolean barFinal;
}
