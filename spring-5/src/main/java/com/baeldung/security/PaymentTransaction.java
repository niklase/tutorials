package com.baeldung.security;

import java.math.BigDecimal;

public class PaymentTransaction implements MergeableModel<PaymentTransaction> {

    private String id;
    private BigDecimal amount;
    private String internalComment;

    public PaymentTransaction(String id, BigDecimal amount, String internalComment) {
        this.id = id;
        this.amount = amount;
        this.internalComment = internalComment;
    }

    public PaymentTransaction() {
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getInternalComment() {
        return internalComment;
    }

    public void setInternalComment(String internalComment) {
        this.internalComment = internalComment;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public PaymentTransaction mergeWith(PaymentTransaction mergeableModel) {

        PaymentTransaction mergedPlayer = new PaymentTransaction();

        mergedPlayer.setInternalComment(mergeableModel.getInternalComment() == null ? this.getInternalComment(): mergeableModel.getInternalComment());
        mergedPlayer.setAmount(mergeableModel.getAmount() == null ? this.getAmount(): mergeableModel.getAmount());
        mergedPlayer.setId(mergeableModel.getId() == null ? this.getId(): mergeableModel.getId());
        return mergedPlayer;
    }
}
