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

import com.example.muhammad.easytour.MainActivity;
import com.example.muhammad.easytour.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText loginEmail;
    private EditText loginPassword;
    private Button loginBTN;
    private TextView loginRegisterTV;
    private TextView loginStatusTV;

    private ProgressDialog progressDialog;

    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();

        if (firebaseAuth.getCurrentUser() != null){
            finish();
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
        }

        progressDialog = new ProgressDialog(this);

        loginEmail = findViewById(R.id.login_email_ET);
        loginPassword = findViewById(R.id.login_password_ET);
        loginBTN = findViewById(R.id.login_BTN);
        loginRegisterTV = findViewById(R.id.login_TV);
        loginStatusTV = findViewById(R.id.login_status_TV);
        loginBTN.setOnClickListener(this);
        loginRegisterTV.setOnClickListener(this);

    }

    public void userLogin(){
        String email = loginEmail.getText().toString().trim();
        String password = loginPassword.getText().toString().trim();

        if (TextUtils.isEmpty(email)){
            loginEmail.setError("Email field cannot be empty");
            return;
        }
        if (TextUtils.isEmpty(password)){
            loginPassword.setError("Password field cannot be empty");
            return;
        }

        progressDialog.setMessage("Please wait a while. Processing data");
        progressDialog.show();

        firebaseAuth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();
                        if  (task.isSuccessful()){
                            firebaseUser = firebaseAuth.getCurrentUser();
                            updateUI();
                            finish();
                            startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                loginStatusTV.setText(e.getLocalizedMessage());
            }
        });

    }

    @Override
    public void onClick(View view) {
        if (view == loginBTN){
            userLogin();
        }
        if (view == loginRegisterTV){
            Intent intent = new Intent(LoginActivity.this,RegistrationActivity.class);
            startActivity(intent);
        }
    }

    private void updateUI()
    {
        if (firebaseUser != null)
        {
            String displayUserName = firebaseUser.getDisplayName();
            String displayUserEmail = firebaseUser.getEmail();
            boolean status = firebaseUser.isEmailVerified();
        }
    }

    private void validate(String userEmail, String userPassword)
    {

    }
}
