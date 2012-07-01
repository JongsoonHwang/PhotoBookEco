package com.appcreator.photobook;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

/*
 * 2012. 06. 02
 * 앨범 룩 => 쎄라누나(이미지 뷰)
 */

public class Intro extends CommonActivity {
	
	final String TAG = "PhotoBookEcoActivity";
	
	final int resIntroID 	= R.id.intro;
	final int resLayoutID 	= R.layout.intro;
	
	ImageView ImgIntro;
	static Handler mHandler;
	Animation animation;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		
        setContentView(resLayoutID);
        
        ImgIntro  = (ImageView)findViewById(R.id.intro); 
         
        animation = AnimationUtils.loadAnimation(Intro.this, R.anim.alpha);
        ImgIntro.startAnimation(animation);
        
        mHandler = new Handler();
        mHandler.postDelayed(runnable, 6000);
    }
        
    Runnable runnable = new Runnable() {
		
		public void run() {
			// TODO Auto-generated method stubs

			Intent i = new Intent(Intro.this, Main.class);

		    startActivity(i);
		    finish();
		}
	};
    
}
