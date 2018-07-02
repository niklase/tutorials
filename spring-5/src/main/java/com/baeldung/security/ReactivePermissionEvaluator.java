package com.baeldung.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import reactor.core.publisher.Mono;

public class ReactivePermissionEvaluator<T> {


    private Class<T> classOfModel;

    public ReactivePermissionEvaluator(Class<T> classOfModel) {
        this.classOfModel = classOfModel;
    }

    public Class getClassOfModel() {
        return classOfModel;
    }

    public Mono<Boolean> hasPermission(Authentication authentication, Object model, Object action) {
        boolean hasPermission = false;
        if (model.getClass() == PaymentTransactionCollectionQuery.class && "query".equals(action)) {
            for (GrantedAuthority grantedAuthority : authentication.getAuthorities()) {


                if (grantedAuthority.getAuthority().equals("ROLE_PLAYER") &&
                        ((User) authentication.getPrincipal()).getUsername().equals(((PaymentTransactionCollectionQuery) model).getId())) {
                    hasPermission = true;
                } else if (grantedAuthority.getAuthority().equals("ROLE_SUPPORT")) {
                    hasPermission = true;
                }
            }
        }
        return Mono.just(hasPermission);
    }


}
