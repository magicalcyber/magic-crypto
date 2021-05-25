package cc.magickiat.crypto.common.dto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class Candlestick {
    private String open;
    private String high;
    private String low;
    private String close;
    private Boolean barFinal;
    private Long closeTime;
}
