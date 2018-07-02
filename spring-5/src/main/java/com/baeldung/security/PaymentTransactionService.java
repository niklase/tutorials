package com.baeldung.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.List;

@Service
public class PaymentTransactionService {

    private ResponseFilter<PaymentTransaction> responseFilter;
    private ServiceAccessController serviceAccessController;



    public PaymentTransactionService(@Autowired List<PerRoleFilter<PaymentTransaction>> filters, @Autowired ServiceAccessController serviceAccessController) {
        responseFilter = new ResponseFilter<>(filters);
        this.serviceAccessController = serviceAccessController;
    }

    public Mono<Response<PaymentTransaction>> getItem(PaymentTransactionCollectionQuery itemQuery) {

        Mono<Boolean> hasQueryPermissionMono = serviceAccessController.authorizeQuery(itemQuery);

        return hasQueryPermissionMono.flatMap(hasQueryPermission ->
                !hasQueryPermission ?
                        createErrorResponse():
                        filterResponse(performGetItemBusinessLogic(itemQuery)));


    }

    private Mono<Response<PaymentTransaction>> createErrorResponse(){
        return Mono.just(new Response(PaymentTransaction.class, "Query permission error...", 403));
    }

    private Mono<Response<PaymentTransaction>> filterResponse(Mono<Response<PaymentTransaction>> businessResponse ) {

        Mono<PaymentTransaction> filteredBodyMono = businessResponse.flatMap(response -> responseFilter.filterByAuthorization(response.getBody()));

        Mono<Response<PaymentTransaction>> result =  filteredBodyMono.map(r -> new Response(r, 200));

        return result;
    }


    private Mono<Response<PaymentTransaction>> performGetItemBusinessLogic(PaymentTransactionCollectionQuery itemQuery) {
        return Mono.just(new Response(new PaymentTransaction("1234567", new BigDecimal("10980"), "This transaction looks fishy"), 200));
    }



}
