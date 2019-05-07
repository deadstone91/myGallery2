package com.example.mygallery;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class TheCamera extends AppCompatActivity {
    ImageView theView;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    String currentPhotoPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_the_camera);
        dispatchTakePictureIntent();
    }

    private void dispatchTakePictureIntent(){
        Intent takePic = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePic.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePic, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            theView = findViewById(R.id.cameraReturn);
            theView.setImageBitmap(imageBitmap);
        }
    }

    protected void retakePic(View view){
        new AlertDialog.Builder(TheCamera.this)
                .setTitle("New Picture?")
                .setMessage("Are you sure you want to take another picture?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dispatchTakePictureIntent();
                    }
                })
                .setNegativeButton(android.R.string.no,null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    protected void savePic(View view){
        Intent save = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if(save.resolveActivity(getPackageManager()) != null){
            File theImage = null;
            try {
                theImage = createImageFile();

            }catch (IOException e){
               e.printStackTrace();
            }
            if(theImage != null){
                Uri uri = FileProvider.getUriForFile(this,"com.example.mygallery.fileprovider",theImage);
                
                Toast.makeText(this,"File Saved",Toast.LENGTH_SHORT).show();
                Log.i("FILE-SAVE", "savePic:"+ uri );
                this.finish();
            }else {
                Toast.makeText(this,"File Save Failed!",Toast.LENGTH_SHORT).show();
            }
        }
    }

    private File createImageFile() throws IOException {
        String stamp = new SimpleDateFormat("ddMMyyHHmmss").format(new Date());
        String fileName = "myGal_" + stamp;
        File dir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);

        File image  = File.createTempFile(fileName,".jpg",dir);
        Log.i("what the dir", "createImageFile:" + image);
        currentPhotoPath = image.getAbsolutePath();
        Log.i("what the dir-1", "createImageFile:" + currentPhotoPath);

        return image;
    }
}
