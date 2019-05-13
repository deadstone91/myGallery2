package com.example.mygallery;

import android.content.Context;
import android.os.Environment;
import android.widget.Toast;

import java.io.File;

public class LastPicThread extends Thread {

    public LastPicThread(Context applicationContext) {

        Context con = applicationContext;

        boolean isIt = deleteLast();

        if(isIt == true){
            Toast.makeText(con,"camera image cleared",Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(con,"image not cleared",Toast.LENGTH_SHORT).show();
        }
    }

    private boolean deleteLast() {
        boolean success = false;
        try {
            File[] images = new File(Environment.getExternalStorageDirectory()
                    + File.separator + "DCIM/100ANDRO").listFiles();
            File latestSavedImage = images[0];
            for (int i = 1; i < images.length; ++i) {
                if (images[i].lastModified() > latestSavedImage.lastModified()) {
                    latestSavedImage = images[i];
                }
            }

            // OR JUST Use  success = latestSavedImage.delete();
            success = new File(Environment.getExternalStorageDirectory()
                    + File.separator + "DCIM/Camera/"
                    + latestSavedImage.getAbsoluteFile()).delete();
            return success;
        } catch (Exception e) {
            e.printStackTrace();
            return success;
        }
    }

}

