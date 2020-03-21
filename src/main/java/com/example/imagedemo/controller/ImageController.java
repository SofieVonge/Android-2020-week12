package com.example.imagedemo.controller;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.LocationManager;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;

import com.example.imagedemo.MainActivity;

import java.io.FileNotFoundException;
import java.io.InputStream;

public class ImageController {

    private MainActivity mainActivity;

    public ImageController(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    public void handleImageReturn(int requestCode, @Nullable Intent data) {
        if (requestCode == 0) { //if photoroll
            Uri uri = data.getData(); //data from photoroll
            try {
                InputStream is = mainActivity.getContentResolver().openInputStream(uri); //handles the content of all apps in the device if it gets the uri
                Bitmap bitmap = BitmapFactory.decodeStream(is);
                mainActivity.imageview.setImageBitmap(bitmap); //show the photo in the imageview
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        else if (requestCode == 1) { //data from camera
            Bitmap bitmap = (Bitmap) data.getExtras().get("data"); //the data itself was provided with the intent
            mainActivity.imageview.setImageBitmap(bitmap);
            //resizing the image from the cameraroll
            mainActivity.imageview.animate().scaleX(0.5f).scaleY(0.5f);
            //getting the image from the camera to the roll
            mainActivity.imageview.buildDrawingCache();
            Bitmap image = mainActivity.imageview.getDrawingCache();
            MediaStore.Images.Media.insertImage(mainActivity.getContentResolver(), image, "From camera", "a photo from the camera");

        }
    }

}
