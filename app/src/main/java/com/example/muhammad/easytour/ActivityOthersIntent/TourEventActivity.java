package com.example.muhammad.easytour.ActivityOthersIntent;

import android.content.Intent;
import android.support.annotation.NonNull;
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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class TourEventActivity extends AppCompatActivity {

    private boolean isSignIn = false;
    private boolean isSignout = false;
    private boolean isHome = false;
    private boolean isProfile = false;

    //Firebase
    private FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;

    //RecyclerView
    RecyclerView recyclerView;
    TourEventsAdapter tourEventsAdapter;
    ArrayList<TourPlanPojo> tourPlanPojoList;
    ArrayList<TourPlanPojo> tourPlanShowList;
    DatabaseReference rootReference;
    DatabaseReference userReference;
    DatabaseReference tourEventsReference;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tour_event);

        //RecyclerView
        recyclerView = findViewById(R.id.tourEventsRecyclerViewID);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        tourPlanPojoList = new ArrayList<>();
        tourPlanShowList = new ArrayList<>();
        //FirebaseAuth
        firebaseAuth = FirebaseAuth.getInstance();
        rootReference = FirebaseDatabase.getInstance().getReference();
        firebaseUser = firebaseAuth.getCurrentUser();
        FirebaseUser user = firebaseAuth.getCurrentUser();
        userReference = rootReference.child(firebaseUser.getUid());
        tourEventsReference = userReference.child("tourEvents");
        tourEventsReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                tourPlanShowList.clear();
                tourPlanPojoList.clear();
                if (dataSnapshot.exists()){
                    for (DataSnapshot d : dataSnapshot.getChildren()){
                        TourPlanPojo tourPlanPojo = d.getValue(TourPlanPojo.class);
                        tourPlanPojoList.add(tourPlanPojo);
                    }
                    for (TourPlanPojo t : tourPlanPojoList){
                        tourPlanShowList.add(new TourPlanPojo(t.getTourFrom(),t.getTourTo(),t.getApproxBudget(),t.getStartingDate(),t.getEndingDate()));
                    }
                    tourEventsAdapter = new TourEventsAdapter(getApplicationContext(), tourPlanShowList);
                    recyclerView.setAdapter(tourEventsAdapter);
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });





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
