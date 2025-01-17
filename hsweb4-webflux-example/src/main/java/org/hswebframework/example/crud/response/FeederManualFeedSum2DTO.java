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
public class FeederManualFeedSum2DTO {

    private String date;
    private String minutes;
    private BigDecimal measure;
    private String type;

}
