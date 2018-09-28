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
import com.example.muhammad.easytour.Adapter.ExpenseListAdapter;
import com.example.muhammad.easytour.Adapter.TourEventsAdapter;
import com.example.muhammad.easytour.MainActivity;
import com.example.muhammad.easytour.PojoClass.AddExpensePojo;
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

public class ExpenseListActivity extends AppCompatActivity {

    private boolean isSignIn = false;
    private boolean isSignout = false;
    private boolean isHome = false;
    private boolean isProfile = false;


    //Firebase
    private FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;

    //RecyclerView
    RecyclerView recyclerView;
    ExpenseListAdapter expenseListAdapter;
    ArrayList<AddExpensePojo> addExpensePojos;
    ArrayList<AddExpensePojo> addExpenseShowPojos;
    DatabaseReference rootReference;
    DatabaseReference userReference;
    DatabaseReference addExpenseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expense_list);

        //RecyclerView
        recyclerView = findViewById(R.id.expenseListRecyclerViewID);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        addExpensePojos = new ArrayList<>();
        addExpenseShowPojos = new ArrayList<>();

        //FirebaseAuth
        firebaseAuth = FirebaseAuth.getInstance();
        rootReference = FirebaseDatabase.getInstance().getReference();
        firebaseUser = firebaseAuth.getCurrentUser();
        FirebaseUser user = firebaseAuth.getCurrentUser();
        userReference = rootReference.child(firebaseUser.getUid());
        addExpenseReference = userReference.child("addExpense");
        addExpenseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                addExpensePojos.clear();
                addExpenseShowPojos.clear();
                if (dataSnapshot.exists()) {
                    for (DataSnapshot d : dataSnapshot.getChildren()) {
                        AddExpensePojo addExpensePojo = d.getValue(AddExpensePojo.class);
                        addExpensePojos.add(addExpensePojo);
                    }
                    for (AddExpensePojo t : addExpensePojos) {
                        addExpenseShowPojos.add(new AddExpensePojo(t.getmExpenseDetails(), t.getmExpenseAmount(),t.getDate(),t.getTime()));
                    }
                    expenseListAdapter = new ExpenseListAdapter(getApplicationContext(), addExpenseShowPojos);
                    recyclerView.setAdapter(expenseListAdapter);
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
        ProfileItem.setVisible(false);

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
