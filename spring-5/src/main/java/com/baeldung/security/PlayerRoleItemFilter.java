package com.baeldung.security;

import org.springframework.stereotype.Component;

@Component
public class PlayerRoleItemFilter implements PerRoleFilter<PaymentTransaction> {

    @Override
    public String role() {
        return "ROLE_PLAYER";
    }

    @Override
    public PaymentTransaction filter(PaymentTransaction toBeFiltered, String contextOfRole) {
        PaymentTransaction.Builder builder = new PaymentTransaction.Builder();
        if (contextOfRole.equals(toBeFiltered.getPlayerId())) {
            builder
                    .internalComment(null) // This one is only for internal use
                    .id(toBeFiltered.getId())
                    .amount(toBeFiltered.getAmount())
                    .playerId(toBeFiltered.getPlayerId())
                    .operatorId(toBeFiltered.getOperatorId());
        }
        return builder.build();
    }
}
