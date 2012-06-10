package com.appcreator.photobook;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MemoListItemView extends LinearLayout {
	
	/*
	 * 외장 메모리 패스
	 */
	public static String ExternalPath = "/sdcard/";

	/**
	 * 외장 메모리 패스 체크 여부
	 */
	public static boolean ExternalChecked = false;

	/**
	 * 사진 저장 위치
	 */
	public static String FOLDER_PHOTO 		= "MultimediaMemo/photo/";

	/**
	 * 동영상 저장 위치
	 */
	public static String FOLDER_VIDEO 		= "MultimediaMemo/video/";

	/**
	 * 녹음 저장 위치
	 */
	public static String FOLDER_VOICE 		= "MultimediaMemo/voice/";

	/**
	 * 손글씨 저장 위치
	 */
	public static String FOLDER_HANDWRITING 	= "MultimediaMemo/handwriting/";

	/**
	 * 미디어 포맷
	 */
	public static final String URI_MEDIA_FORMAT		= "content://media";

	private ImageView itemPhoto;

	private TextView itemDate;

	private TextView itemText;

	private ImageView itemVideoState;

	private ImageView itemVoiceState;

	private ImageView itemHandwriting;

	private Context mContext;

	private String mVideoUri;

	private String mVoiceUri;

	public MemoListItemView(Context context) {
		super(context);
		mContext = context;

		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.main, this, true);

		itemPhoto = (ImageView) findViewById(R.id.itemPhoto);

		itemDate = (TextView) findViewById(R.id.itemDate);

		itemText = (TextView) findViewById(R.id.itemText);

		itemHandwriting = (ImageView) findViewById(R.id.itemHandwriting);

//		itemVideoState = (ImageView) findViewById(R.id.itemVideoState);
//		itemVideoState.setOnClickListener(new OnClickListener() {
//			public void onClick(View v) {
//				if(mVideoUri != null && mVideoUri.trim().length() > 0 && !mVideoUri.equals("-1")) {
//					showVideoPlayingActivity();
//				} else {
//					Toast.makeText(mContext, "����� �������� ����ϴ�.", Toast.LENGTH_SHORT).show();
//				}
//			}
//		});
//
//		itemVoiceState = (ImageView) findViewById(R.id.itemVoiceState);
//		itemVoiceState.setOnClickListener(new OnClickListener() {
//			public void onClick(View v) {
//				if(mVoiceUri != null && mVoiceUri.trim().length() > 0 && !mVoiceUri.equals("-1")) {
//					showVoicePlayingActivity();
//				} else {
//					Toast.makeText(mContext, "����� ������ ����ϴ�.", Toast.LENGTH_SHORT).show();
//				}
//			}
//		});

	}


//	public void showVoicePlayingActivity() {
//		Intent intent = new Intent(mContext, VoicePlayActivity.class);
//		intent.putExtra(BasicInfo.KEY_URI_VOICE, BasicInfo.FOLDER_VOICE + mVoiceUri);
//
//		mContext.startActivity(intent);
//	}
//
//	public void showVideoPlayingActivity() {
//		Intent intent = new Intent(mContext, VideoPlayActivity.class);
//		if(BasicInfo.isAbsoluteVideoPath(mVideoUri)) {
//			intent.putExtra(BasicInfo.KEY_URI_VIDEO, BasicInfo.FOLDER_VIDEO + mVideoUri);
//		} else {
//			intent.putExtra(BasicInfo.KEY_URI_VIDEO, mVideoUri);
//		}
//
//		mContext.startActivity(intent);
//	}

	public void setContents(int index, String data) {
		if (index == 0) {
			itemDate.setText(data);
		} else if (index == 1) {
			itemText.setText(data);
		} else if (index == 2) {
			if (data == null || data.equals("-1") || data.equals("")) {
				itemHandwriting.setImageBitmap(null);
			} else {
				itemHandwriting.setImageURI(Uri.parse(FOLDER_HANDWRITING + data));
			}
		} else if (index == 3) {
			if (data == null || data.equals("-1") || data.equals("")) {
				itemPhoto.setImageResource(R.drawable.nike);
			} else {
				itemPhoto.setImageURI(Uri.parse(FOLDER_PHOTO + data));
			}
		} else {
			throw new IllegalArgumentException();
		}
	}

//	public void setMediaState(String sVideoUri, String sVoiceUri) {
//		mVideoUri = sVideoUri;
//		mVoiceUri = sVoiceUri;
//
//		if(sVideoUri == null || sVideoUri.trim().length() < 1 || sVideoUri.equals("-1")) {
//			itemVideoState.setImageResource(R.drawable.icon_video_empty);
//		} else {
//			itemVideoState.setImageResource(R.drawable.icon_video);
//		}
//
//		if(sVoiceUri == null || sVoiceUri.trim().length() < 1 || sVoiceUri.equals("-1")) {
//			itemVoiceState.setImageResource(R.drawable.icon_voice_empty);
//		} else {
//			itemVoiceState.setImageResource(R.drawable.icon_voice);
//		}
//	}

}
