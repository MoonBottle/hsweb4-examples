package org.hswebframework.example.crud.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FeederManualFeedSumDTO {

    private String minuteInterval;
    private BigDecimal sumWater;
    private BigDecimal sumFeed;

}
