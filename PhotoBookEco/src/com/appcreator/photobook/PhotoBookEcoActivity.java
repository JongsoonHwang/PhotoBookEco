package com.appcreator.photobook;

import android.app.Activity;
import android.os.Bundle;

public class PhotoBookEcoActivity extends Activity {
	
	final int resIntroID 	= R.id.intro;
	final int resLayoutID 	= R.layout.main;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(resLayoutID);
    }
    
    
}