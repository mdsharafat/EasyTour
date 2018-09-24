package com.example.muhammad.easytour;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

import com.example.muhammad.easytour.ActivityGoogleMap.MapsActivity;
import com.example.muhammad.easytour.ActivityOthersIntent.AddExpenseActivity;
import com.example.muhammad.easytour.ActivityOthersIntent.AddMomentActivity;
import com.example.muhammad.easytour.ActivityOthersIntent.ExpenseListActivity;
import com.example.muhammad.easytour.ActivityOthersIntent.ProfileActivity;
import com.example.muhammad.easytour.ActivityOthersIntent.TourEventActivity;
import com.example.muhammad.easytour.ActivityOthersIntent.TourPlanActivity;
import com.example.muhammad.easytour.ActivityOthersIntent.ViewMomentActivity;
import com.example.muhammad.easytour.ActivityOthersIntent.WeatherActivity;
import com.example.muhammad.easytour.ActivityRegistrationLogin.LoginActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity implements View.OnClickListener
{


    private FirebaseAuth firebaseAuth;

    //Card View Variable
    private CardView mProfileCardView;
    private CardView mLocationCardView;
    private CardView mWeatherCardView;
    private CardView mAddMomentsCardView;
    private CardView mTourPlanCardView;
    private CardView mViewTourEventsCardView;
    private CardView mAddExpenseCardView;
    private CardView mExpenseListCardView;
    private CardView mViewMomentsCardView;

    //        mLocationCardView, mWeatherCardView,
     //       mMomentsCardViewID, mTourPlanCardView,
       //     mEventsICardView, mBudgetCardView,
         //   mExpenseList, mAboutAppCardView;




    private boolean isSignIn = false;
    private boolean isSignout = false;
    private boolean isHome = false;
    private boolean isProfile = false;

    private ImageButton mTravellerProfileIBtn,
            mNearbyIBtn, mEventsIBtn,
            mAllEventsIBtn, mHapppyMomentsIBtn,
            mWeatherIBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mProfileCardView = findViewById(R.id.main_profile_cardView);
        mLocationCardView = findViewById(R.id.main_location_cardView);
        mWeatherCardView = findViewById(R.id.main_weather_cardView);
        mAddMomentsCardView = findViewById(R.id.main_add_moments_cardview);
        mTourPlanCardView = findViewById(R.id.main_tour_plan_cardView);
        mViewTourEventsCardView = findViewById(R.id.main_view_tour_events_cardView);
        mAddExpenseCardView = findViewById(R.id.main_add_expense_cardView);
        mExpenseListCardView = findViewById(R.id.main_expense_list_cardView);
        mViewMomentsCardView = findViewById(R.id.main_view_moments_cardView);

        mProfileCardView.setOnClickListener(this);
        mLocationCardView.setOnClickListener(this);
        mWeatherCardView.setOnClickListener(this);
        mAddMomentsCardView.setOnClickListener(this);
        mTourPlanCardView.setOnClickListener(this);
        mViewTourEventsCardView.setOnClickListener(this);
        mAddExpenseCardView.setOnClickListener(this);
        mExpenseListCardView.setOnClickListener(this);
        mViewMomentsCardView.setOnClickListener(this);



        //********* Start **************
        firebaseAuth = FirebaseAuth.getInstance();

        if (firebaseAuth.getCurrentUser() == null)
        {
            finish();
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
        }

        FirebaseUser user = firebaseAuth.getCurrentUser();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_item, menu);
        return true;
    }


    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {

        MenuItem SignInItem = menu.findItem(R.id.item_login);
        MenuItem SignOutItem = menu.findItem(R.id.item_logout);
        MenuItem HomeItem = menu.findItem(R.id.item_home);
        MenuItem ProfileItem = menu.findItem(R.id.item_profile);

        SignOutItem.setVisible(true);
        SignInItem.setVisible(false);
        HomeItem.setVisible(false);
        ProfileItem.setVisible(true);

        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {

        switch (item.getItemId())
        {
            case R.id.item_profile:
                startActivity(new Intent(MainActivity.this,ProfileActivity.class));
                break;
            case R.id.item_logout:
                firebaseAuth.signOut();
                finish();
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view)
    {
        if (view == mProfileCardView )
        {
            startActivity(new Intent(MainActivity.this, ProfileActivity.class));
        }
        if (view == mLocationCardView)
        {
            startActivity(new Intent(MainActivity.this, MapsActivity.class));
        }
        if (view == mWeatherCardView)
        {
            startActivity(new Intent(MainActivity.this, WeatherActivity.class));
        }
        if (view == mAddMomentsCardView)
        {
            startActivity(new Intent(MainActivity.this, AddMomentActivity.class));
        }
        if (view == mTourPlanCardView)
        {
            startActivity(new Intent(MainActivity.this, TourPlanActivity.class));
        }
        if (view == mViewTourEventsCardView)
        {
            startActivity(new Intent(MainActivity.this, TourEventActivity.class));
        }
        if (view == mAddExpenseCardView)
        {
            startActivity(new Intent(MainActivity.this, AddExpenseActivity.class));
        }
        if (view == mExpenseListCardView)
        {
            startActivity(new Intent(MainActivity.this, ExpenseListActivity.class));
        }
        if (view == mViewMomentsCardView)
        {
            startActivity(new Intent(MainActivity.this, ViewMomentActivity.class));
        }

    }
}
