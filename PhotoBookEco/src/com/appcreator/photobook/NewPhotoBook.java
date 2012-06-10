package com.appcreator.photobook;

import java.io.File;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
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
	final int resLayoutID 	= R.layout.temp; 
	int mSelectedContentArray = -1;
	int mChoicedArrayItem = 0;
	
	boolean isPhotoCanceled;
	boolean isPhotoCaptured;
	
	Bitmap resultPhotoBitmap;
	String tempPhotoUri;
	
	
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
	
	/**
	 * 리스트 이미지
	 */
	ImageView mPhoto;
	
	/**
	 * 리스트 텍스트
	 */
	TextView mText;
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	
	    setContentView(resLayoutID);
	    
	    mPhoto = (ImageView)findViewById(R.id.itemPhoto);
	    mText = (TextView)findViewById(R.id.itemText);
	    mMemoListView = (ListView)findViewById(R.id.list_content);
	    mMemoListAdapter = new MemoListAdapter(this);
	    mMemoListView.setAdapter(mMemoListAdapter);
	    mMemoListView.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> arg0, View view, int position, long arg3) {
				// TODO Auto-generated method stub
				view.findViewById(R.id.itemPhoto).setOnClickListener(new OnClickListener() {
					
					public void onClick(View arg0) {
						// TODO Auto-generated method stub
						showDialog(BasicDefine.CONTENT_PHOTO);
					}
				});
				
			}
		});
	    
	    MemoListItem item = new MemoListItem("1", "2012-06-10 10:20", "오늘은 코딩 날", null, null);
	    mMemoListAdapter.addItem(item);
	    
	    mMemoListAdapter.notifyDataSetChanged();
	    
	    
        
//	       ((TextView)findViewById(resIntroID)).setOnClickListener(new View.OnClickListener() {
//
//	    	   public void onClick(View v) {
//					
//	    		   Log.e(TAG,"onClick");
//	    		   Intent i = new Intent(NewPhotoBook.this,PhotoBook.class);
//	    		   startActivity(i);
//	    		   
//	    		   
//				}
//	       });
//	    mPhoto.setOnClickListener(new OnClickListener() {
//			
//			public void onClick(View arg0) {
//				// TODO Auto-generated method stub
////				showDialog();
//			}
//		});
	}
	protected Dialog onCreateDialog(int id){

AlertDialog.Builder builder = null;

		switch(id) {
			case BasicDefine.CONFIRM_TEXT_INPUT:
				builder = new AlertDialog.Builder(this);
				builder.setTitle("메모");
				builder.setMessage("텍스트를 입력하세요.");
				builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
	        	    public void onClick(DialogInterface dialog, int whichButton) {

                    }
                });

				break;

			case BasicDefine.CONTENT_PHOTO:
				builder = new AlertDialog.Builder(this);

				mSelectedContentArray = R.array.array_photo;
				builder.setTitle("선택하세요");
				builder.setSingleChoiceItems(mSelectedContentArray, 0, new DialogInterface.OnClickListener() {
	                public void onClick(DialogInterface dialog, int whichButton) {
	                	mChoicedArrayItem = whichButton;
	                }
	            });
				builder.setPositiveButton("선택", new DialogInterface.OnClickListener() {
	        	    public void onClick(DialogInterface dialog, int whichButton) {
	        	    	if(mChoicedArrayItem == 0 ) {
	        	    		showPhotoCaptureActivity();
	        	    	} else if(mChoicedArrayItem == 1) {
	        	    		showPhotoLoadingActivity();
	        	    	}
	                }
	            });
				builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
		             public void onClick(DialogInterface dialog, int whichButton) {

	                 }
	            });

				break;

			case BasicDefine.CONTENT_PHOTO_EX:
				builder = new AlertDialog.Builder(this);

				mSelectedContentArray = R.array.array_photo_ex;
				builder.setTitle("선택하세요");
				builder.setSingleChoiceItems(mSelectedContentArray, 0, new DialogInterface.OnClickListener() {
	                public void onClick(DialogInterface dialog, int whichButton) {
	                	mChoicedArrayItem = whichButton;
	                }
	            });
				builder.setPositiveButton("선택", new DialogInterface.OnClickListener() {
	        	    public void onClick(DialogInterface dialog, int whichButton) {
	        	    	if(mChoicedArrayItem == 0) {
	        	    		showPhotoCaptureActivity();
	        	    	} else if(mChoicedArrayItem == 1) {
	        	    		showPhotoLoadingActivity();
	        	    	} else if(mChoicedArrayItem == 2) {
	        	    		isPhotoCanceled = true;
	        	    		isPhotoCaptured = false;

	        	    		mPhoto.setImageResource(R.drawable.person);
	        	    	}
	                }
	            });
				builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
		             public void onClick(DialogInterface dialog, int whichButton) {

	                 }
	             });

				break;

			case BasicDefine.CONFIRM_DELETE:
				builder = new AlertDialog.Builder(this);
				builder.setTitle("메모");
				builder.setMessage("메모를 삭제하시겠습니까?");
				builder.setPositiveButton("예", new DialogInterface.OnClickListener() {
	        	    public void onClick(DialogInterface dialog, int whichButton) {
//	        	    	deleteMemo();
                    }
                });
				builder.setNegativeButton("아니오", new DialogInterface.OnClickListener() {
		             public void onClick(DialogInterface dialog, int whichButton) {
		            	 dismissDialog(BasicDefine.CONFIRM_DELETE);
		             }
				});

				break;
