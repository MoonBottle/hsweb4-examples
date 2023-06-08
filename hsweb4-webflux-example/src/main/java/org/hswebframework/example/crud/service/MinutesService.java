package org.hswebframework.example.crud.service;

import lombok.AllArgsConstructor;
import org.hswebframework.example.crud.entity.JoinTable;
import org.hswebframework.example.crud.entity.MinutesEntity;
import org.hswebframework.example.crud.entity.TestEntity;
import org.hswebframework.ezorm.rdb.mapping.ReactiveRepository;
import org.hswebframework.web.crud.events.EntityPrepareCreateEvent;
import org.hswebframework.web.crud.service.GenericReactiveCrudService;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
public class MinutesService extends GenericReactiveCrudService<MinutesEntity, String> {


}
