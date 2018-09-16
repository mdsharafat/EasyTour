package com.example.muhammad.easytour;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.muhammad.easytour.ActivityRegistrationLogin.LoginActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.zip.Inflater;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private Button mLogout;
    private TextView mWelcomeTV;
    private FirebaseAuth firebaseAuth;

    private ImageButton mTravellerProfileIBtn,
            mNearbyIBtn, mEventsIBtn,
            mAllEventsIBtn, mHapppyMomentsIBtn,
            mWeatherIBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //********* Start **************
        firebaseAuth = FirebaseAuth.getInstance();

        if (firebaseAuth.getCurrentUser() == null){
            finish();
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
        }

        FirebaseUser user = firebaseAuth.getCurrentUser();

        //************** Complete ***********

        mWelcomeTV = findViewById(R.id.welcomeTV);
        mLogout = findViewById(R.id.logout);
//        mTravellerProfileIBtn = findViewById(R.id.home_travelerImageIconBTN);
//        mNearbyIBtn = findViewById(R.id.home_nearbyIconBTN);
//        mEventsIBtn = findViewById(R.id.home_eventsIconIBtn);
//        mAllEventsIBtn = findViewById(R.id.home_allEventsIconIBtn);
//        mHapppyMomentsIBtn = findViewById(R.id.main_happyMoments_iconIBtn);
//        mWeatherIBtn = findViewById(R.id.main_weather_iconIBtn);

        mLogout.setOnClickListener(this);

    }

//******************** Start ******************
    @Override
    public void onClick(View view) {
        if (view == mLogout){
            firebaseAuth.signOut();
            finish();
            startActivity(new Intent(MainActivity.this,LoginActivity.class));

        }
    }
    //***************Complete************
}