//
//			case BasicDefine.CONTENT_VIDEO:
//				builder = new AlertDialog.Builder(this);
//
//				mSelectedContentArray = R.array.array_video;
//				builder.setTitle("선택하세요");
//				builder.setSingleChoiceItems(mSelectedContentArray, 0, new DialogInterface.OnClickListener() {
//	                public void onClick(DialogInterface dialog, int whichButton) {
//	                	Log.d(TAG, "whichButton1 : " + whichButton);
//	                	mChoicedArrayItem = whichButton;
//	                }
//	            });
//				builder.setPositiveButton("선택", new DialogInterface.OnClickListener() {
//	        	    public void onClick(DialogInterface dialog, int whichButton) {
//	        	    	Log.d(TAG, "Selected Index : " + mChoicedArrayItem);
//
//	        	    	if(mChoicedArrayItem == 0) {
//	        	    		showVideoRecordingActivity();
//	        	    	} else if(mChoicedArrayItem == 1) {
//	        	    		showVideoLoadingActivity();
//	        	    	}
//	                }
//	            });
//				builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
//		             public void onClick(DialogInterface dialog, int whichButton) {
//
//	                 }
//	             });
//
//				break;
//
//			case BasicDefine.CONTENT_VIDEO_EX:
//				builder = new AlertDialog.Builder(this);
//
//				mSelectedContentArray = R.array.array_video_ex;
//				builder.setTitle("선택하세요");
//				builder.setSingleChoiceItems(mSelectedContentArray, 0, new DialogInterface.OnClickListener() {
//	                public void onClick(DialogInterface dialog, int whichButton) {
//	                	Log.d(TAG, "whichButton1 : " + whichButton);
//	                	mChoicedArrayItem = whichButton;
//	                }
//	            });
//				builder.setPositiveButton("선택", new DialogInterface.OnClickListener() {
//	        	    public void onClick(DialogInterface dialog, int whichButton) {
//	        	    	Log.d(TAG, "Selected Index : " + mChoicedArrayItem);
//	        	    	if(mChoicedArrayItem == 0) {
//	        	    		showVideoPlayingActivity();
//	        	    	} else if(mChoicedArrayItem == 1) {
//	        	    		showVideoRecordingActivity();
//	        	    	} else if(mChoicedArrayItem == 2) {
//	        	    		showVideoLoadingActivity();
//	        	    	} else if(mChoicedArrayItem == 3) {
//	        	    		isVideoCanceled = true;
//	        	    		isVideoRecorded = false;
//
//	        	    		mVideoBtn.setCompoundDrawablesWithIntrinsicBounds(null, getResources().getDrawable
//	        	        			(R.drawable.icon_video_empty), null, null);
//	        	    	}
//	                }
//	            });
//				builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
//		             public void onClick(DialogInterface dialog, int whichButton) {
//
//	                 }
//	             });
//
//				break;
//
//			case BasicDefine.CONTENT_VOICE:
//				builder = new AlertDialog.Builder(this);
//
//				mSelectedContentArray = R.array.array_voice;
//				builder.setTitle("선택하세요");
//				builder.setSingleChoiceItems(mSelectedContentArray, 0, new DialogInterface.OnClickListener() {
//	                public void onClick(DialogInterface dialog, int whichButton) {
//	                	Log.d(TAG, "whichButton1 : " + whichButton);
//	                	mChoicedArrayItem = whichButton;
//	                }
//	            });
//				builder.setPositiveButton("선택", new DialogInterface.OnClickListener() {
//	        	    public void onClick(DialogInterface dialog, int whichButton) {
//	        	    	Log.d(TAG, "whichButton2        ======        " + whichButton);
//	        	    	if(mChoicedArrayItem == 0 ){
//	        	    		showVoiceRecordingActivity();
//	        	    	}
//	                }
//	            });
//				builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
//		             public void onClick(DialogInterface dialog, int whichButton) {
//
//	                 }
//	             });
//
//				break;

