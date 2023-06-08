package org.hswebframework.example.crud.web;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hswebframework.example.crud.entity.FeederManualFeedEntity;
import org.hswebframework.example.crud.entity.TestEntity;
import org.hswebframework.example.crud.response.FeederManualFeedSum2DTO;
import org.hswebframework.example.crud.response.FeederManualFeedSumDTO;
import org.hswebframework.example.crud.service.FeederManualFeedService;
import org.hswebframework.web.api.crud.entity.QueryParamEntity;
import org.hswebframework.web.authorization.annotation.Authorize;
import org.hswebframework.web.authorization.annotation.QueryAction;
import org.hswebframework.web.authorization.annotation.Resource;
import org.hswebframework.web.crud.query.QueryHelper;
import org.hswebframework.web.crud.service.ReactiveCrudService;
import org.hswebframework.web.crud.web.reactive.ReactiveServiceCrudController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/test2")
@Slf4j
public class FeederManualFeedController implements ReactiveServiceCrudController<FeederManualFeedEntity, String> {

    private final QueryHelper queryHelper;
    private final FeederManualFeedService feederManualFeedService;

    @Override
    public ReactiveCrudService<FeederManualFeedEntity, String> getService() {
        return feederManualFeedService;
    }

    @PostMapping({"/group-by/_query1"})
    @QueryAction
    public Flux<FeederManualFeedSumDTO> testGroupBy1(@RequestBody QueryParamEntity query) {
        return queryHelper.select(
                        "SELECT to_char(m.minute_interval, 'YYYY-MM-DD HH24:MI:SS') AS minute,\n" +
                                "       COALESCE(SUM(f.measure), 0)                         AS sum_water,\n" +
                                "       COALESCE(SUM(f2.measure), 0)                        AS sum_feed\n" +
                                "FROM minutes m\n" +
                                "         LEFT JOIN feeder_manual_feed f ON to_timestamp(f.date / 1000) AT TIME ZONE 'Asia/Shanghai' >= m.minute_interval\n" +
                                "    AND to_timestamp(f.date / 1000) AT TIME ZONE 'Asia/Shanghai' < m.minute_interval + '1 minute'::interval\n" +
                                "    AND f.type = 'water'\n" +
                                "         LEFT JOIN feeder_manual_feed f2\n" +
                                "                   ON to_timestamp(f2.date / 1000) AT TIME ZONE 'Asia/Shanghai' >= m.minute_interval\n" +
                                "                       AND to_timestamp(f2.date / 1000) AT TIME ZONE 'Asia/Shanghai' < m.minute_interval + '1 minute'::interval\n" +
                                "                       AND f2.type = 'feed'\n" +
                                "GROUP BY minute",
                        FeederManualFeedSumDTO::new)
                .where(query)
                .fetch();
    }

    @PostMapping({"/group-by/_query2"})
    @QueryAction
    public Flux<FeederManualFeedSum2DTO> testGroupBy2(@RequestBody QueryParamEntity query) {
        return queryHelper.select(
                        "select to_char(to_timestamp(date / 1000) AT TIME ZONE 'Asia/Shanghai', 'YYYY-MM-DD HH24:MI:00') AS date,\n" +
                                "to_char(to_timestamp(date / 1000) AT TIME ZONE 'Asia/Shanghai', 'YYYY-MM-DD HH24:MI:00') AS minutes,\n" +
                                "       SUM(measure)                                                                             AS measure,\n" +
                                "       type                                                                             AS type\n" +
                                "from feeder_manual_feed\n" +
                                "GROUP BY date, type",
                        FeederManualFeedSum2DTO::new)
                .where(query)
                .fetch();
    }

}
