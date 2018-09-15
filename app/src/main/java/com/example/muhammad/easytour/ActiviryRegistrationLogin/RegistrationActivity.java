package com.example.muhammad.easytour.ActiviryRegistrationLogin;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.muhammad.easytour.R;
import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegistrationActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText registrationEmail;
    private EditText registrationPassword;
    private Button registrationBTN;
    private TextView registrationSigninTV;

    private ProgressDialog progressDialog;

    private FirebaseAuth firebaseAuth;

    //menu item
    private boolean isSignIn= false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        firebaseAuth = FirebaseAuth.getInstance();

        progressDialog = new ProgressDialog(this);

        registrationEmail = findViewById(R.id.registration_email_ET);
        registrationPassword = findViewById(R.id.registration_password_ET);
        registrationBTN = findViewById(R.id.registration_BTN);
        registrationSigninTV = findViewById(R.id.registration_signin_TV);

        registrationBTN.setOnClickListener(this);
        registrationSigninTV.setOnClickListener(this);

    }

    private void registerUser(){
        String email = registrationEmail.getText().toString().trim();
        String password = registrationPassword.getText().toString().trim();

        if (TextUtils.isEmpty(email)){
            registrationEmail.setError("Email field cannot be empty");
            return;
        }
        if (TextUtils.isEmpty(password)){
            registrationPassword.setError("Password field cannot be empty");
            return;
        }

        progressDialog.setMessage("Please wait a while. Processing data");
        progressDialog.show();

        firebaseAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(RegistrationActivity.this, "Successfull", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(RegistrationActivity.this, LoginActivity.class);
                            startActivity(intent);
                        }else {
                            Toast.makeText(RegistrationActivity.this, "Error", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }

    @Override
    public void onClick(View view) {
        if (view == registrationBTN){
            registerUser();
        }
        if (view == registrationSigninTV){

        }
    }


    //*******************menu item selection method***************************

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater Inflater = getMenuInflater();
        Inflater.inflate(R.menu.menu_item,menu);

        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {

        MenuItem SignInItem = menu.findItem(R.id.item_signIn);
        MenuItem SignOutItem = menu.findItem(R.id.item_signOut);
        MenuItem HomeItem = menu.findItem(R.id.item_home);
        if(!isSignIn)
        {
            SignOutItem.setVisible(true);
            SignInItem.setVisible(false);
        }else
            {
                SignOutItem.setVisible(false);
                SignInItem.setVisible(true);
            }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_home:

                break;
            case R.id.item_profile:

                break;
            case R.id.item_signIn:
                isSignIn = true;
                break;
            case R.id.item_signOut:
                isSignIn = false;

                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
