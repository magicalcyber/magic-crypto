package cc.magickiat.crypto.exchange.binance.domain.event;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;

public class BinanceCandlestickEventDeserializer extends JsonDeserializer {
    @Override
    public Object deserialize(JsonParser jp, DeserializationContext ctx) throws IOException {
        ObjectCodec oc = jp.getCodec();
        JsonNode node = oc.readTree(jp);

        BinanceCandlestickEvent candlestickEvent = new BinanceCandlestickEvent();

        // Parse header
        candlestickEvent.setEventType(node.get("e").asText());
        candlestickEvent.setEventTime(node.get("E").asLong());
        candlestickEvent.setSymbol(node.get("s").asText());

        // Parse candlestick data
        JsonNode candlestickNode = node.get("k");
        candlestickEvent.setOpenTime(candlestickNode.get("t").asLong());
        candlestickEvent.setCloseTime(candlestickNode.get("T").asLong());
        candlestickEvent.setIntervalId(candlestickNode.get("i").asText());
        candlestickEvent.setFirstTradeId(candlestickNode.get("f").asLong());
        candlestickEvent.setLastTradeId(candlestickNode.get("L").asLong());
        candlestickEvent.setOpen(candlestickNode.get("o").asText());
        candlestickEvent.setClose(candlestickNode.get("c").asText());
        candlestickEvent.setHigh(candlestickNode.get("h").asText());
        candlestickEvent.setLow(candlestickNode.get("l").asText());
        candlestickEvent.setVolume(candlestickNode.get("v").asText());
        candlestickEvent.setNumberOfTrades(candlestickNode.get("n").asLong());
        candlestickEvent.setBarFinal(candlestickNode.get("x").asBoolean());
        candlestickEvent.setQuoteAssetVolume(candlestickNode.get("q").asText());
        candlestickEvent.setTakerBuyBaseAssetVolume(candlestickNode.get("V").asText());
        candlestickEvent.setTakerBuyQuoteAssetVolume(candlestickNode.get("Q").asText());

        return candlestickEvent;

    }
}
