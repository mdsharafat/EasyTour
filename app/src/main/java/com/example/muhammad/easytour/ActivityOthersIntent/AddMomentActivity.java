package com.example.muhammad.easytour.ActivityOthersIntent;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.muhammad.easytour.ActivityRegistrationLogin.LoginActivity;
import com.example.muhammad.easytour.MainActivity;
import com.example.muhammad.easytour.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;

public class AddMomentActivity extends AppCompatActivity {
    private boolean isSignIn = false;
    private boolean isSignout = false;
    private boolean isHome = false;
    private boolean isProfile = false;


    //Camera
    private Button mSaveMoments;
    private ImageView mCamera;
    private EditText mMomentDescription;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    static final int MY_CAMERA_REQUEST_CODE = 2;
    private String mPhotoData;
    private StorageReference mStorage;
    private ProgressDialog mProgress;
    private StorageReference filepath;

    //Firebase Database
    private FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    DatabaseReference rootReference;
    DatabaseReference userReference;
    DatabaseReference momentReference;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_moment);
        firebaseAuth = FirebaseAuth.getInstance();
        if (firebaseAuth.getCurrentUser() == null){
            finish();
            startActivity(new Intent(AddMomentActivity.this, LoginActivity.class));
        }
        rootReference = FirebaseDatabase.getInstance().getReference();
        firebaseUser = firebaseAuth.getCurrentUser();
        FirebaseUser user = firebaseAuth.getCurrentUser();
        userReference = rootReference.child(firebaseUser.getUid());
        momentReference = userReference.child("moments");


        //Camera
        mSaveMoments = findViewById(R.id.saveMoment);
        mCamera = findViewById(R.id.momentImage);
        mMomentDescription = findViewById(R.id.momentDetails);
        mStorage = FirebaseStorage.getInstance().getReference();
        mProgress = new ProgressDialog(this);
        mCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dispatchTakePictureIntent ();
            }
        });
        getCameraPermission();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK ) {

            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) data.getExtras().get("data");
            mPhotoData = encodeToBase64 (imageBitmap,Bitmap.CompressFormat.JPEG, 50);
            System.out.println(mPhotoData);
            mCamera.setImageBitmap(imageBitmap);

            //mProgress.setMessage("Uploading Image");
            //mProgress.show();
        }
    }


    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    public void getCameraPermission()
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (this.checkSelfPermission(Manifest.permission.CAMERA)
                    != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.CAMERA}, MY_CAMERA_REQUEST_CODE);
            }
        }
    }

    public static String encodeToBase64(Bitmap image, Bitmap.CompressFormat compressFormat, int quality)
    {
        ByteArrayOutputStream byteArrayOS = new ByteArrayOutputStream();
        image.compress(compressFormat, quality, byteArrayOS);
        return Base64.encodeToString(byteArrayOS.toByteArray(), Base64.DEFAULT);
    }


    public static Bitmap decodeBase64(String input)
    {
        byte[] decodedBytes = Base64.decode(input, 0);
        return BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
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
