package com.baeldung.security;

import java.math.BigDecimal;

public class PaymentTransaction implements MergeableModel<PaymentTransaction> {

    private String id;
    private BigDecimal amount;
    private String internalComment;
    private String playerId;
    private String operatorId;

    /*
    public PaymentTransaction(String id, BigDecimal amount, String internalComment) {
        this.id = id;
        this.amount = amount;
        this.internalComment = internalComment;
    }
    */

    public PaymentTransaction() {
    }

    public BigDecimal getAmount() {
        return amount;
    }



    public String getInternalComment() {
        return internalComment;
    }


    public String getId() {
        return id;
    }



    public String getPlayerId() {
        return playerId;
    }

    public String getOperatorId() {
        return operatorId;
    }

    @Override
    public PaymentTransaction mergeWith(PaymentTransaction mergeableModel) {

        PaymentTransaction mergedPlayer = new Builder()
                .internalComment(mergeableModel.getInternalComment() == null ? this.getInternalComment(): mergeableModel.getInternalComment())
                .amount(mergeableModel.getAmount() == null ? this.getAmount(): mergeableModel.getAmount())
                .id(mergeableModel.getId() == null ? this.getId(): mergeableModel.getId())
                .playerId(mergeableModel.getPlayerId() == null ? this.getPlayerId() : mergeableModel.getPlayerId())
                .operatorId(mergeableModel.getOperatorId() == null ? this.getOperatorId() : mergeableModel.getOperatorId())
                .build()
                ;
        return mergedPlayer;
    }


    public static final class Builder {
        private String id;
        private BigDecimal amount;
        private String internalComment;
        private String playerId;
        private String operatorId;

        public Builder() {
        }

        public static Builder aPaymentTransaction() {
            return new Builder();
        }

        public Builder id(String id) {
            this.id = id;
            return this;
        }

        public Builder amount(BigDecimal amount) {
            this.amount = amount;
            return this;
        }

        public Builder internalComment(String internalComment) {
            this.internalComment = internalComment;
            return this;
        }

        public Builder playerId(String playerId) {
            this.playerId = playerId;
            return this;
        }

        public Builder operatorId(String operatorId) {
            this.operatorId = operatorId;
            return this;
        }

        public PaymentTransaction build() {
            PaymentTransaction paymentTransaction = new PaymentTransaction();
            paymentTransaction.id = this.id;
            paymentTransaction.amount = this.amount;
            paymentTransaction.internalComment = this.internalComment;
            paymentTransaction.playerId = this.playerId;
            paymentTransaction.operatorId = this.operatorId;
            return paymentTransaction;
        }
    }
}
