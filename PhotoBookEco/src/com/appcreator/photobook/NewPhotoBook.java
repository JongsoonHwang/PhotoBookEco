package com.appcreator.photobook;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

/*
 * 2012. 06. 02
 * List=>재빈, 버튼 3개 => 쎄라누나
 * 다음 주 ==> 
 * 사진 가져오기, 찍기 => 
 * 디비 연동 =>
 */

public class NewPhotoBook extends Activity {

	final String TAG = "NewPhotoBook";
	
	final int resIntroID 	= R.id.intro;
	final int resLayoutID 	= R.layout.newphotobook;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	
	    setContentView(resLayoutID);
        
	       ((TextView)findViewById(resIntroID)).setOnClickListener(new View.OnClickListener() {

	    	   public void onClick(View v) {
					
	    		   Log.e(TAG,"onClick");
	    		   Intent i = new Intent(NewPhotoBook.this,PhotoBook.class);
	    		   startActivity(i);
	    		   
	    		   
				}
	       });
	}

}
