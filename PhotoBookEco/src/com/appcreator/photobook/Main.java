package com.appcreator.photobook;

import java.io.File;
import java.util.Date;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
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
    		if (!BasicInfo.ExternalChecked && externalPath != null) {
    			BasicInfo.ExternalPath = externalPath + File.separator;
    			Log.d(TAG, "ExternalPath : " + BasicInfo.ExternalPath);

    			BasicInfo.FOLDER_PHOTO = BasicInfo.ExternalPath + BasicInfo.FOLDER_PHOTO;
    			//BasicInfo.FOLDER_VIDEO = BasicInfo.ExternalPath + BasicInfo.FOLDER_VIDEO;
    			//BasicInfo.FOLDER_VOICE = BasicInfo.ExternalPath + BasicInfo.FOLDER_VOICE;
    			//BasicInfo.FOLDER_HANDWRITING = BasicInfo.ExternalPath + BasicInfo.FOLDER_HANDWRITING;
    			BasicInfo.DATABASE_NAME = BasicInfo.ExternalPath + BasicInfo.DATABASE_NAME;

    			BasicInfo.ExternalChecked = true;
    		}
    	}
        
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
    
    /**
     * 메모 리스트 데이터 로딩
     */
    public int loadMemoListData() {
     	String SQL = "select _id, INPUT_DATE, CONTENT_TEXT, ID_PHOTO, ID_VIDEO, ID_VOICE, ID_HANDWRITING from MEMO order by INPUT_DATE desc";

     	int recordCount = -1;
     	if (this.mDatabase != null) {
	   		Cursor outCursor = this.mDatabase.rawQuery(SQL);

	   		recordCount = outCursor.getCount();
			Log.d(TAG, "cursor count : " + recordCount + "\n");

			mMemoListAdapter.clear();

			for (int i = 0; i < recordCount; i++) {
				outCursor.moveToNext();

				String memoId = outCursor.getString(0);

				String dateStr = outCursor.getString(1);
				if (dateStr != null && dateStr.length() > 10) {
					//dateStr = dateStr.substring(0, 10);
					try {
						Date inDate = BasicInfo.dateFormat.parse(dateStr);
						dateStr = BasicInfo.dateNameFormat2.format(inDate);
					} catch(Exception ex) {
						ex.printStackTrace();
					}
				} else {
					dateStr = "";
				}

				String memoStr = outCursor.getString(2);
				String photoId = outCursor.getString(3);
				String photoUriStr = getPhotoUriStr(photoId);

				String videoId = outCursor.getString(4);
				String videoUriStr = null;

				String voiceId = outCursor.getString(5);
				String voiceUriStr = null;

				String handwritingId = outCursor.getString(6);
				String handwritingUriStr = null;

				//Stage3 added
				//handwritingUriStr = getHandwritingUriStr(handwritingId);

				// Stage4 added
				//videoUriStr = getVideoUriStr(videoId);
				//voiceUriStr = getVoiceUriStr(voiceId);


				mMemoListAdapter.addItem(new MemoListItem("1", "2012-06-10 18:09", "오늘은 코딩하는 날", null, null));
				
			}

			outCursor.close();

			mMemoListAdapter.notifyDataSetChanged();
	   }

	   return recordCount;
    }
    
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
//		intent.putExtra(BasicInfo.KEY_MEMO_MODE, BasicInfo.MODE_VIEW);
//		intent.putExtra(BasicInfo.KEY_MEMO_ID, item.getId());
//		intent.putExtra(BasicInfo.KEY_MEMO_DATE, item.getData(0));
//		intent.putExtra(BasicInfo.KEY_MEMO_TEXT, item.getData(1));
//
//		intent.putExtra(BasicInfo.KEY_ID_HANDWRITING, item.getData(2));
//		intent.putExtra(BasicInfo.KEY_URI_HANDWRITING, item.getData(3));
//
//		intent.putExtra(BasicInfo.KEY_ID_PHOTO, item.getData(4));
//		intent.putExtra(BasicInfo.KEY_URI_PHOTO, item.getData(5));
//
//		intent.putExtra(BasicInfo.KEY_ID_VIDEO, item.getData(6));
//		intent.putExtra(BasicInfo.KEY_URI_VIDEO, item.getData(7));
//
//		intent.putExtra(BasicInfo.KEY_ID_VOICE, item.getData(8));
//		intent.putExtra(BasicInfo.KEY_URI_VOICE, item.getData(9));
//
//		startActivityForResult(intent, BasicInfo.REQ_VIEW_ACTIVITY);
    }

}
