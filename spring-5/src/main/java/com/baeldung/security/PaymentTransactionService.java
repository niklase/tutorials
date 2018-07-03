package com.baeldung.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class PaymentTransactionService extends BaseService<PaymentTransaction, PaymentTransactionItemQuery, PaymentTransactionCollection, PaymentTransactionCollectionQuery> {


    public PaymentTransactionService(@Autowired List<PerRoleFilter<PaymentTransaction>> itemFilters,
                                     @Autowired List<PerRoleFilter<PaymentTransactionCollection>> collectionFilters,
                                     @Autowired ServiceAccessController serviceAccessController,
                                     @Autowired TestRepository repository) {
        super(itemFilters, collectionFilters, serviceAccessController, repository);
    }

    protected Mono<Response<PaymentTransaction>> readItemUnprotected(PaymentTransactionItemQuery itemQuery) {

        PaymentTransaction paymentTransaction = getRepository().get(itemQuery.getId());
        return Mono.just(new Response(paymentTransaction, 200));
    }

    protected Mono<Response<PaymentTransactionCollection>> readCollectionUnprotected(PaymentTransactionItemQuery itemQuery) {

        PaymentTransaction paymentTransaction = getRepository().get(itemQuery.getId());
        return Mono.just(new Response(paymentTransaction, 200));
    }

    @Override
    protected Mono<Response<PaymentTransactionCollection>> readCollectionUnprotected(PaymentTransactionCollectionQuery paymentTransactionCollectionQuery) {


        List<PaymentTransaction> paymentTransactionList = getRepository().getPaymentTransactions();

        PaymentTransactionCollection paymentTransactionCollection = new PaymentTransactionCollection(paymentTransactionList);

        return Mono.just(new Response(paymentTransactionCollection, 200));

    }
}
