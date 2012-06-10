package com.appcreator.photobook;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

/*
 * 2012. 06. 02
 * ¾Ù¹ü ·è => ½ê¶ó´©³ª(ÀÌ¹ÌÁö ºä)
 */
public class Intro extends Activity {
	
	final String TAG = "PhotoBookEcoActivity";
	
	final int resIntroID 	= R.id.intro;
	final int resLayoutID 	= R.layout.intro;
	
	ImageView ImgIntro;
	static Handler mHandler;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(resLayoutID);
        
        ImgIntro  = (ImageView)findViewById(R.id.intro);
        Animation animation = null;
        animation = AnimationUtils.loadAnimation(Intro.this, R.anim.alpha);
        ImgIntro.startAnimation(animation);
        
        mHandler = new Handler();
        mHandler.postDelayed(runnable, 5500);
    }
        
    Runnable runnable = new Runnable() {
		
		public void run() {
			// TODO Auto-generated method stub
			Intent i = new Intent(Intro.this, Main.class);
		    startActivity(i);
		    finish();
		}
	};
    
}