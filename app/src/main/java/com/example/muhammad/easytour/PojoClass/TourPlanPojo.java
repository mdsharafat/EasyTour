package com.example.muhammad.easytour.PojoClass;

import android.widget.TextView;

public class TourPlanPojo {

    private String tourFrom;
    private String tourTo;
    private String startingDate;
    private String endingDate;
    private double approxBudget;


    public TourPlanPojo() {
    }

    public TourPlanPojo(String tourFrom, String tourTo, String startingDate, String endingDate, double approxBudget) {
        this.tourFrom = tourFrom;
        this.tourTo = tourTo;
        this.startingDate = startingDate;
        this.endingDate = endingDate;
        this.approxBudget = approxBudget;
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

    public double getApproxBudget() {
        return approxBudget;
    }
}
