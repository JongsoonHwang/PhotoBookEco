package com.appcreator.photobook;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;

public class OriginalPicture extends Activity {
	private ImageView imgView; 
    private String filename; 
  
    public void onCreate(Bundle savedInstanceState) { 
        super.onCreate(savedInstanceState); 
        setContentView(R.layout.picture_view); 
  
        imgView = (ImageView) findViewById(R.id.imageView); 
        processIntent(); 
    }
    private void processIntent() { 
        System.gc(); 
  
        Intent intent = getIntent(); 
        Bundle extras = intent.getExtras(); 
        filename = extras.getString("filename"); 
  
        BitmapFactory.Options options = new BitmapFactory.Options(); 
        options.inSampleSize = 2; 
  
        Bitmap bitmap = BitmapFactory.decodeFile(BasicDefine.FOLDER_PHOTO + filename, options); 
  
        imgView.setImageBitmap(bitmap); 
  
    } 

}
