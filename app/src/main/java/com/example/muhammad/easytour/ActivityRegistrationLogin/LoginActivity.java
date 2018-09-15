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
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText loginEmail;
    private EditText loginPassword;
    private Button loginBTN;
    private TextView loginRegisterTV;

    private ProgressDialog progressDialog;

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        firebaseAuth = FirebaseAuth.getInstance();

        if (firebaseAuth.getCurrentUser() != null){
            finish();
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
        }

        progressDialog = new ProgressDialog(this);

        loginEmail = findViewById(R.id.login_email_ET);
        loginPassword = findViewById(R.id.login_password_ET);
        loginBTN = findViewById(R.id.login_BTN);
        loginRegisterTV = findViewById(R.id.login_TV);

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
                            finish();
                            startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        }
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
}
