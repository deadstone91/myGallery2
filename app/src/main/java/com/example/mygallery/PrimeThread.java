package com.example.mygallery;

import android.os.Environment;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Random;

public class PrimeThread extends Thread {

    private static final String CHANNEL_ID = "interesting";
    String file;
    int counter = 100;

    public PrimeThread(String file) {
        this.file = file;
        Log.i("file", "PrimeThread: " + file);
        saveFile(file);

    }

    private void saveFile(String aFile) {
        File dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        String dirPath = dir.getAbsolutePath();

        InputStream in = null;
        OutputStream out = null;

        try{
            Random rand = new Random();
            int n = rand.nextInt(10000);
            n += 1;
            in = new FileInputStream( aFile);
            out = new FileOutputStream(dir + "/" + "mygal_"+n+".jpg");

            byte[] buffer = new byte[4000];
            int read;
            while((read = in.read(buffer)) != -1){
                out.write(buffer,0,read);
            }
            in.close();
            in=null;

            out.flush();
            out.close();
            out = null;

        }catch (FileNotFoundException f){
            f.printStackTrace();
        }catch (Exception e){
            e.printStackTrace();
        }



        counter++;


    }

}
