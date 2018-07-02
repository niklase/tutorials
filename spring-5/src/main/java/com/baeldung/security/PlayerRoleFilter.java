package com.baeldung.security;

import org.springframework.stereotype.Component;

@Component
public class PlayerRoleFilter implements PerRoleFilter<PaymentTransaction> {

    @Override
    public String role() {
        return "ROLE_PLAYER";
    }

    @Override
    public PaymentTransaction filter(PaymentTransaction toBeFiltered, String contextOfRole) {
        PaymentTransaction filteredModel = new PaymentTransaction();
        if (contextOfRole.equals(toBeFiltered.getId())) {
            filteredModel.setInternalComment(null);
            filteredModel.setId(toBeFiltered.getId());
            filteredModel.setAmount(toBeFiltered.getAmount());
        }
        return filteredModel;
    }
}
