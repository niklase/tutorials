package com.baeldung.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import reactor.core.publisher.Mono;

public class PaymentTransactionItemQueryPermissionEvaluator extends ReactivePermissionEvaluator<PaymentTransactionItemQuery> {

    private TestRepository repository;

    public PaymentTransactionItemQueryPermissionEvaluator(@Autowired TestRepository repository) {
        super(PaymentTransactionItemQuery.class);
        this.repository = repository;
    }

    public Mono<Boolean> hasPermission(Authentication authentication, PaymentTransactionItemQuery query, Object action) {
        boolean hasPermission = false;
        if ("query".equals(action)) {
            for (GrantedAuthority grantedAuthority : authentication.getAuthorities()) {

                if (grantedAuthority.getAuthority().equals("ROLE_PLAYER")){

                    // TODO: blocking style - make reactive
                    PaymentTransaction paymentTransaction = repository.get(query.getId());
                    hasPermission = paymentTransaction != null && ((User) authentication.getPrincipal()).getUsername().equals(paymentTransaction.getPlayerId());
                } else if (grantedAuthority.getAuthority().equals("ROLE_SUPPORT")) {
                    hasPermission = true;
                }
            }
        }
        return Mono.just(hasPermission);
    }
}
