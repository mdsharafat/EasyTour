package com.example.muhammad.easytour.ActivityRegistrationLogin;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.muhammad.easytour.MainActivity;
import com.example.muhammad.easytour.R;
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        firebaseAuth = FirebaseAuth.getInstance();

        if (firebaseAuth.getCurrentUser() != null){
            finish();
            Intent homePage = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(homePage);
        }

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

        progressDialog.setMessage("Please wait a while...");
        progressDialog.show();

        firebaseAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            progressDialog.dismiss();
                            finish();
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        }else {
                            progressDialog.dismiss();
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
            startActivity(new Intent(this, LoginActivity.class));
        }
    }
}
