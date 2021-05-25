package cc.magickiat.crypto.strategy;

import cc.magickiat.crypto.common.dto.Candlestick;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CandlestickTestUtil {

    public static List<Candlestick> readCandleStick(String filePath) throws Exception {
        List<Candlestick> candlesticks = new ArrayList<>();
        Stream<String> lines = Files.lines(Path.of(CandlestickTestUtil.class.getResource(filePath).toURI()));
        List<String> closedPrices = lines.map(e -> e.split(",")[4]).collect(Collectors.toList());
        for (String closedPrice : closedPrices) {
            Candlestick candlestick = new Candlestick();
            candlestick.setClose(closedPrice);
            candlesticks.add(candlestick);
        }

        return candlesticks;
    }
}
