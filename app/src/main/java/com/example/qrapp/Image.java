package com.example.qrapp;

import android.graphics.Bitmap;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.util.UUID;

public class Image {

    String imgSrc;

    public Image(String imgSrc) {
        this.imgSrc = imgSrc;
    }

    public String getImgSrc() {
        return imgSrc;
    }

    public void setImgSrc(String imgSrc) {
        this.imgSrc = imgSrc;
    }


    public void saveToInternalStorage(Bitmap bitmapImage){
        File directory = new File(this.imgSrc);

        if (! directory.exists()){
            directory.mkdir();
        }

        UUID uuid = UUID.randomUUID();
        File mypath = new File(directory, uuid.toString() + ".png");

        Log.d("hm", mypath.getAbsolutePath());
        Log.d("hm", directory.getAbsolutePath());

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(mypath);
            bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, fos);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
