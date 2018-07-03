package com.baeldung.security;

import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.*;

@Component
public class TestRepository {


    private Map<String, PaymentTransaction> paymentTransactions = new HashMap();

    public TestRepository() {

        PaymentTransaction paymentTransaction =
                new PaymentTransaction.Builder()
                        .id("1")
                        .amount(new BigDecimal(1090.0))
                        .internalComment("Looks suspect!")
                        .operatorId("A")
                        .playerId("1234567")
                        .build();

        paymentTransactions.put(paymentTransaction.getId(), paymentTransaction);
    }


    public PaymentTransaction get(String itemId) {
        return paymentTransactions.get(itemId);

    }

    public List<PaymentTransaction> getPaymentTransactions(){

        List list = new ArrayList(paymentTransactions.values());
        Collections.sort(list);
        return list;
    }
}
