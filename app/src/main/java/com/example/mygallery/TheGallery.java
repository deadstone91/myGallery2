package com.example.mygallery;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.GridView;

public class TheGallery extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_the_gallery);

        GridView grid = (GridView)findViewById(R.id.grid);
        Intent intent = getIntent();
        String dirPath = intent.getStringExtra("dirPath");
        grid.setAdapter(new ImageAdapter(this,dirPath));

    }
}
