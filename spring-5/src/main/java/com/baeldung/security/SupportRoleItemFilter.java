package com.baeldung.security;

import org.springframework.stereotype.Component;

@Component
public class SupportRoleItemFilter implements PerRoleFilter<PaymentTransaction> {

    @Override
    public String role() {
        return "ROLE_SUPPORT";
    }

    @Override
    public PaymentTransaction filter(PaymentTransaction toBeFiltered, String contextOfRole) {
        return toBeFiltered;
    }
}
