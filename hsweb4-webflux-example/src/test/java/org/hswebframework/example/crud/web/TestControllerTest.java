package org.hswebframework.example.crud.web;

import org.hswebframework.example.crud.entity.JoinTable;
import org.hswebframework.example.crud.entity.MinutesEntity;
import org.hswebframework.example.crud.entity.TestEntity;
import org.hswebframework.example.crud.enums.TestEnum;
import org.hswebframework.web.crud.configuration.EasyormConfiguration;
import org.hswebframework.web.crud.configuration.R2dbcSqlExecutorConfiguration;
import org.hswebframework.web.starter.jackson.CustomCodecsAutoConfiguration;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.r2dbc.R2dbcAutoConfiguration;
import org.springframework.boot.autoconfigure.r2dbc.R2dbcTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.transaction.TransactionAutoConfiguration;
import org.springframework.boot.autoconfigure.web.reactive.function.client.WebClientAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@WebFluxTest(value = {TestController.class, FeederManualFeedController.class, MinutesController.class})
@ImportAutoConfiguration({
        EasyormConfiguration.class,
        CustomCodecsAutoConfiguration.class,
        R2dbcSqlExecutorConfiguration.class, R2dbcAutoConfiguration.class,
        R2dbcTransactionManagerAutoConfiguration.class, TransactionAutoConfiguration.class,
        WebClientAutoConfiguration.class
})
@ComponentScan("org.hswebframework.example.crud")
class TestControllerTest {

    @Autowired
    private WebTestClient testClient;

    @Test
    void test() {

        TestEntity entity = new TestEntity();
        entity.setId("Test");
        entity.setName("Test");
        entity.setEnumTest(TestEnum.state1);
        entity.setJoinTable(JoinTable.of("Test", "Code"));
        testClient
                .post()
                .uri("/api/test")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(entity)
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .expectBody()
                .jsonPath("$.joinId")
                .isNotEmpty();


        testClient
                .post()
                .uri("/api/test/_query/no-paging")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue("{\"where\":\"joinTable.code is Code\"}")
                .exchange()
                .expectBody()
                .jsonPath("$[0].joinId").isNotEmpty()
                .jsonPath("$[0].joinTable.name").isEqualTo("Test")
                .jsonPath("$[0].joinTable.code").isEqualTo("Code");

        testClient
                .get()
                .uri("/api/test/join-native/_query?where=joinTable.code is Code")
                .exchange()
                .expectBody()
                .consumeWith(res->{
                    System.out.println(new String(res.getResponseBody()));
                })
                .jsonPath("$.data[0].joinTable.name").isEqualTo("Test")
                .jsonPath("$.data[0].joinTable.code").isEqualTo("Code");


    }

    @Test
    void test2() {
        String startTimeStr = "2023-06-07 08:10:03";
        String endTimeStr = "2023-06-07 08:20:00";
        int intervalMinutes = 1;

        List<Date> timeRange = generateTimeRange(startTimeStr, endTimeStr, intervalMinutes);

        // 初始化测试数据
//        timeRange.forEach(time -> {
//            MinutesEntity entity = new MinutesEntity();
//            entity.setMinuteInterval(time);
//            testClient
//                    .post()
//                    .uri("/api/minutes/test")
//                    .contentType(MediaType.APPLICATION_JSON)
//                    .bodyValue(entity)
//                    .exchange()
//                    .expectStatus()
//                    .is2xxSuccessful()
//                    .expectBody();
//
//        });


        testClient
                .post()
                .uri("/api/test2/group-by/_query1")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue("{\"sorts\": [\n" +
                        "        {\n" +
                        "            \"name\": \"minute\",\n" +
                        "            \"order\": \"asc\"\n" +
                        "        }\n" +
                        "    ]}")
                .exchange()
                .expectBody()
                .consumeWith(res->{
                    System.out.println(new String(res.getResponseBody()));
                });

    }


    @Test
    void test3() {
        testClient
                .post()
                .uri("/api/test2/group-by/_query2")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue("{}")
                .exchange()
                .expectBody()
                .consumeWith(res->{
                    System.out.println(new String(res.getResponseBody()));
                });
    }

    public static List<Date> generateTimeRange(String startTimeStr, String endTimeStr, int intervalMinutes) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime startTime = LocalDateTime.parse(startTimeStr, formatter).withSecond(0);
        LocalDateTime endTime = LocalDateTime.parse(endTimeStr, formatter);

        List<Date> timeRange = new ArrayList<>();
        LocalDateTime currentTime = startTime;

        while (currentTime.isBefore(endTime)) {
            ZoneId zoneId = ZoneId.systemDefault();
            ZonedDateTime zonedDateTime = currentTime.atZone(zoneId);
            Instant instant = zonedDateTime.toInstant();
            timeRange.add(Date.from(instant));
            currentTime = currentTime.plusMinutes(intervalMinutes);
        }

        return timeRange;
    }

}