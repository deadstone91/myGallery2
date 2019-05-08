package com.example.mygallery;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.GridView;

public class TheGallery extends AppCompatActivity {
    GridView grid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_the_gallery);

        grid = (GridView)findViewById(R.id.grid);
        Intent intent = getIntent();
        String dirPath = intent.getStringExtra("dirPath");
        grid.setAdapter(new ImageAdapter(this,dirPath));
        alert();
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
}
