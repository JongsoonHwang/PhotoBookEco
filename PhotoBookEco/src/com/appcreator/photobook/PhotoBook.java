package com.appcreator.photobook;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

/*
 * 2012. 06. 02
 * TAB => Àçºó, ½½¶óÀÌµå => Àçºó(themeslide Àç»ç¿ëÇÔ)
 * ÀÏ¹Ý·è, Ä«Åå ·è =>½ê¶ó´©³ª
 */


public class PhotoBook extends Activity {

	final String TAG = "PhotoBook";
	
	final int resIntroID 	= R.id.intro;
	final int resLayoutID 	= R.layout.main;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	
	    setContentView(resLayoutID);
        
	       ((TextView)findViewById(resIntroID)).setOnClickListener(new View.OnClickListener() {

	    	   public void onClick(View v) {
					
	    		   Log.e(TAG,"onClick");
	    		   
	    		   
				}
	       });
	}

}
