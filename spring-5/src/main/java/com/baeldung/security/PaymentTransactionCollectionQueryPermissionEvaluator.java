package com.baeldung.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import reactor.core.publisher.Mono;

public class PaymentTransactionCollectionQueryPermissionEvaluator extends ReactivePermissionEvaluator<PaymentTransactionCollectionQuery> {

    public PaymentTransactionCollectionQueryPermissionEvaluator() {
        super(PaymentTransactionCollectionQuery.class);
    }

    public Mono<Boolean> hasPermission(Authentication authentication, PaymentTransactionCollectionQuery model, Object action) {
        boolean hasPermission = false;
        if ("query".equals(action)) {
            for (GrantedAuthority grantedAuthority : authentication.getAuthorities()) {


                if (grantedAuthority.getAuthority().equals("ROLE_PLAYER") &&
                        ((User) authentication.getPrincipal()).getUsername().equals(model.getPlayerId())) {
                    hasPermission = true;
                } else if (grantedAuthority.getAuthority().equals("ROLE_SUPPORT")) {
                    hasPermission = true;
                }
            }
        }
        return Mono.just(hasPermission);
    }
}
