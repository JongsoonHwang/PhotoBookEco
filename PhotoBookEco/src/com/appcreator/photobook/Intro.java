package com.appcreator.photobook;

import android.content.Intent;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Handler;
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
	SoundPool sp;
	int introsound;

	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		
        setContentView(resLayoutID);
        
        sp = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);
        introsound = sp.load(this, R.raw.introsound, 1);
        
        ImgIntro  = (ImageView)findViewById(R.id.intro);
        Animation animation = null;
        animation = AnimationUtils.loadAnimation(Intro.this, R.anim.alpha);
        ImgIntro.startAnimation(animation);
        
        mHandler = new Handler();
        mHandler.postDelayed(runnable, 6000);
        
        
        new Handler().postDelayed(new Runnable() {
            public void run() {
            	sp.play(introsound, 1, 1, 0, 0, 1);
            }
        }, 2000);
        //sp.release();
    }
        
    Runnable runnable = new Runnable() {
		
		public void run() {
			// TODO Auto-generated method stubs

			Intent i = new Intent(Intro.this, PhotoBook.class);
			//Intent i = new Intent(Intro.this, ThemeSlide.class);

		    startActivity(i);
		    finish();
		}
	};
    
}
