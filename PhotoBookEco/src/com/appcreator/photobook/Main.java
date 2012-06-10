package com.appcreator.photobook;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

/*
 * 2012. 06. 02
 * List(expanded, 더미)=>재빈(Listview+이미지뷰:맨하단), 
 * 다음주==>
 * 광고
 */

public class Main extends Activity {

	final String TAG = "Main";
	
	final int resIntroID 	= R.id.intro;
	final int resLayoutID 	= R.layout.main;
	
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
