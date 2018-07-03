package com.baeldung.security;

import java.util.ArrayList;
import java.util.List;

public class PaymentTransactionCollection implements MergeableModel<PaymentTransactionCollection>{

    private List<PaymentTransaction> value;

    public PaymentTransactionCollection(List<PaymentTransaction> value) {
        this.value = value;
    }

    public List<PaymentTransaction> getValue() {
        return value;
    }

    @Override
    public PaymentTransactionCollection mergeWith(PaymentTransactionCollection mergeableModel) {

        List<PaymentTransaction> mergedList = new ArrayList<>();
        for (int i = 0; i < mergeableModel.getValue().size(); i++) {

            PaymentTransaction other = mergeableModel.getValue().get(i);
            PaymentTransaction my = getValue().get(i);
            mergedList.add(my.mergeWith(other));
        }
        return new PaymentTransactionCollection(mergedList);
    }
}
