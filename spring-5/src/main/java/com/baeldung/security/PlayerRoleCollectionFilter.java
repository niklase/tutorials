package com.baeldung.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class PlayerRoleCollectionFilter implements PerRoleFilter<PaymentTransactionCollection> {

    private PlayerRoleItemFilter playerRoleItemFilter;

    @Override
    public String role() {
        return "ROLE_PLAYER";
    }

    public PlayerRoleCollectionFilter(@Autowired PlayerRoleItemFilter playerRoleItemFilter) {
        this.playerRoleItemFilter = playerRoleItemFilter;
    }

    @Override
    public PaymentTransactionCollection filter(PaymentTransactionCollection toBeFiltered, String contextOfRole) {

        List<PaymentTransaction> paymentTransactionList = new ArrayList<>();
        for (PaymentTransaction paymentTransaction: toBeFiltered.getValue()) {
            paymentTransactionList.add(playerRoleItemFilter.filter(paymentTransaction, contextOfRole));
        }
        return new PaymentTransactionCollection(paymentTransactionList);
    }
}
