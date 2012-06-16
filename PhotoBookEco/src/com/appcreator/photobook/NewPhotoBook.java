package com.appcreator.photobook;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Date;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.appcreator.photobook.db.PhotoBookDB;

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
	boolean isPhotoFileSaed;
	
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
	
	String memoId = null;
	String memoDate = null;
	String memoText = "값을 입력하세요";
	String id_photo = null;
	String uri_photo = null;
	
	/*
	 * 완료
	 */
	Button completeBtn;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	
	    setContentView(resLayoutID);

		Log.d("where", "onCreate");
//	    mPhoto = (ImageView)findViewById(R.id.itemPhoto);
//	    mText = (TextView)findViewById(R.id.itemText);
	    mMemoListView = (ListView)findViewById(R.id.list_content);
	    mMemoListAdapter = new MemoListAdapter(this);
	    mMemoListView.setAdapter(mMemoListAdapter);
	    
	    MemoListItem item = new MemoListItem(memoId, memoDate, memoText, id_photo, uri_photo);
	    mMemoListAdapter.addItem(item);
	    mMemoListView.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> arg0, View view, int position, long arg3) {
				// TODO Auto-generated method stub
//				view_memo(position);
				Log.d("where", "dfasdf");
				Intent intent = new Intent(getApplicationContext(), Modify.class); 
                intent.putExtra(BasicDefine.KEY_MEMO_MODE, BasicDefine.MODE_INSERT); 
                startActivityForResult(intent, BasicDefine.REQ_INSERT_ACTIVITY); 

				
				/*view.findViewById(R.id.itemPhoto).setOnClickListener(new OnClickListener() {
					
					public void onClick(View arg0) {
						// TODO Auto-generated method stub
//						if(isPhotoCaptured || isPhotoFileSaved)
					
						showDialog(BasicDefine.CONTENT_PHOTO);
					}
				});
				view.findViewById(R.id.itemText).setOnClickListener(new OnClickListener() {
					
					public void onClick(View v) {
						// TODO Auto-generated method stub
//						showDialog(BasicDefine.CONFIRM_TEXT_INPUT); // 아무값이 없을 때 (텍스트 창)다잉알로그
//						memoText = mText.getText().toString();
//						mText.setText(memoText);
							
						
					}
				});*/
				
			}
		});
	   
	    
	    completeBtn = (Button)findViewById(R.id.completeBtn);
	    completeBtn.setOnClickListener(new OnClickListener() {
			
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
			}
		});
	    
//	    MemoListItem item = new MemoListItem(memoId, memoDate, memoText, id_photo, uri_photo);
//	    mMemoListAdapter.addItem(item);
//	    
//	    mMemoListAdapter.notifyDataSetChanged();
	    
	    
        
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
	
	public void showPhotoCaptureActivity(){//사진 찍기
		Intent intent = new Intent(getApplicationContext(), PhotoCaptureActivity.class);
		startActivityForResult(intent, BasicDefine.REQ_PHOTO_CAPTURE_ACTIVITY);
	}
	public void showPhotoLoadingActivity(){//사진 불러오기
		Intent intent = new Intent(getApplicationContext(), PhotoSelectionActivity.class);
		startActivityForResult(intent, BasicDefine.REQ_PHOTO_SELECTION_ACTIVITY);
	}
	
	
	 private void view_memo(int position){
		 MemoListItem item = (MemoListItem)mMemoListAdapter.getItem(position);
		 
		 Intent intent = new Intent(getApplicationContext(), Modify.class);
		 intent.putExtra(BasicDefine.KEY_MEMO_MODE, BasicDefine.MODE_VIEW);
		 intent.putExtra(BasicDefine.KEY_MEMO_ID, item.getId());
		 intent.putExtra(BasicDefine.KEY_MEMO_DATE, item.getData(0));
		 intent.putExtra(BasicDefine.KEY_MEMO_TEXT, item.getData(1));
		 intent.putExtra(BasicDefine.KEY_ID_PHOTO, item.getData(2));
		 intent.putExtra(BasicDefine.KEY_URI_PHOTO, item.getData(3));
		 
		 startActivityForResult(intent, BasicDefine.REQ_VIEW_ACTIVITY);
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

    /** 
     * 사진 데이터 URI 가져오기 
     */
    public String getPhotoUriStr(String id_photo) { 
        String photoUriStr = null; 
        if (id_photo != null && !id_photo.equals("-1")) { 
            String SQL = "select URI from " + PhotoBookDB.TABLE_PHOTO + " where _ID = " + id_photo + ""; 
            Cursor photoCursor = Main.mDatabase.rawQuery(SQL); 
            if (photoCursor.moveToNext()) { 
                photoUriStr = photoCursor.getString(0); 
            } 
            photoCursor.close(); 
        } else if(id_photo == null || id_photo.equals("-1")) { 
            photoUriStr = ""; 
        } 
  
        return photoUriStr; 
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
