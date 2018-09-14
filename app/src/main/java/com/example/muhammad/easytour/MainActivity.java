package com.example.muhammad.easytour;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity {

    private ImageButton mTravellerProfileIBtn,
            mNearbyIBtn, mEventsIBtn,
            mAllEventsIBtn, mHapppyMomentsIBtn,
            mWeatherIBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTravellerProfileIBtn = findViewById(R.id.home_travelerImageIconBTN);
        mNearbyIBtn = findViewById(R.id.home_nearbyIconBTN);
        mEventsIBtn = findViewById(R.id.home_eventsIconIBtn);
        mAllEventsIBtn = findViewById(R.id.home_allEventsIconIBtn);
        mHapppyMomentsIBtn = findViewById(R.id.main_happyMoments_iconIBtn);
        mWeatherIBtn = findViewById(R.id.main_weather_iconIBtn);
    }
}
