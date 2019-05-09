package com.example.mygallery;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import java.io.IOException;

public class ViewImage extends AppCompatActivity {

    private static final int MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL = 1;
    private static final String CHANNEL_ID = "intersting";
    ImageView view = null;
    String file;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_image);

        addImage();
    }

    protected void addImage(){

        Bundle extra = getIntent().getExtras();
        String picture = extra.getString("picture");
        file = picture;
        Log.i("picture", "onCreate: " + picture);
        Bitmap bm = BitmapFactory.decodeFile(picture);
        view = findViewById(R.id.imageView2);
        view.setImageBitmap(bm);
    }

    protected void save(View view){
        PrimeThread thread = null;
        try {
            thread = new PrimeThread(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

            } else {

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL);

            }
        } else {
            // Permission has already been granted
        }
        thread.start();

       //notifyUser();

    }

    private void notifyUser(){
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("My Gallery")
                .setContentText("FIlE SAVED TO DOWNLOADS")
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText("FILE SAVED"))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        int notificationId = 1003;
        notificationManager.notify(notificationId, builder.build());
    }
}
