package com.example.mygallery;

import android.content.Context;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;


import java.io.File;
import java.nio.file.Files;
import java.util.ArrayList;

public class ImageAdapter extends BaseAdapter {
    private Context context;

    private ArrayList<File> pics = new ArrayList<>();
    private Bitmap[] bit = new Bitmap[50];
    String path;
    File[] files;

    int counter = 0;

    public ImageAdapter(Context con, String dirPath) {
        context = con;
        path = dirPath;
        getImages();
    }

    public void getImages() {
        //String path = Environment.getExternalStorageDirectory().toString()+"/Pictures";
        Log.d("Files", "Path: " + path);
        File directory = new File(path);

        files = directory.listFiles();
        Log.d("Files", "Size: " + files.length);
        if (files.length != 0) {
           for(int i = 0;i < files.length;i++)
           {
                if(i % 2 != 0)
                {
                    pics.add(files[i]);
                    Log.d("Files", "FileName:" + files[i].getName());
                    Bitmap bm = BitmapFactory.decodeFile(path + files[i].getName());
                    bit[counter] = bm;
                    counter++;
                }
           }
       } else {
            Log.i("EMPTY", "getImages: TOTALLY EMPTY");

       }
    }

        @Override
        public int getCount () {
            return pics.size();
        }

        @Override
        public Object getItem ( int position){
            return pics.get(position);
        }

        @Override
        public long getItemId ( int position){
            return 0;
        }

        @Override
        public View getView ( int position, View convertView, ViewGroup parent){
            ImageView imageView = new ImageView(context);
            imageView.setImageBitmap(bit[position]);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setLayoutParams(new GridView.LayoutParams(240, 240));
            return imageView;
        }
    }
