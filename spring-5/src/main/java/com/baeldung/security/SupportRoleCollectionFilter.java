package com.baeldung.security;

import org.springframework.stereotype.Component;

@Component
public class SupportRoleCollectionFilter implements PerRoleFilter<PaymentTransactionCollection> {

    @Override
    public String role() {
        return "ROLE_SUPPORT";
    }

    @Override
    public PaymentTransactionCollection filter(PaymentTransactionCollection toBeFiltered, String contextOfRole) {
        return toBeFiltered;
    }
}
