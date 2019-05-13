package com.example.mygallery;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class TheCamera extends AppCompatActivity {
    ImageView theView;
    Button save;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    static final int REQUEST_TAKE_PHOTO = 2;
    String currentPhotoPath;
    String currentDirPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_the_camera);
        dispatchTakePictureIntent();
    }

    protected void dispatchTakePictureIntent() {
        Intent takePic = new Intent(MediaStore.ACTION_IMAGE_CAPTURE); //retrieves image from device inbuilt camera app
        if (takePic.resolveActivity(getPackageManager()) != null) {
            //startActivityForResult(takePic, REQUEST_IMAGE_CAPTURE);
            File photoFile = null;
            try {
                photoFile = createImageFile();
                Log.i("DTPI-1", "dispatchTakePictureIntent: The IF AND TRY");
            } catch (IOException ex) {
                Log.i("DTPI-3", "dispatchTakePictureIntent: catch");
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Log.i("photoURI", "dispatchTakePictureIntent: PHOTO FILE NOT EMPTY");
                Uri photoURI = FileProvider.getUriForFile(this,
                        "com.example.mygallery.fileprovider",
                        photoFile);
                Log.i("PHOTOFILE", "dispatchTakePictureIntent:" + photoURI);
                takePic.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                takePic.putExtra("data", "hello");


                startActivityForResult(takePic, REQUEST_IMAGE_CAPTURE);
                setResult(RESULT_OK, takePic);
            }
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, final Intent data) {

        theView = findViewById(R.id.cameraReturn);
        save = findViewById(R.id.saveBtn);

        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            if (data != null) {
                Bundle extras = data.getExtras();
                Bitmap imageBitmap = (Bitmap) extras.get("data");
                //Bitmap theImage = (Bitmap) extras.get("fullImage");
                theView = findViewById(R.id.cameraReturn);
                theView.setImageBitmap(imageBitmap);

                save.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        savePic(data);
                    }
                });
            } else {
                Bitmap bit;
                bit = BitmapFactory.decodeFile(currentPhotoPath);
                theView.setImageBitmap(bit);
                final Intent intent = new Intent();
                intent.putExtra("data", bit);
                save.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        savePic(intent);
                    }
                });
            }


        }else{
            Intent goBack = new Intent(this, MainActivity.class);
            startActivity(goBack);
            this.finish();
        }
    }

    protected void retakePic(View view) {
       deletePic();




        new AlertDialog.Builder(TheCamera.this)
                .setTitle("New Picture?")
                .setMessage("Are you sure you want to take another picture?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        theView.setImageDrawable(null);
                        dispatchTakePictureIntent();

                    }
                })
                .setNegativeButton(android.R.string.no, null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    private void deletePic() {
        File[] pics = new File[50];
        //get directory here
    }

    protected void savePic(Intent data) {
        Intent save = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (save.resolveActivity(getPackageManager()) != null) {
            File theImage = null;
            try {
                theImage = createImageFile();

            } catch (IOException e) {
                e.printStackTrace();
            }
            if (theImage != null) {
                Uri uri = FileProvider.getUriForFile(this, "com.example.mygallery.fileprovider", theImage);
                String filePath = currentPhotoPath;
                Bundle bundle = data.getExtras();
                Bitmap image = (Bitmap) bundle.get("data");

                FileOutputStream outputStream;

                try {
                    outputStream = new FileOutputStream(filePath);

                    image.compress(Bitmap.CompressFormat.JPEG, 1, outputStream);
                    outputStream.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }


                Log.i("BUNDLE_CONTENTS", "savePic: " + bundle);
                Toast.makeText(this, "File Saved", Toast.LENGTH_SHORT).show();
                Log.i("FILE-SAVE", "savePic:" + uri);
                Intent back = new Intent(this, MainActivity.class);
                back.putExtra("dirPath", currentDirPath);
                startActivity(back);

                this.finish();
            } else {
                Toast.makeText(this, "File Save Failed!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private File createImageFile() throws IOException {
        String stamp = new SimpleDateFormat("ddMMyyHHmmss_").format(new Date());
        String fileName = "myGal_" + stamp;
        File dir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        currentDirPath = dir.getAbsolutePath();
        Log.i("PATH", "createImageFile:" + currentDirPath);

        File image = File.createTempFile(fileName, ".jpg", dir);
        Log.i("what the dir", "createImageFile:" + image);
        currentPhotoPath = image.getAbsolutePath();
        Log.i("what the dir-1", "createImageFile:" + currentPhotoPath);

        return image;
    }
}
