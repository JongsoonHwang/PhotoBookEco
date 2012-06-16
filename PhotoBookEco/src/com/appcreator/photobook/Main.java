package com.appcreator.photobook;

import java.io.File;
import java.util.Date;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.appcreator.photobook.db.PhotoBookDB;

/*
 * 2012. 06. 02
 * List(expanded, 더미)=>재빈(Listview+이미지뷰:맨하단), 
 * 다음주==>
 * 광고
 */



public class Main extends CommonActivity {

	final String TAG = "Main";
	
	final int resIntroID 	= R.id.intro;
	final int resLayoutID 	= R.layout.main;
	
	/**
	 * 데이터베이스 인스턴스
	 */
	public static PhotoBookDB mDatabase = null;
	
	
	/**
	 * 메모 리스트뷰
	 */
	ListView mMemoListView;

	/**
	 * 메모 리스트 어댑터
	 */
	MemoListAdapter mMemoListAdapter;

	/**
	 * 메모 갯수
	 */
	int mMemoCount = 0;
	
	//Intent를 위한 임시 버트
	Button btn;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	
	    setContentView(resLayoutID);
	    
		// SD Card checking
        if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
    		Toast.makeText(this, "SD 카드가 없습니다. SD 카드를 넣은 후 다시 실행하십시오.", Toast.LENGTH_LONG).show();
    		return;
    	} else {
    		String externalPath = Environment.getExternalStorageDirectory().getAbsolutePath();
    		if (!BasicDefine.ExternalChecked && externalPath != null) {
    			BasicDefine.ExternalPath = externalPath + File.separator;
    			Log.d("where", "ExternalPath : " + BasicDefine.ExternalPath);

    			BasicDefine.FOLDER_PHOTO = BasicDefine.ExternalPath + BasicDefine.FOLDER_PHOTO;
    			BasicDefine.FOLDER_VIDEO = BasicDefine.ExternalPath + BasicDefine.FOLDER_VIDEO;
    			BasicDefine.FOLDER_VOICE = BasicDefine.ExternalPath + BasicDefine.FOLDER_VOICE;
    			BasicDefine.FOLDER_HANDWRITING = BasicDefine.ExternalPath + BasicDefine.FOLDER_HANDWRITING;
    			BasicDefine.DATABASE_NAME = BasicDefine.ExternalPath + BasicDefine.DATABASE_NAME;

    			BasicDefine.ExternalChecked = true;
    		}
    	}
        btn = (Button)findViewById(R.id.titleBackgroundBtn);
        btn.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(getApplicationContext(), Modify.class); 
                intent.putExtra(BasicDefine.KEY_MEMO_MODE, BasicDefine.MODE_INSERT); 
                startActivityForResult(intent, BasicDefine.REQ_INSERT_ACTIVITY);
			
			}
		});
        
	    // 메모 리스트
        mMemoListView = (ListView)findViewById(R.id.memoList);
    	mMemoListAdapter = new MemoListAdapter(this);
    	
    	mMemoListView.setAdapter(mMemoListAdapter);
    	mMemoListView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
				viewMemo(position);
			}
		});
    	
    
	}
	
	protected void onStart() {

        // 데이터베이스 열기
        openDatabase();

        // 메모 데이터 로딩
        loadMemoListData();


		super.onStart();
	}
	
	/**
     * 데이터베이스 열기 (데이터베이스가 없을 때는 만들기)
     */
    public void openDatabase() {
		// open database
    	if (mDatabase != null) {
    		mDatabase.close();
    		mDatabase = null;
    	}

    	mDatabase = PhotoBookDB.getInstance(this);
    	boolean isOpen = mDatabase.open();
    	if (isOpen) {
    		Log.d(TAG, "Memo database is open.");
    	} else {
    		Log.d(TAG, "Memo database is not open.");
    	}
    }
    
