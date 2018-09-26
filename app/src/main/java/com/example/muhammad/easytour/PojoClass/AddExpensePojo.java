package com.example.muhammad.easytour.PojoClass;

import java.util.Map;

public class AddExpensePojo {

    private String mExpenseDetails;
    private String mExpenseAmount;
    private String key;

    public AddExpensePojo() {
    }


    public AddExpensePojo(String mExpenseDetails, String mExpenseAmount) {
        this.mExpenseDetails = mExpenseDetails;
        this.mExpenseAmount = mExpenseAmount;
    }

    public AddExpensePojo(String mExpenseDetails, String mExpenseAmount, String key) {
        this.mExpenseDetails = mExpenseDetails;
        this.mExpenseAmount = mExpenseAmount;
        this.key = key;
    }

    public String getmExpenseDetails() {
        return mExpenseDetails;
    }

    public String getmExpenseAmount() {
        return mExpenseAmount;
    }



    public String getKey() {
        return key;
    }
}
