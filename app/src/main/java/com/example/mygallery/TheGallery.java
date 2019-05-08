package com.example.mygallery;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;

public class TheGallery extends AppCompatActivity {
    GridView grid;
    String dirPath;
    Bitmap thatImage = null;

    String filePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_the_gallery);

        grid = (GridView)findViewById(R.id.grid);
        Intent intent = getIntent();
        dirPath = intent.getStringExtra("dirPath");
        grid.setAdapter(new ImageAdapter(this,dirPath));
        alert();




        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Log.i("GALLERY POSITION", "onItemClick: " + position);
                if(position == 0){
                    Bitmap theImageIWant = null;
                    getFile(position);

                    Log.i("THEIMAGEIWANT", "onItemClick: " + theImageIWant);

                    Intent viewImage = new Intent(TheGallery.this, ViewImage.class);
                    viewImage.putExtra("picture",filePath);
                    filePath = null;
                    startActivity(viewImage);
                }else{
                    getFile(position);
                    Intent viewImage = new Intent(TheGallery.this, ViewImage.class);
                    viewImage.putExtra("picture",filePath);
                    filePath = null;
                    startActivity(viewImage);
                }
            }
        });
    }
    protected void alert(){
        if (grid.getChildCount() == 0){
            new AlertDialog.Builder(this)
                    .setTitle("Empty")
                    .setMessage("Gallery seems to be empty would you like to take a photo?")
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent go = new Intent(TheGallery.this, TheCamera.class);
                            startActivity(go);
                        }
                    })
                    .setNegativeButton(android.R.string.no,null)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        }

    }

    protected void getFile(int number){

        File directory = new File(dirPath);
        File[] images;
        images = directory.listFiles();
        Log.i("DIRECTORY LENGTH", "getFile:" + images.length);
        if(number == 0){
            filePath = dirPath + images[0].getName();
            Log.i("file~path", "getFile: " + filePath);
        }else{
            int temp = (number*2)+1;
            filePath = dirPath + images[temp].getName();
            Log.i("file~path2", "getFile: " + filePath);
        }
    }
}

