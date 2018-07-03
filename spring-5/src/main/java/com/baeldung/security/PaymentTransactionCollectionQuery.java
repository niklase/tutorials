package com.baeldung.security;

public class PaymentTransactionCollectionQuery {
    private String playerId;
    private String operatorId;

    private PaymentTransactionCollectionQuery() {
    }

    public PaymentTransactionCollectionQuery(String playerId, String operatorId) {
        this.playerId = playerId;
        this.operatorId = operatorId;
    }

    public String getPlayerId() {
        return playerId;
    }

    public String getOperatorId() {
        return operatorId;
    }
}
