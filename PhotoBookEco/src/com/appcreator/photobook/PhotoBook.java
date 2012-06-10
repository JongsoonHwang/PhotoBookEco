package com.appcreator.photobook;

import java.util.ArrayList;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import android.widget.Toast;

/*
 * 2012. 06. 02
 * TAB => 재빈, 슬라이드 => 재빈(themeslide 재사용함)
 * 일반룩, 카톡 룩 =>쎄라누나
 * 다음 주
 * SNS => 미니 리스트 , sns
 */


public class PhotoBook extends Activity {

	final String TAG = "PhotoBook";
	
	final int resIntroID 	= R.id.intro;
	final int resLayoutID 	= R.layout.photobook;
	
	ArrayList<String> arr = null;
	ArrayAdapter<String> adapter = null;
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	
	    setContentView(resLayoutID);
	    
	    arr = new ArrayList<String>();
        arr.add("apple");       // 동적 배열에 자료 넣기             
        arr.add("orange");
    
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arr);
        
        ListView listView = (ListView)findViewById(R.id.listview);
        listView.setAdapter(adapter);
	    
	    TabHost tabhost = (TabHost) findViewById(R.id.tabhost); 
	     tabhost.setup();
	     
	     TabSpec spec = tabhost.newTabSpec("Tab1");  
	     spec.setContent(R.id.txt1); 
	     spec.setIndicator("tab one");
	     tabhost.addTab(spec);
	     
	     
	     spec= tabhost.newTabSpec("Tab2"); 
	     spec.setContent(R.id.txt2);
	     spec.setIndicator("tab two");
	     tabhost.addTab(spec);
	     
	     Drawable icon = getResources().getDrawable(R.drawable.nike);
	     spec= tabhost.newTabSpec("Tab3"); 
	     spec.setContent(R.id.txt3);
	     spec.setIndicator("tab three", icon);
	     tabhost.addTab(spec);
	     
	     for(int i=0 ; i<tabhost.getTabWidget().getChildCount() ; i++){
	    	 tabhost.getTabWidget().getChildAt(i).getLayoutParams().height = 60;
	     }

//	       ((TextView)findViewById(resIntroID)).setOnClickListener(new View.OnClickListener() {
//
//	    	   public void onClick(View v) {
//					
//	    		   Log.e(TAG,"onClick");
//	    		   
//	    		   
//				}
//	       });
	     listView.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> arg0, View arg1, int position, long id) {
				// TODO Auto-generated method stub
				switch(position){
				case 0:
				case 1:
					
				}
				Toast.makeText(PhotoBook.this, arr.get(position), Toast.LENGTH_SHORT).show();
			}
		});
	}


}
