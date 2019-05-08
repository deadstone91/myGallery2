package com.example.mygallery;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

public class ViewImage extends AppCompatActivity {

    ImageView view = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_image);

        addImage();






    }

    protected void addImage(){

        Bundle extra = getIntent().getExtras();
        String picture = extra.getString("picture");
        Log.i("picture", "onCreate: " + picture);
        Bitmap bm = BitmapFactory.decodeFile(picture);
        view = findViewById(R.id.imageView2);
        view.setImageBitmap(bm);
    }
}
