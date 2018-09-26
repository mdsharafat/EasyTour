package com.example.muhammad.easytour.PojoClass;

import java.util.Map;

public class AddExpensePojo {

    private String mExpenseDetails;
    private String mExpenseAmount;
    private String date;
    private String time;
    private String key;

    public AddExpensePojo() {
    }

    public AddExpensePojo(String mExpenseDetails, String mExpenseAmount) {
        this.mExpenseDetails = mExpenseDetails;
        this.mExpenseAmount = mExpenseAmount;
    }

    public AddExpensePojo(String mExpenseDetails, String mExpenseAmount, String date) {
        this.mExpenseDetails = mExpenseDetails;
        this.mExpenseAmount = mExpenseAmount;
        this.date = date;
    }

    public AddExpensePojo(String mExpenseDetails, String mExpenseAmount, String date, String time) {
        this.mExpenseDetails = mExpenseDetails;
        this.mExpenseAmount = mExpenseAmount;
        this.date = date;
        this.time = time;
    }

    public AddExpensePojo(String mExpenseDetails, String mExpenseAmount, String date, String time, String key) {
        this.mExpenseDetails = mExpenseDetails;
        this.mExpenseAmount = mExpenseAmount;
        this.date = date;
        this.time = time;
        this.key = key;
    }

    public String getmExpenseDetails() {
        return mExpenseDetails;
    }

    public String getmExpenseAmount() {
        return mExpenseAmount;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public String getKey() {
        return key;
    }
}
