package com.example.muhammad.easytour.ActivityOthersIntent;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.muhammad.easytour.ActivityRegistrationLogin.LoginActivity;
import com.example.muhammad.easytour.Adapter.TourEventsAdapter;
import com.example.muhammad.easytour.MainActivity;
import com.example.muhammad.easytour.PojoClass.TourPlanPojo;
import com.example.muhammad.easytour.R;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

public class TourEventActivity extends AppCompatActivity {

    private boolean isSignIn = false;
    private boolean isSignout = false;
    private boolean isHome = false;
    private boolean isProfile = false;

    private FirebaseAuth firebaseAuth;

    //RecyclerView
    RecyclerView recyclerView;
    TourEventsAdapter tourEventsAdapter;
    List<TourPlanPojo> tourPlanPojoList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tour_event);

        //FirebaseAuth
        firebaseAuth = FirebaseAuth.getInstance();

        //RecyclerView
        recyclerView = findViewById(R.id.tourEventsRecyclerViewID);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        tourPlanPojoList = new ArrayList<>();
        tourPlanPojoList.add(new TourPlanPojo("Dhaka","Rangamati","10/8/2018","20/8/2018",200000));
        tourPlanPojoList.add(new TourPlanPojo("Dhaka","Sylhet","1/8/2017","20/7/2018",300000));
        tourPlanPojoList.add(new TourPlanPojo("Dhaka","Chittagong","21/8/2018","25/8/2018",150000));
        tourPlanPojoList.add(new TourPlanPojo("Dhaka","Coxs Bazar","22/8/2018","20/8/2018",360000));

        tourEventsAdapter = new TourEventsAdapter(this, tourPlanPojoList);
        recyclerView.setAdapter(tourEventsAdapter);



    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater Inflater = getMenuInflater();
        Inflater.inflate(R.menu.menu_item, menu);

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
        HomeItem.setVisible(true);
        ProfileItem.setVisible(true);

        return true;

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_home:
                isHome = false;
                startActivity(new Intent(this, MainActivity.class));
                break;
            case R.id.item_profile:
                isProfile = false;
                startActivity(new Intent(this, ProfileActivity.class));
                break;
            case R.id.item_logout:
                firebaseAuth.signOut();
                finish();
                startActivity(new Intent(this, LoginActivity.class));
                break;
        }

        return super.onOptionsItemSelected(item);

    }
}
