package com.example.imagedemo;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;

import com.example.imagedemo.controller.ImageController;

import java.io.FileNotFoundException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity {

    public ImageView imageview;
    private ImageController ic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageview = findViewById(R.id.imageView);
        ic = new ImageController(this);
    }

    public void photoRollPressed(View view) {
        // an intent is our way of saying that Android system should launch an activity that can handle this request
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, 0);

    }

    @Override //this is code for what happens when we return from the intent, e.g. the result of the intent
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode != -1) return; //-1 indicates that everything went well, and if it didn't go well, we just return

        ic.handleImageReturn(requestCode, data);
    }


    public void cameraPressed(View view) {
        //check for permission
        handlePermissionUpdate();
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE); //gets us to the camera
        startActivityForResult(intent, 1);
    }

    private void handlePermissionUpdate() {
        //handle the permissions
        //first chech if we have permissions,
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            //if we dont, we need to ask for permission
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        }
    }

    //what happens based on the result of the permission request
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            handlePermissionUpdate();
        }
    }
}
