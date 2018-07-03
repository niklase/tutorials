package com.baeldung.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import reactor.core.publisher.Mono;

public abstract class ReactivePermissionEvaluator<T> {


    private Class<T> classOfModel;


    public ReactivePermissionEvaluator(Class<T> classOfModel) {
        this.classOfModel = classOfModel;
    }

    public Class getClassOfModel() {
        return classOfModel;
    }

    public abstract Mono<Boolean> hasPermission(Authentication authentication, T model, Object action);


}
