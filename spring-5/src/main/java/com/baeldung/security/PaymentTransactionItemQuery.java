package com.baeldung.security;

public class PaymentTransactionItemQuery {
    private String id;

    public PaymentTransactionItemQuery(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
