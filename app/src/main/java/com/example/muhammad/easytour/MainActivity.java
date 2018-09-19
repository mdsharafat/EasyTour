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
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.muhammad.easytour.ActivityGoogleMap.MapsActivity;
import com.example.muhammad.easytour.ActivityRegistrationLogin.LoginActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.zip.Inflater;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private LinearLayout mLocationLL;
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

        mLocationLL = findViewById(R.id.location_LL);
        mLocationLL.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_item, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.item_logout:
                firebaseAuth.signOut();
                finish();
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        if (view == mLocationLL){
            startActivity(new Intent(MainActivity.this, MapsActivity.class));
        }
    }
}
