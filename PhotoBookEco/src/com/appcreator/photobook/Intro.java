package com.appcreator.photobook;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

/*
 * 2012. 06. 02
 * ¾Ù¹ü ·è => ½ê¶ó´©³ª(ÀÌ¹ÌÁö ºä)
 */
public class Intro extends Activity {
	
	final String TAG = "PhotoBookEcoActivity";
	
	final int resIntroID 	= R.id.intro;
	final int resLayoutID 	= R.layout.intro;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(resLayoutID);
        
       ((TextView)findViewById(resIntroID)).setOnClickListener(new View.OnClickListener() {

    	   public void onClick(View v) {
				
    		   Log.e(TAG,"onClick");
    		   
    		   Intent i = new Intent(Intro.this,Main.class);
    		   startActivity(i);
    		   
    		   
			}
       });
    }
    
    
}