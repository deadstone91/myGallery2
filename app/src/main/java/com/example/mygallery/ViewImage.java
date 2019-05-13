package com.example.mygallery;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ShareActionProvider;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class ViewImage extends AppCompatActivity {

    private static final int MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL = 1;
    private static final String CHANNEL_ID = "intersting";
    ImageView view = null;
    String file;
    ImageButton shareBtn;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_image);

        shareBtn = findViewById(R.id.shareBtn);
        addImage();
        shareBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Drawable myDraw = view.getDrawable();
                Bitmap bm = ((BitmapDrawable)myDraw).getBitmap();


                 try {
                     File file = new File(ViewImage.this.getExternalCacheDir(), "image.jpg");
                     FileOutputStream fOut  = new FileOutputStream(file);
                     bm.compress(Bitmap.CompressFormat.JPEG, 100, fOut);
                     fOut.flush();
                     fOut.close();
                     file.setReadable(true,false);
                     Intent intent = new Intent(Intent.ACTION_SEND);
                     intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                     intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file));
                     intent.setType("image/jpg");
                     startActivity(Intent.createChooser(intent, "Share Picture"));
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                     Toast.makeText(ViewImage.this,"File Not Found",Toast.LENGTH_SHORT).show();
                }catch (IOException i) {
                     i.printStackTrace();
                 }catch (Exception e){
                     e.printStackTrace();
                 }
            }
        });
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
