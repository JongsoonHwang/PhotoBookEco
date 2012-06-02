package com.appcreator.photobook;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

/*
 * 2012. 06. 02
 * List(expanded, ´õ¹Ì)=>Àçºó(Listview+ÀÌ¹ÌÁöºä:¸ÇÇÏ´Ü), 
 * ´ÙÀ½ÁÖ==>
 * ±¤°í
 */
public class Main extends Activity {

	final String TAG = "Main";
	
	final int resIntroID 	= R.id.intro;
	final int resLayoutID 	= R.layout.main;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	
	    setContentView(resLayoutID);
        
	       ((TextView)findViewById(resIntroID)).setOnClickListener(new View.OnClickListener() {

	    	   public void onClick(View v) {
					
	    		   Log.e(TAG,"onClick");
	    		   Intent i = new Intent(Main.this,NewPhotoBook.class);
	    		   startActivity(i);

	    		   
	    		   
				}
	       });
	}

}
