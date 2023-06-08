package org.hswebframework.example.crud.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.hswebframework.ezorm.rdb.mapping.annotation.ColumnType;
import org.hswebframework.ezorm.rdb.mapping.annotation.Comment;
import org.hswebframework.ezorm.rdb.mapping.annotation.EnumCodec;
import org.hswebframework.web.api.crud.entity.GenericEntity;
import org.hswebframework.web.crud.generator.Generators;
import org.hswebframework.web.validator.CreateGroup;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Index;
import javax.persistence.Table;
import javax.validation.constraints.Pattern;
import java.math.BigDecimal;

@Getter
@Setter
@Table(name = "feeder_manual_feed")
public class FeederManualFeedEntity extends GenericEntity<String> {

    @Override
    @GeneratedValue(generator = Generators.SNOW_FLAKE)
    @Pattern(regexp = "^[0-9a-zA-Z_\\-]+$", message = "ID只能由数字,字母,下划线和中划线组成", groups = CreateGroup.class)
    @Schema(description = "设备ID(只能由数字,字母,下划线和中划线组成)")
    public String getId() {
        return super.getId();
    }

    @Column(length = 64)
    @Schema(description = "设备ID")
    private String deviceId;

    @Column
    @Schema(description = "量")
    private BigDecimal measure;

    @Column
    @Schema(description = "类型")
    private String type;

    @Column(length = 64)
    @Schema(description = "时间")
    private Long date;

    @Column(updatable = false)
    @Schema(description = "创建时间(只读)")
    private Long createTime;

}
