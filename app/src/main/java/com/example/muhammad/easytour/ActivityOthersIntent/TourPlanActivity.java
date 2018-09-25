package com.example.muhammad.easytour.ActivityOthersIntent;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.example.muhammad.easytour.ActivityRegistrationLogin.LoginActivity;
import com.example.muhammad.easytour.MainActivity;
import com.example.muhammad.easytour.PojoClass.TourPlanPojo;
import com.example.muhammad.easytour.R;
import com.firebase.ui.auth.data.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;

public class TourPlanActivity extends AppCompatActivity {


    //Firebase Variable
    private FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    DatabaseReference rootReference;
    DatabaseReference userReference;
    DatabaseReference tourEventsReference;


    private boolean isSignIn = false;
    private boolean isSignout = false;
    private boolean isHome = false;
    private boolean isProfile = false;

    //Tour Plan Edit Text

    private int mYear;
    private int mMonth;
    private int mDay;

    //Instance Variable
    private EditText mStartingFrom;
    private EditText mEndingTo;
    private EditText mApproxBudget;
    static EditText mFromDate;
    static EditText mToDate;
    private Button mSaveTourPlan;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tour_plan);

        //Firebase
        firebaseAuth = FirebaseAuth.getInstance();
        if (firebaseAuth.getCurrentUser() == null){
            finish();
            startActivity(new Intent(TourPlanActivity.this, LoginActivity.class));
        }
        rootReference = FirebaseDatabase.getInstance().getReference();
        firebaseUser = firebaseAuth.getCurrentUser();
        FirebaseUser user = firebaseAuth.getCurrentUser();
        userReference = rootReference.child(firebaseUser.getUid());
        tourEventsReference = userReference.child("tourEvents");




        mStartingFrom = findViewById(R.id.travel_startsET);
        mEndingTo = findViewById(R.id.travel_destinationET);
        mApproxBudget = findViewById(R.id.estimated_budgetET);
        mFromDate = findViewById(R.id.from_dateET);
        mToDate = findViewById(R.id.to_dateET);
        mSaveTourPlan = findViewById(R.id.saveTourPlan);

        mSaveTourPlan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                save();
            }
        });
        mFromDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showTruitonDatePickerDialog(view);
            }
        });
        mToDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showToDatePickerDialog(view);
            }
        });

    }

    public void save(){
        String startFrom = mStartingFrom.getText().toString().trim();
        String endTo = mEndingTo.getText().toString().trim();
        String approxBudget = mApproxBudget.getText().toString().trim();
        String startDate = mFromDate.getText().toString().trim();
        String endDate = mToDate.getText().toString().trim();
        String key = rootReference.push().getKey();
        TourPlanPojo tourPlanPojo = new TourPlanPojo(startFrom,endTo,approxBudget,startDate,endDate,key);
        FirebaseUser user = firebaseAuth.getCurrentUser();

        rootReference.child(user.getUid()).child("tourEvents").push().setValue(tourPlanPojo);
        Toast.makeText(this, "Information Saved", Toast.LENGTH_LONG).show();
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



    public void showTruitonDatePickerDialog(View v) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }
    public void showToDatePickerDialog(View v) {
        DialogFragment newFragment = new ToDatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    public static class DatePickerFragment extends DialogFragment implements
            DatePickerDialog.OnDateSetListener {
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);
            DatePickerDialog datePickerDialog;
            datePickerDialog = new DatePickerDialog(getActivity(),this, year,
                    month,day);
            return datePickerDialog;
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            // Do something with the date chosen by the user
            mFromDate.setText(day + "/" + month  + "/" + year);
        }

    }

    public static class ToDatePickerFragment extends DialogFragment implements
            DatePickerDialog.OnDateSetListener {
        // Calendar startDateCalendar=Calendar.getInstance();
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            String getfromdate = mFromDate.getText().toString().trim();
            String getfrom[] = getfromdate.split("/");
            int year,month,day;
            year= Integer.parseInt(getfrom[2]);
            month = Integer.parseInt(getfrom[1]);
            day = Integer.parseInt(getfrom[0]);
            final Calendar c = Calendar.getInstance();
            c.set(year,month,day+1);
            DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(),this, year,month,day);
            datePickerDialog.getDatePicker().setMinDate(c.getTimeInMillis());
            return datePickerDialog;
        }
        public void onDateSet(DatePicker view, int year, int month, int day) {

            mToDate.setText(day + "/" + month  + "/" + year);
        }
    }
}
