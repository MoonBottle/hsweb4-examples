package org.hswebframework.example.crud.web;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.hswebframework.example.crud.entity.JoinTable;
import org.hswebframework.example.crud.entity.MinutesEntity;
import org.hswebframework.example.crud.entity.TestEntity;
import org.hswebframework.example.crud.enums.TestEnum;
import org.hswebframework.example.crud.service.MinutesService;
import org.hswebframework.example.crud.service.TestService;
import org.hswebframework.web.api.crud.entity.PagerResult;
import org.hswebframework.web.api.crud.entity.QueryParamEntity;
import org.hswebframework.web.crud.query.QueryHelper;
import org.hswebframework.web.crud.web.reactive.ReactiveServiceCrudController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@AllArgsConstructor
@RestController
@RequestMapping("/api/minutes/test")
@Tag(name = "简单测试")
public class MinutesController implements ReactiveServiceCrudController<MinutesEntity, String> {

    @Getter
    private final MinutesService service;

}