//    /**
//     * 메모 리스트 데이터 로딩
//     */
//    public int loadMemoListData() {
//     	String SQL = "select _id, INPUT_DATE, CONTENT_TEXT, ID_PHOTO from MEMO order by INPUT_DATE desc";
////     	String SQL = "select _id, INPUT_DATE, CONTENT_TEXT, ID_PHOTO, ID_VIDEO, ID_VOICE, ID_HANDWRITING from MEMO order by INPUT_DATE desc";
//
//     	int recordCount = -1;
//     	if (this.mDatabase != null) {
//	   		Cursor outCursor = this.mDatabase.rawQuery(SQL);
//
//	   		recordCount = outCursor.getCount();
//			Log.d(TAG, "cursor count : " + recordCount + "\n");
//
//			mMemoListAdapter.clear();
//
//			for (int i = 0; i < recordCount; i++) {
//				outCursor.moveToNext();
//
//				String memoId = outCursor.getString(0);
//
//				String dateStr = outCursor.getString(1);
//				if (dateStr != null && dateStr.length() > 10) {
//					//dateStr = dateStr.substring(0, 10);
//					try {
//						Date inDate = BasicDefine.dateFormat.parse(dateStr);
//						dateStr = BasicDefine.dateNameFormat2.format(inDate);
//					} catch(Exception ex) {
//						ex.printStackTrace();
//					}
//				} else {
//					dateStr = "";
//				}
//
//				String memoStr = outCursor.getString(2);
//				String photoId = outCursor.getString(3);
//				String photoUriStr = getPhotoUriStr(photoId);
//
////				String videoId = outCursor.getString(4);
////				String videoUriStr = null;
////
////				String voiceId = outCursor.getString(5);
////				String voiceUriStr = null;
////
////				String handwritingId = outCursor.getString(6);
////				String handwritingUriStr = null;
//
//				//Stage3 added
//				//handwritingUriStr = getHandwritingUriStr(handwritingId);
//
//				// Stage4 added
//				//videoUriStr = getVideoUriStr(videoId);
//				//voiceUriStr = getVoiceUriStr(voiceId);
//
//
//				mMemoListAdapter.addItem(new MemoListItem(memoId, dateStr, memoStr, photoId, photoUriStr));
//				
//			}
//
//			outCursor.close();
//
//			mMemoListAdapter.notifyDataSetChanged();
//	   }
//
//	   return recordCount;
//    }
    
	/**
	 * 사진 데이터 URI 가져오기
	 */
	public String getPhotoUriStr(String id_photo) {
		String photoUriStr = null;
		if (id_photo != null && !id_photo.equals("-1")) {
			String SQL = "select URI from " + PhotoBookDB.TABLE_PHOTO + " where _ID = " + id_photo + "";
			Cursor photoCursor = this.mDatabase.rawQuery(SQL);
	    	if (photoCursor.moveToNext()) {
	    		photoUriStr = photoCursor.getString(0);
	    	}
	    	photoCursor.close();
		} else if(id_photo == null || id_photo.equals("-1")) {
			photoUriStr = "";
		}

		return photoUriStr;
	}
	
	private void viewMemo(int position) {
    	MemoListItem item = (MemoListItem)mMemoListAdapter.getItem(position);

//    	// 메모 보기 액티비티 띄우기
//		Intent intent = new Intent(getApplicationContext(), MemoInsertActivity.class);
//		intent.putExtra(BasicDefine.KEY_MEMO_MODE, BasicDefine.MODE_VIEW);
//		intent.putExtra(BasicDefine.KEY_MEMO_ID, item.getId());
//		intent.putExtra(BasicDefine.KEY_MEMO_DATE, item.getData(0));
//		intent.putExtra(BasicDefine.KEY_MEMO_TEXT, item.getData(1));
//
//		intent.putExtra(BasicDefine.KEY_ID_HANDWRITING, item.getData(2));
//		intent.putExtra(BasicDefine.KEY_URI_HANDWRITING, item.getData(3));
//
//		intent.putExtra(BasicDefine.KEY_ID_PHOTO, item.getData(4));
//		intent.putExtra(BasicDefine.KEY_URI_PHOTO, item.getData(5));
//
//		intent.putExtra(BasicDefine.KEY_ID_VIDEO, item.getData(6));
//		intent.putExtra(BasicDefine.KEY_URI_VIDEO, item.getData(7));
//
//		intent.putExtra(BasicDefine.KEY_ID_VOICE, item.getData(8));
//		intent.putExtra(BasicDefine.KEY_URI_VOICE, item.getData(9));
//
//		startActivityForResult(intent, BasicDefine.REQ_VIEW_ACTIVITY);
    }
	protected void onActivityResult(int requestCode, int resultCode, Intent intent){
		super.onActivityResult(requestCode, resultCode, intent);
		
		switch(requestCode){
		case BasicDefine.REQ_VIEW_ACTIVITY :
			loadMemoListData();
			break;
		case BasicDefine.REQ_INSERT_ACTIVITY: 
            if(resultCode == RESULT_OK) { 
                loadMemoListData(); 
            } 
  
            break;
//		case BasicDefine.REQ_PHOTO_CAPTURE_ACTIVITY :
//			if (resultCode == RESULT_OK) {
//
//				boolean isPhotoExists = checkCapturedPhotoFile();
//		    	if (isPhotoExists) {
//		    		Log.d(TAG, "image file exists : " + BasicDefine.FOLDER_PHOTO + "captured");
//		    		
//		    		BitmapFactory.Options opts = new BitmapFactory.Options();
//		    		opts.inSampleSize = 1;
//		    		
//
//		    		resultPhotoBitmap = BitmapFactory.decodeFile(BasicDefine.FOLDER_PHOTO + "captured", opts);
//
//		    		tempPhotoUri = "captured";
//
//		    		mPhoto.setImageBitmap(resultPhotoBitmap);
//		            isPhotoCaptured = true;
//
//		            mPhoto.invalidate();
//		    	} else {
//		    		Log.d(TAG, "image file doesn't exists : " + BasicDefine.FOLDER_PHOTO + "captured");
//		    	}
//			}
//
//			break;
//		case BasicDefine.REQ_PHOTO_SELECTION_ACTIVITY:  // 사진을 앨범에서 선택하는 경우
//			Log.d(TAG, "onActivityResult() for REQ_PHOTO_LOADING_ACTIVITY.");
//
//			if (resultCode == RESULT_OK) {
//				Log.d(TAG, "resultCode : " + resultCode);
//
//				Uri getPhotoUri = intent.getParcelableExtra(BasicDefine.KEY_URI_PHOTO);
//				try { 
//					
//					BitmapFactory.Options opts = new BitmapFactory.Options();
//		    		opts.inSampleSize = 1;
//					
//					resultPhotoBitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(getPhotoUri), null, opts);
//				} catch (FileNotFoundException e) {
//					e.printStackTrace();
//				}
//				if(resultPhotoBitmap == null){
//					Log.d("where", "resultPhotoBitmap : "+resultPhotoBitmap);
//				}
//
//				mPhoto.setImageBitmap(resultPhotoBitmap);
//	            isPhotoCaptured = true;
//
//	            mPhoto.invalidate();
//			}
//
//			break;
		}
	}
	public int loadMemoListData() { 
        String SQL = "select _id, INPUT_DATE, CONTENT_TEXT, ID_PHOTO from MEMO order by INPUT_DATE desc"; 
//        String SQL = "select _id, INPUT_DATE, CONTENT_TEXT, ID_PHOTO, ID_VIDEO, ID_VOICE, ID_HANDWRITING from MEMO order by INPUT_DATE desc"; 
        Log.d("where", "loadMemoListData 진입");
        int recordCount = -1; 
        if (Main.mDatabase != null) { 
        	
            Cursor outCursor = Main.mDatabase.rawQuery(SQL); 
            Log.d("where", "outCursor : "+outCursor.getCount());
            recordCount = outCursor.getCount(); 
            Log.d(TAG, "cursor count : " + recordCount + "\n"); 
  
            mMemoListAdapter.clear(); 
  
            for (int i = 0; i < recordCount; i++) {
            	Log.d("where", "포문 ");
                outCursor.moveToNext(); 
  
                String memoId = outCursor.getString(0); 
  
                String dateStr = outCursor.getString(1); 
                if (dateStr != null && dateStr.length() > 10) { 
                    //dateStr = dateStr.substring(0, 10); 
                    try { 
                        Date inDate = BasicDefine.dateFormat.parse(dateStr); 
                        dateStr = BasicDefine.dateNameFormat2.format(inDate); 
                    } catch(Exception ex) { 
                        ex.printStackTrace(); 
                    } 
                } else { 
                    dateStr = ""; 
                } 
  
                String memoStr = outCursor.getString(2); 
                String photoId = outCursor.getString(3); 
                String photoUriStr = getPhotoUriStr(photoId); 
                Log.d("where", "memoStr : "+memoStr+"  photoId : "+photoId +"  photoUriStr : "+photoUriStr);
  
//                String videoId = outCursor.getString(4); 
//                String videoUriStr = null; 
//  
//                String voiceId = outCursor.getString(5); 
//                String voiceUriStr = null; 
//  
//                String handwritingId = outCursor.getString(6); 
//                String handwritingUriStr = null; 
//  
//                // Stage3 added 
//                handwritingUriStr = getHandwritingUriStr(handwritingId); 
//  
//                // Stage4 added 
//                videoUriStr = getVideoUriStr(videoId); 
//                voiceUriStr = getVoiceUriStr(voiceId); 
  
  
//                mMemoListAdapter.addItem(new MemoListItem(memoId, dateStr, memoStr, handwritingId, handwritingUriStr, photoId, photoUriStr, videoId, videoUriStr, voiceId, voiceUriStr)); 
                mMemoListAdapter.addItem(new MemoListItem(memoId, dateStr, memoStr, photoId, photoUriStr)); 
            
            } 
           
  
            outCursor.close(); 
  
            mMemoListAdapter.notifyDataSetChanged(); 
       } 
        else{
        	Log.d("where", "데이터베이스 없당");
        	
        }
        return recordCount; 
    } 

}
