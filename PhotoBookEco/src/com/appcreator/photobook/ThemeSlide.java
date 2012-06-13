package com.appcreator.photobook;

import android.app.Activity;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;

/*
 * 2012. 06. 02
 * 슬라이드=>재빈
 */

public class ThemeSlide extends Activity {

	final String TAG = "ThemeSlide";
	
	final int resIntroID 	= R.id.intro;
	final int resLayoutID 	= R.layout.themeslide;

	private int current;
	private int length;
	
//    Integer[] images = { R.drawable.test1, R.drawable.test2, R.drawable.test3,
//            R.drawable.test4, R.drawable.test5, R.drawable.test6 };
    
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	
	    setContentView(resLayoutID);
	    
    
//        try
//        {
//	        for( int i=0; i < 6; i++ )
//	        {
//	        	Thread.sleep(100000); 
//		    }
//        }
//        catch(InterruptedException e) {
//        	e.printStackTrace(); 
//        }	
	    current = 0;
	   // showSlide(current);
        
//	       ((TextView)findViewById(resIntroID)).setOnClickListener(new View.OnClickListener() {
//
//	    	   public void onClick(View v) {
//					
//	    		   Log.e(TAG,"onClick");
//	    		   Intent i = new Intent(ThemeSlide.this,PhotoBook.class);
//	    		   startActivity(i);
//	    		   
//	    		   
//				}
//	       });
	}
	
    public void showSlide(int i) {

    	
    	
    	//갤러리에서 선택된 이미지를 보여주기 위한 ImageView를 가지고 온다.
        //ImageView imageView = (ImageView) findViewById(R.id.imageView1);
    
        //ImageView에 표시되는 이미지를 가로, 세로 비율에 맞게 보여질 수 있도록 설정한다.
        //imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
        
        //사용자가 선택한 위치에 있는 이미지를 ImageView에 설정한다.
        //사용자가 선택한 이미지의 위치는 position으로 알 수 있으며
        //이를 통해 배열에서 적절한 이미지를 얻으면 된다.
        //imageView.setImageResource(images[0]);
       	//imageView.setImageResource(images[i]);
       	


    	
    	
    	
       	
       	
       	
       	
       	
       	
        //managedQuery 메소드에서 조회할 컬럼 이름을 지정한다.
        //이미지 데이터를 지정한다.
        String[] proj = { MediaStore.Images.Media.DATA };
        
        //외부 메모리에 있는 이미지 정보를 조회한다.        
        Cursor dataCursor = managedQuery(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI, //외부 메모리 
                proj, //조회할 컬럼
                null, //WHERE절, 조건 지정
                null, //WHERE절, 선택 인자 지정
                null); //Order by절, 정렬 순서 지정
        
        length = dataCursor.getCount();
        
        //데이터에 해당하는 컬럼 인덱스를 얻는다.
        int columnIndex = dataCursor
                .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);

        //갤러리에서 사용자가 선택한 아이템에 해당하는 위치로 커서를 이동한다.
        dataCursor.moveToPosition(i);

        //지정된 인덱스에 해당하는 이미지 파일 이름을 얻는다. 
        String filename = dataCursor.getString(columnIndex);

        
        //파일 이름으로 비트맵을 얻는다. 
        Bitmap bitmap = BitmapFactory.decodeFile(filename);

        //비트맵 이미지를 이미지뷰에 설정한다.
        ImageView imageView = (ImageView) findViewById(R.id.imageView1);
        imageView.setImageBitmap(bitmap);
        
        dataCursor.close();       	
    }
    
    public void onClickLeftButton( View v ) {
    	
    	if( this.current > 0 ) 
    	{
    		this.current--;
    		//showSlide(this.current);
    		
    	}
    }
    
    
    public void onClickRightButton( View v ) {
    	
    	if( this.current < length-1 ) 
    	{
    		this.current++;
    		//showSlide(this.current);
    		
    	}    	
    }
  
}
