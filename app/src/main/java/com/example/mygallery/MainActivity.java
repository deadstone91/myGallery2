package com.example.mygallery;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Gallery;

public class MainActivity extends AppCompatActivity {

    String dir = "/storage/emulated/0/Android/data/com.example.mygallery/files/Pictures/";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Log.i("INTENT DATA", "onCreate: " + dir);
    }

    protected void viewCamera(View view){
        Intent useCamera = new Intent(this,TheCamera.class);
        startActivity(useCamera);
    }

    protected void  viewGallery(View view){
        Intent gallery = new Intent(this, TheGallery.class);
        gallery.putExtra("dirPath",dir);
        startActivity(gallery);
    }
}