//			case BasicDefine.CONTENT_VOICE_EX:
//				builder = new AlertDialog.Builder(this);
//
//				mSelectedContentArray = R.array.array_voice_ex;
//				builder.setTitle("선택하세요");
//				builder.setSingleChoiceItems(mSelectedContentArray, 0, new DialogInterface.OnClickListener() {
//	                public void onClick(DialogInterface dialog, int whichButton) {
//	                	Log.d(TAG, "whichButton1 : " + whichButton);
//	                	mChoicedArrayItem = whichButton;
//	                }
//	            });
//				builder.setPositiveButton("선택", new DialogInterface.OnClickListener() {
//	        	    public void onClick(DialogInterface dialog, int whichButton) {
//	        	    	Log.d(TAG, "Selected Index : " + mChoicedArrayItem);
//
//	        	    	if(mChoicedArrayItem == 0 ) {
//	        	    		showVoicePlayingActivity();
//	        	    	} else if(mChoicedArrayItem == 1) {
//	        	    		isVoiceCanceled = true;
//	        	    		isVoiceRecorded = false;
//
//	        	    		mVoiceBtn.setCompoundDrawablesWithIntrinsicBounds(null, getResources().getDrawable
//	        	        			(R.drawable.icon_voice_empty), null, null);
//	        	    	}
//	                }
//	            });
//				builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
//		             public void onClick(DialogInterface dialog, int whichButton) {
//
//	                 }
//	             });
//
//				break;

			default:
				break;
		}

		return builder.create();
	}
	
	public void showPhotoCaptureActivity(){
		Intent intent = new Intent(getApplicationContext(), PhotoCaptureActivity.class);
		startActivityForResult(intent, BasicDefine.REQ_PHOTO_CAPTURE_ACTIVITY);
	}
	public void showPhotoLoadingActivity(){
//		Intent intent = new Intent(getApplicationContext(), PhotoLoadingActivity.class);
//		startActivityForResult(intent, BasicDefine.REQ_PHOTO_CAPTURE_ACTIVITY);
	}
	protected void onActivityResult(int requestCode, int resultCode, Intent intent){
		super.onActivityResult(requestCode, resultCode, intent);
		
		switch(requestCode){
		case BasicDefine.REQ_PHOTO_CAPTURE_ACTIVITY :
			if (resultCode == RESULT_OK) {

				boolean isPhotoExists = checkCapturedPhotoFile();
		    	if (isPhotoExists) {
		    		Log.d(TAG, "image file exists : " + BasicDefine.FOLDER_PHOTO + "captured");

		    		resultPhotoBitmap = BitmapFactory.decodeFile(BasicDefine.FOLDER_PHOTO + "captured");

		    		tempPhotoUri = "captured";

		    		mPhoto.setImageBitmap(resultPhotoBitmap);
		            isPhotoCaptured = true;

		            mPhoto.invalidate();
		    	} else {
		    		Log.d(TAG, "image file doesn't exists : " + BasicDefine.FOLDER_PHOTO + "captured");
		    	}
			}

			break;
		}
	}

	/**
     * 저장된 사진 파일 확인
     */
    private boolean checkCapturedPhotoFile() {
    	File file = new File(BasicDefine.FOLDER_PHOTO + "captured");
    	if(file.exists()) {
    		return true;
    	}

    	return false;
    }
}
