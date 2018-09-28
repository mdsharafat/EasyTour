package com.example.muhammad.easytour.ActivityOthersIntent;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.muhammad.easytour.ActivityRegistrationLogin.LoginActivity;
import com.example.muhammad.easytour.MainActivity;
import com.example.muhammad.easytour.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.UUID;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {

    private boolean isSignIn = false;
    private boolean isSignout = false;
    private boolean isHome = false;
    private boolean isProfile = false;

    private ImageView mProfilePic;
    private EditText mFullName;
    private EditText mEmail;
    private EditText mPhone;
    private EditText mAddress;
    private Button mSaveButton;
    private Button mUpdateButton;

    private Uri uriProfileImage;
    String profileImageUrl;

    private static final int CHOOSE_IMAGE = 71;


    FirebaseAuth mAuth;
    FirebaseStorage firebaseStorage;
    StorageReference profileImageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        mProfilePic = findViewById(R.id.profilePic);
        mProfilePic.setEnabled(false);

        mUpdateButton = findViewById(R.id.profileUpdateButton);
        mUpdateButton.setOnClickListener(this);
        mFullName = findViewById(R.id.fullName);
        mFullName.setEnabled(false);
        mEmail = findViewById(R.id.emailAddress);
        mEmail.setEnabled(false);
        mPhone = findViewById(R.id.contactNumber);
        mPhone.setEnabled(false);
        mAddress = findViewById(R.id.address);
        mAddress.setEnabled(false);
        mSaveButton = findViewById(R.id.profileSaveButton);
        mSaveButton.setOnClickListener(this);



        mAuth = FirebaseAuth.getInstance();
        firebaseStorage = FirebaseStorage.getInstance();

        loadUserInformation();



    }

    @Override
    protected void onStart() {
        super.onStart();

        if (mAuth.getCurrentUser() == null){
            finish();
            startActivity(new Intent(ProfileActivity.this,LoginActivity.class));
        }
    }

    private void loadUserInformation() {
        FirebaseUser user = mAuth.getCurrentUser();

        if (user != null){
            if (user.getPhotoUrl() != null){
                Glide.with(this).load(user.getPhotoUrl().toString()).into(mProfilePic);
            }
            if (user.getDisplayName() != null){
                mFullName.setText(user.getDisplayName());
            }
        }
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
                break;
            case R.id.item_logout:
                mAuth.signOut();
                finish();
                startActivity(new Intent(ProfileActivity.this, LoginActivity.class));
                break;
        }

        return super.onOptionsItemSelected(item);

    }

    @Override
    public void onClick(View view) {
        if (view == mProfilePic){
            showImageChooser();
        }
        if (view == mUpdateButton){
            mFullName.setEnabled(true);
            mPhone.setEnabled(true);
            mAddress.setEnabled(true);
            mProfilePic.setEnabled(true);
            mProfilePic.setOnClickListener(this);
        }

        if (view == mSaveButton){
            saveUserInformation();
        }
    }

    private void saveUserInformation() {
        String fullName = mFullName.getText().toString();


        FirebaseUser user = mAuth.getCurrentUser();

        if (user != null && profileImageUrl != null){
            UserProfileChangeRequest profile = new UserProfileChangeRequest.Builder()
                    .setDisplayName(fullName)
                    .setPhotoUri(Uri.parse(profileImageUrl))
                    .build()
                    ;

            user.updateProfile(profile).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()){
                        Toast.makeText(ProfileActivity.this, "Profile Updated", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CHOOSE_IMAGE && resultCode == RESULT_OK && data != null && data.getData() != null){
            uriProfileImage = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),uriProfileImage);
                mProfilePic.setImageBitmap(bitmap);

                uploadImageToFirebaseStorage();

            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    private void uploadImageToFirebaseStorage() {
        profileImageReference = FirebaseStorage.getInstance().getReference("profilepics/"+ System.currentTimeMillis()+ ".jpg");
        if (uriProfileImage != null){

            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("Uploading ....");
            progressDialog.show();

            profileImageReference.putFile(uriProfileImage).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    progressDialog.dismiss();

                    profileImageUrl = profileImageReference.getDownloadUrl().toString();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    progressDialog.dismiss();
                    Toast.makeText(ProfileActivity.this, "Failed"+e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                    double progress = (100.0*taskSnapshot.getBytesTransferred()/taskSnapshot.getTotalByteCount());
                    progressDialog.setMessage("Uploaded "+(int)progress+"%");
                }
            });
        }
    }

    private void showImageChooser(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Select Picture"),CHOOSE_IMAGE);
    }


}
