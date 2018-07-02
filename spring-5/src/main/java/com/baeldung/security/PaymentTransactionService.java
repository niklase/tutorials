package com.baeldung.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.List;

@Service
public class PaymentTransactionService {

    private List<PerRoleFilter<PaymentTransaction>> filters;
    private ResponseFilter<PaymentTransaction> responseFilter;

    public PaymentTransactionService(@Autowired List<PerRoleFilter<PaymentTransaction>> filters) {
        this.filters = filters;
        responseFilter = new ResponseFilter<>(filters);
    }

    @PreAuthorize("hasPermission(#itemQuery, 'query')")
    //@PreAuthorize("hasRole('SUPPORT')")
    public Mono<PaymentTransaction> getItem(PaymentTransactionCollectionQuery itemQuery) {

        // Business logic
        PaymentTransaction player = new PaymentTransaction("1234567", new BigDecimal("10980"), "This transaction looks fishy");

        return responseFilter.filterByAuthorization(player);
    }

}
