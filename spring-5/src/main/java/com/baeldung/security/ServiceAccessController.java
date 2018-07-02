package com.baeldung.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Component
public class ServiceAccessController {

    private List<PerRoleFilter<?>> filters;
    private List<ReactivePermissionEvaluator> reactivePermissionEvaluators;
    private Map<Class, ReactivePermissionEvaluator> reactivePermissionEvaluatorMap = new HashMap<>();

    public ServiceAccessController(
            @Autowired List<PerRoleFilter<?>> filters,
            @Autowired List<ReactivePermissionEvaluator> reactivePermissionEvaluators) {

        this.filters = filters;
        this.reactivePermissionEvaluators = reactivePermissionEvaluators;

        for (ReactivePermissionEvaluator reactivePermissionEvaluator: reactivePermissionEvaluators) {
            reactivePermissionEvaluatorMap.put(reactivePermissionEvaluator.getClassOfModel(), reactivePermissionEvaluator);
        }
    }


    public Mono<Boolean> authorizeQuery(Object queryModel) {

        ReactivePermissionEvaluator reactivePermissionEvaluator = reactivePermissionEvaluatorMap.get(queryModel.getClass());
        Mono<Boolean> result = ReactiveSecurityContextHolder.getContext().flatMap(context -> reactivePermissionEvaluator.hasPermission(context.getAuthentication(), queryModel, "query"));
        return result;
    }

}
