package org.hswebframework.example.crud.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.hswebframework.web.api.crud.entity.GenericEntity;
import org.hswebframework.web.crud.generator.Generators;
import org.hswebframework.web.validator.CreateGroup;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Table;
import javax.validation.constraints.Pattern;
import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@Table(name = "minutes")
public class MinutesEntity extends GenericEntity<String> {

    @Override
    @GeneratedValue(generator = Generators.SNOW_FLAKE)
    @Pattern(regexp = "^[0-9a-zA-Z_\\-]+$", message = "ID只能由数字,字母,下划线和中划线组成", groups = CreateGroup.class)
    @Schema(description = "设备ID(只能由数字,字母,下划线和中划线组成)")
    public String getId() {
        return super.getId();
    }

    @Column
    @Schema(description = "量")
    private Date minuteInterval;

}
