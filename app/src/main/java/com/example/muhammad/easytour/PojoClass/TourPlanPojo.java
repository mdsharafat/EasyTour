package com.example.muhammad.easytour.PojoClass;

import android.widget.TextView;

public class TourPlanPojo {

    private String tourFrom;
    private String tourTo;
    private String approxBudget;
    private String startingDate;
    private String endingDate;
    private String key;

    public TourPlanPojo() {
    }

    public TourPlanPojo(String startFrom, String endTo, String approxBudget, String startDate, String endDate, String key) {
        this.tourFrom = startFrom;
        this.tourTo = endTo;
        this.approxBudget = approxBudget;
        this.startingDate = startDate;
        this.endingDate = endDate;
        this.key = key;
    }

    public TourPlanPojo(String tourFrom, String tourTo, String approxBudget, String startingDate, String endingDate) {
        this.tourFrom = tourFrom;
        this.tourTo = tourTo;
        this.approxBudget = approxBudget;
        this.startingDate = startingDate;
        this.endingDate = endingDate;

    }




    public String getTourFrom() {
        return tourFrom;
    }

    public String getTourTo() {
        return tourTo;
    }

    public String getStartingDate() {
        return startingDate;
    }

    public String getEndingDate() {
        return endingDate;
    }

    public String getApproxBudget() {
        return approxBudget;
    }

    public String getKey() {
        return key;
    }
}
