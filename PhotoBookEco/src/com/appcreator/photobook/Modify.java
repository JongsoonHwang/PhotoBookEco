package com.appcreator.photobook;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;

import com.appcreator.photobook.db.PhotoBookDB;

public class Modify extends Activity {
	
	public static final String TAGA = "Modify"; 
	
	BitmapBtn mPhotoBtn;
	EditText mMemoEdit; 
    ImageView mPhoto; 
  
    String mMemoMode; 
    String mMemoId; 
    String mMemoDate; 
  
    String mMediaPhotoId; 
    String mMediaPhotoUri; 
    String mMediaVideoId; 
    String mMediaVideoUri; 
    String mMediaVoiceId; 
    String mMediaVoiceUri; 
    String mMediaHandwritingId; 
    String mMediaHandwritingUri; 
  
    String tempPhotoUri; 
    String tempVideoUri; 
    String tempVoiceUri; 
    String tempHandwritingUri; 
  
    String mDateStr; 
    String mMemoStr; 
  
    Bitmap resultPhotoBitmap; 
  
    Bitmap resultHandwritingBitmap; 
    boolean isPhotoCaptured; 
    boolean isVideoRecorded; 
    boolean isVoiceRecorded; 
    boolean isHandwritingMade; 
  
    boolean isPhotoFileSaved; 
    boolean isVideoFileSaved; 
    boolean isVoiceFileSaved; 
    boolean isHandwritingFileSaved; 
  
    boolean isPhotoCanceled; 
    boolean isVideoCanceled; 
    boolean isVoiceCanceled; 
    boolean isHandwritingCanceled; 
  
    Calendar mCalendar = Calendar.getInstance(); 
    BitmapBtn insertDateButton; 
  
    int mSelectdContentArray; 
    int mChoicedArrayItem; 
  
    TitleBackgroundButton titleBackgroundBtn; 
    BitmapBtn insertSaveBtn; 
    BitmapBtn insertCancelBtn; 
    BitmapBtn insert_textBtn; 
    BitmapBtn insert_handwritingBtn; 
    BitmapBtn deleteBtn; 
  
    int textViewMode = 0; 
    EditText insert_memoEdit; 
    ImageView insert_handwriting; 
  
    Animation translateLeftAnim; 
    Animation translateRightAnim; 
  
    protected void onCreate(Bundle savedInstanceState) { 
        super.onCreate(savedInstanceState); 
        setContentView(R.layout.modify); 
  
        titleBackgroundBtn = (TitleBackgroundButton)findViewById(R.id.titleBackgroundBtn); 
        mPhoto = (ImageView)findViewById(R.id.insert_photo); 
        mMemoEdit = (EditText) findViewById(R.id.insert_memoEdit); 
  
//        insert_textBtn = (BitmapBtn)findViewById(R.id.insert_textBtn); 
//        insert_handwritingBtn = (BitmapBtn)findViewById(R.id.insert_handwritingBtn); 
        insert_memoEdit = (EditText)findViewById(R.id.insert_memoEdit); 
//        insert_handwriting = (ImageView)findViewById(R.id.insert_handwriting); 
        deleteBtn = (BitmapBtn)findViewById(R.id.deleteBtn); 
//        mVideoBtn = (BitmapBtn)findViewById(R.id.insert_videoBtn); 
//        mVoiceBtn = (BitmapBtn)findViewById(R.id.insert_voiceBtn); 
//  
//        mVideoBtn.setCompoundDrawablesWithIntrinsicBounds(null, getResources().getDrawable 
//                (R.drawable.icon_video), null, null); 
//  
//        mVoiceBtn.setCompoundDrawablesWithIntrinsicBounds(null, getResources().getDrawable 
//                (R.drawable.icon_voice), null, null); 
  
  
//        translateLeftAnim = AnimationUtils.loadAnimation(this, R.anim.translate_left); 
//        translateRightAnim = AnimationUtils.loadAnimation(this, R.anim.translate_right); 
//  
//        SlidingPageAnimationListener animListener = new SlidingPageAnimationListener(); 
//        translateLeftAnim.setAnimationListener(animListener); 
//        translateRightAnim.setAnimationListener(animListener); 
  
//        insert_textBtn.setSelected(true); 
//        insert_handwritingBtn.setSelected(false); 
//  
//        insert_textBtn.setOnClickListener(new OnClickListener() { 
//            public void onClick(View v) { 
//                if (textViewMode == 1) { 
//                    insert_handwriting.setVisibility(View.GONE); 
//                    insert_memoEdit.setVisibility(View.VISIBLE); 
//                    insert_memoEdit.startAnimation(translateLeftAnim); 
//  
//                    textViewMode = 0; 
//                    insert_textBtn.setSelected(true); 
//                    insert_handwritingBtn.setSelected(false); 
//                } 
//            } 
//        }); 
//  
//        insert_handwritingBtn.setOnClickListener(new OnClickListener() { 
//            public void onClick(View v) { 
//                if (textViewMode == 0) { 
//                    insert_handwriting.setVisibility(View.VISIBLE); 
//                    insert_memoEdit.setVisibility(View.GONE); 
//                    insert_handwriting.startAnimation(translateLeftAnim); 
//  
//                    textViewMode = 1; 
//                    insert_handwritingBtn.setSelected(true); 
//                    insert_textBtn.setSelected(false); 
//                } 
//            } 
//        }); 
  
        mPhoto.setOnClickListener(new OnClickListener() { 
            public void onClick(View v) { 
                if(isPhotoCaptured || isPhotoFileSaved) { 
                    showDialog(BasicDefine.CONTENT_PHOTO_EX); 
                } else { 
                    showDialog(BasicDefine.CONTENT_PHOTO); 
                } 
            } 
        }); 
  
//        insert_handwriting.setOnClickListener(new OnClickListener() { 
//            public void onClick(View v) { 
//                Intent intent = new Intent(getApplicationContext(), HandwritingMakingActivity.class); 
//                startActivityForResult(intent, BasicDefine.REQ_HANDWRITING_MAKING_ACTIVITY); 
//            } 
//        }); 
  
        deleteBtn.setOnClickListener(new OnClickListener() { 
            public void onClick(View v) { 
                showDialog(BasicDefine.CONFIRM_DELETE); 
            } 
        }); 
  
  
//        mVideoBtn.setOnClickListener(new OnClickListener() { 
//  
//            public void onClick(View v) { 
//                if(isVideoRecorded || isVideoFileSaved) 
//                { 
//                    showDialog(BasicDefine.CONTENT_VIDEO_EX); 
//                } 
//                else
//                { 
//                    showDialog(BasicDefine.CONTENT_VIDEO); 
//                } 
//            } 
//        }); 
//  
//        mVoiceBtn.setOnClickListener(new OnClickListener() { 
//  
//            public void onClick(View v) { 
//                if(isVoiceRecorded || isVoiceFileSaved) 
//                { 
//                    showDialog(BasicDefine.CONTENT_VOICE_EX); 
//                } 
//                else
//                { 
//                    showDialog(BasicDefine.CONTENT_VOICE); 
//                } 
//            } 
//        }); 
  
  
        setBottomButtons(); 
  
        setMediaLayout(); 
  
        setCalendar(); 
  
        Intent intent = getIntent(); 
        mMemoMode = intent.getStringExtra(BasicDefine.KEY_MEMO_MODE); 
        if(mMemoMode.equals(BasicDefine.MODE_MODIFY) || mMemoMode.equals(BasicDefine.MODE_VIEW)) { 
            processIntent(intent); 
  
            titleBackgroundBtn.setText("메모 보기"); 
            insertSaveBtn.setText("수정"); 
  
            deleteBtn.setVisibility(View.VISIBLE); 
        } else { 
            titleBackgroundBtn.setText("새 메모"); 
            insertSaveBtn.setText("저장"); 
  
            deleteBtn.setVisibility(View.GONE); 
        } 
    } 
  
    private class SlidingPageAnimationListener implements AnimationListener { 
  
        public void onAnimationEnd(Animation animation) { 
  
        } 
  
        public void onAnimationRepeat(Animation animation) { 
  
        } 
  
        public void onAnimationStart(Animation animation) { 
  
        } 
  
    } 
  
  
  
    public void processIntent(Intent intent) { 
        mMemoId = intent.getStringExtra(BasicDefine.KEY_MEMO_ID); 
        mMemoEdit.setText(intent.getStringExtra(BasicDefine.KEY_MEMO_TEXT)); 
        mMediaPhotoId = intent.getStringExtra(BasicDefine.KEY_ID_PHOTO); 
        mMediaPhotoUri = intent.getStringExtra(BasicDefine.KEY_URI_PHOTO); 
//        mMediaVideoId = intent.getStringExtra(BasicDefine.KEY_ID_VIDEO); 
//        mMediaVideoUri = intent.getStringExtra(BasicDefine.KEY_URI_VIDEO); 
//        mMediaVoiceId = intent.getStringExtra(BasicDefine.KEY_ID_VOICE); 
//        mMediaVoiceUri = intent.getStringExtra(BasicDefine.KEY_URI_VOICE); 
//        mMediaHandwritingId = intent.getStringExtra(BasicDefine.KEY_ID_HANDWRITING); 
//        mMediaHandwritingUri = intent.getStringExtra(BasicDefine.KEY_URI_HANDWRITING); 
  
        setMediaImage(mMediaPhotoId, mMediaPhotoUri); 
//        setMediaImage(mMediaPhotoId, mMediaPhotoUri, mMediaVideoId, mMediaVoiceId, mMediaHandwritingId); 
    } 
  
//    public void showVideoRecordingActivity() { 
//        Intent intent = new Intent(getApplicationContext(), VideoRecordingActivity.class); 
//        startActivityForResult(intent, BasicDefine.REQ_VIDEO_RECORDING_ACTIVITY); 
//    } 
//  
//    public void showVideoLoadingActivity() { 
//        Intent intent = new Intent(getApplicationContext(), VideoSelectionActivity.class); 
//        startActivityForResult(intent, BasicDefine.REQ_VIDEO_LOADING_ACTIVITY); 
//    } 
//  
//    public void showVideoPlayingActivity() { 
//        Intent intent = new Intent(getApplicationContext(), VideoPlayActivity.class); 
//        if(BasicDefine.isAbsoluteVideoPath(tempVideoUri)) { 
//            intent.putExtra(BasicDefine.KEY_URI_VIDEO, BasicDefine.FOLDER_VIDEO + tempVideoUri); 
//        } else { 
//            intent.putExtra(BasicDefine.KEY_URI_VIDEO, tempVideoUri); 
//        } 
//  
//        startActivity(intent); 
//    } 
  
//    public void showVoiceRecordingActivity() { 
//        Intent intent = new Intent(getApplicationContext(), VoiceRecordingActivity.class); 
//        startActivityForResult(intent, BasicDefine.REQ_VOICE_RECORDING_ACTIVITY); 
//    } 
//  
//    public void showVoicePlayingActivity() { 
//        Intent intent = new Intent(getApplicationContext(), VoicePlayActivity.class); 
//        intent.putExtra(BasicDefine.KEY_URI_VOICE, BasicDefine.FOLDER_VOICE + tempVoiceUri); 
//        startActivity(intent); 
//    } 
  
  
  
    public void setMediaImage(String photoId, String photoUri) { 
//    public void setMediaImage(String photoId, String photoUri, String videoId, String voiceId, String handwritingId) { 
        Log.d("where", "photoId : " + photoId + ", photoUri : " + photoUri); 
  
        if(photoId.equals("") || photoId.equals("-1")) { 
            mPhoto.setImageResource(R.drawable.person); 
        } else { 
            isPhotoFileSaved = true; 
            mPhoto.setImageURI(Uri.parse(BasicDefine.FOLDER_PHOTO + photoUri)); 
        } 
  
//        if(handwritingId.equals("") || handwritingId.equals("-1")) { 
//  
//        } else { 
//            isHandwritingFileSaved = true; 
//            tempHandwritingUri = mMediaHandwritingUri; 
//  
//            Bitmap resultBitmap = BitmapFactory.decodeFile(BasicDefine.FOLDER_HANDWRITING + tempHandwritingUri); 
//            insert_handwriting.setImageBitmap(resultBitmap); 
//        } 
//  
//        if(videoId.equals("") || videoId.equals("-1")) { 
//            mVideoBtn.setCompoundDrawablesWithIntrinsicBounds(null, getResources().getDrawable 
//                    (R.drawable.icon_video_empty), null, null); 
//        } else { 
//            isVideoFileSaved = true; 
//            tempVideoUri = mMediaVideoUri; 
//  
//            mVideoBtn.setCompoundDrawablesWithIntrinsicBounds(null, getResources().getDrawable 
//                    (R.drawable.icon_video), null, null); 
//        } 
//  
//        if(voiceId.equals("") || voiceId.equals("-1")) { 
//            mVoiceBtn.setCompoundDrawablesWithIntrinsicBounds(null, getResources().getDrawable 
//                    (R.drawable.icon_voice_empty), null, null); 
//        } else { 
//            isVoiceFileSaved = true; 
//            tempVoiceUri = mMediaVoiceUri; 
//  
//            mVoiceBtn.setCompoundDrawablesWithIntrinsicBounds(null, getResources().getDrawable 
//                    (R.drawable.icon_voice), null, null); 
//        } 
  
    } 
  
  
    /** 
     * 하단 메뉴 버튼 설정 
     */
    public void setBottomButtons() { 
        insertSaveBtn = (BitmapBtn)findViewById(R.id.insert_saveBtn); 
        insertCancelBtn = (BitmapBtn)findViewById(R.id.insert_cancelBtn); 
  
        // 저장 버튼 
        insertSaveBtn.setOnClickListener(new OnClickListener() { 
            public void onClick(View v) { 
                boolean isParsed = parseValues(); 
                if (isParsed) { 
                    if(mMemoMode.equals(BasicDefine.MODE_INSERT)) { 
                        saveInput(); 
                    } else if(mMemoMode.equals(BasicDefine.MODE_MODIFY) || mMemoMode.equals(BasicDefine.MODE_VIEW)) { 
                        modifyInput(); 
                    } 
                } 
            } 
        }); 
  
        // 닫기 버튼 
        insertCancelBtn.setOnClickListener(new OnClickListener() { 
            public void onClick(View v) { 
                finish(); 
            } 
        }); 
    } 
  
  
    /** 
     * 데이터베이스에 레코드 추가 
     */
    private void saveInput() { 
  
        String photoFilename = insertPhoto(); 
        int photoId = -1; 
  
        String SQL = null; 
  
        if (photoFilename != null) { 
            // query picture id 
            SQL = "select _ID from " + PhotoBookDB.TABLE_PHOTO + " where URI = '" + photoFilename + "'"; 
            Log.d("where", "SQL : " + SQL); 
            if (Main.mDatabase != null) { 
                Cursor cursor = Main.mDatabase.rawQuery(SQL); 
                if (cursor.moveToNext()) { 
                    photoId = cursor.getInt(0); 
                } 
                cursor.close(); 
            } 
        } 
  
  
//        String handwritingFileName = insertHandwriting(); 
//        int handwritingId = -1; 
//  
//        if (handwritingFileName != null) { 
//            // query picture id 
//            SQL = "select _ID from " + MemoDatabase.TABLE_HANDWRITING + " where URI = '" + handwritingFileName + "'"; 
//            Log.d("where", "SQL : " + SQL); 
//            if (MultiMemoActivity.mDatabase != null) { 
//                Cursor cursor = MultiMemoActivity.mDatabase.rawQuery(SQL); 
//                if (cursor.moveToNext()) { 
//                    handwritingId = cursor.getInt(0); 
//                } 
//                cursor.close(); 
//            } 
//        } 
//  
//  
//  
//        String videoFileName = insertVideo(); 
//        int videoId = -1; 
//  
//        if (videoFileName != null) { 
//            // query picture id 
//            SQL = "select _ID from " + MemoDatabase.TABLE_VIDEO + " where URI = '" + videoFileName + "'"; 
//            Log.d("where", "SQL : " + SQL); 
//            if (MultiMemoActivity.mDatabase != null) { 
//                Cursor cursor = MultiMemoActivity.mDatabase.rawQuery(SQL); 
//                if (cursor.moveToNext()) { 
//                    videoId = cursor.getInt(0); 
//                } 
//                cursor.close(); 
//            } 
//        } 
//  
//        String voiceFileName = insertVoice(); 
//        int voiceId = -1; 
//  
//        if (isVoiceRecorded && voiceFileName != null) { 
//            // query picture id 
//            SQL = "select _ID from " + MemoDatabase.TABLE_VOICE + " where URI = '" + voiceFileName + "'"; 
//            Log.d("where", "SQL : " + SQL); 
//            if (MultiMemoActivity.mDatabase != null) { 
//                Cursor cursor = MultiMemoActivity.mDatabase.rawQuery(SQL); 
//                if (cursor.moveToNext()) { 
//                    voiceId = cursor.getInt(0); 
//                } 
//                cursor.close(); 
//            } 
//        } 
  
  
  
  
        SQL = "insert into " + PhotoBookDB.TABLE_MEMO + 
//                    "(INPUT_DATE, CONTENT_TEXT, ID_PHOTO, ID_VIDEO, ID_VOICE, ID_HANDWRITING) values(" + 
                    "(INPUT_DATE, CONTENT_TEXT, ID_PHOTO) values(" + 
                    "DATETIME('" + mDateStr + "'), " + 
                    "'"+ mMemoStr + "', " + 
                    "'"+ photoId  + 
//                    "'"+ videoId + "', " + 
//                    "'"+ voiceId + "', " + 
//                    "'"+ handwritingId +
                    "')";          // S"where"e3 added 
  
        Log.d("where", "SQL : " + SQL); 
        if (Main.mDatabase != null) { 
            Main.mDatabase.execSQL(SQL); 
        } 
  
        Intent intent = getIntent(); 
        setResult(RESULT_OK, intent); 
        finish(); 
  
    } 
  
    /** 
     * 데이터베이스 레코드 수정 
     */
    private void modifyInput() { 
  
        Intent intent = getIntent(); 
  
        String photoFilename = insertPhoto(); 
        int photoId = -1; 
  
        String SQL = null; 
  
        if (photoFilename != null) { 
            // query picture id 
            SQL = "select _ID from " + PhotoBookDB.TABLE_PHOTO + " where URI = '" + photoFilename + "'"; 
            Log.d("where", "SQL : " + SQL); 
            if (Main.mDatabase != null) { 
                Cursor cursor = Main.mDatabase.rawQuery(SQL); 
                if (cursor.moveToNext()) { 
                    photoId = cursor.getInt(0); 
                } 
                cursor.close(); 
  
                mMediaPhotoUri = photoFilename; 
  
                SQL = "update " + PhotoBookDB.TABLE_MEMO + 
                    " set " + 
                    " ID_PHOTO = '" + photoId + "'" + 
                    " where _id = '" + mMemoId + "'"; 
  
                if (Main.mDatabase != null) { 
                	Main.mDatabase.rawQuery(SQL); 
                } 
  
                mMediaPhotoId = String.valueOf(photoId); 
            } 
        } else if(isPhotoCanceled && isPhotoFileSaved) { 
            SQL = "delete from " + PhotoBookDB.TABLE_PHOTO + 
                " where _ID = '" + mMediaPhotoId + "'"; 
            Log.d("where", "SQL : " + SQL); 
            if (Main.mDatabase != null) { 
            	Main.mDatabase.execSQL(SQL); 
            } 
  
            File photoFile = new File(BasicDefine.FOLDER_PHOTO + mMediaPhotoUri); 
            if (photoFile.exists()) { 
                photoFile.delete(); 
            } 
  
            SQL = "update " + PhotoBookDB.TABLE_MEMO + 
            " set " + 
            " ID_PHOTO = '" + photoId + "'" + 
            " where _id = '" + mMemoId + "'"; 
  
            if (Main.mDatabase != null) { 
            	Main.mDatabase.rawQuery(SQL); 
            } 
  
            mMediaPhotoId = String.valueOf(photoId); 
        } 
  
  
//        String handwritingFileName = insertHandwriting(); 
//        int handwritingId = -1; 
//  
//        if (handwritingFileName != null) { 
//            // query picture id 
//            SQL = "select _ID from " + MemoDatabase.TABLE_HANDWRITING + " where URI = '" + handwritingFileName + "'"; 
//            Log.d("where", "SQL : " + SQL); 
//            if (MultiMemoActivity.mDatabase != null) { 
//                Cursor cursor = MultiMemoActivity.mDatabase.rawQuery(SQL); 
//                if (cursor.moveToNext()) { 
//                    handwritingId = cursor.getInt(0); 
//                } 
//                cursor.close(); 
//  
//                mMediaHandwritingUri = handwritingFileName; 
//  
//                SQL = "update " + MemoDatabase.TABLE_MEMO + 
//                    " set " + 
//                    " ID_HANDWRITING = '" + handwritingId + "' " + 
//                    " where _id = '" + mMemoId + "'"; 
//  
//                if (MultiMemoActivity.mDatabase != null) { 
//                    MultiMemoActivity.mDatabase.rawQuery(SQL); 
//                } 
//  
//                mMediaHandwritingId = String.valueOf(handwritingId); 
//            } 
//        } else if(isHandwritingCanceled && isHandwritingFileSaved) { 
//            SQL = "delete from " + MemoDatabase.TABLE_HANDWRITING + 
//                " where _ID = '" + mMediaHandwritingId + "'"; 
//            Log.d("where", "SQL : " + SQL); 
//            if (MultiMemoActivity.mDatabase != null) { 
//                MultiMemoActivity.mDatabase.execSQL(SQL); 
//            } 
//  
//            File handwritingFile = new File(BasicDefine.FOLDER_HANDWRITING + mMediaHandwritingUri); 
//            if (handwritingFile.exists()) { 
//                handwritingFile.delete(); 
//            } 
//  
//            SQL = "update " + MemoDatabase.TABLE_MEMO + 
//            " set " + 
//            " ID_HANDWRITING = '" + handwritingId + "' " + 
//            " where _id = '" + mMemoId + "'"; 
//  
//            if (MultiMemoActivity.mDatabase != null) { 
//                MultiMemoActivity.mDatabase.rawQuery(SQL); 
//            } 
//  
//            mMediaHandwritingId = String.valueOf(handwritingId); 
//        } 
//  
//  
//        String videoFileName = insertVideo(); 
//        int videoId = -1; 
//  
//        if (videoFileName != null) { 
//            // query picture id 
//            SQL = "select _ID from " + MemoDatabase.TABLE_VIDEO + " where URI = '" + videoFileName + "'"; 
//            Log.d("where", "SQL : " + SQL); 
//            if (MultiMemoActivity.mDatabase != null) { 
//                Cursor cursor = MultiMemoActivity.mDatabase.rawQuery(SQL); 
//                if (cursor.moveToNext()) { 
//                    videoId = cursor.getInt(0); 
//                } 
//                cursor.close(); 
//  
//                mMediaVideoUri = videoFileName; 
//  
//                SQL = "update " + MemoDatabase.TABLE_MEMO + 
//                    " set " + 
//                    " ID_VIDEO = '" + videoId + "'" + 
//                    " where _id = '" + mMemoId + "'"; 
//  
//                if (MultiMemoActivity.mDatabase != null) { 
//                    MultiMemoActivity.mDatabase.rawQuery(SQL); 
//                } 
//  
//                mMediaVideoId = String.valueOf(videoId); 
//            } 
//        } 
//        else if(isVideoCanceled && isVideoFileSaved) 
//        { 
//            SQL = "delete from " + MemoDatabase.TABLE_VIDEO + 
//                " where _ID = '" + mMediaVideoId + "'"; 
//            Log.d("where", "SQL : " + SQL); 
//            if (MultiMemoActivity.mDatabase != null) { 
//                MultiMemoActivity.mDatabase.execSQL(SQL); 
//            } 
//  
//            File videoFile = new File(BasicDefine.FOLDER_VIDEO + mMediaVideoUri); 
//            if (videoFile.exists()) { 
//                videoFile.delete(); 
//            } 
//  
//            SQL = "update " + MemoDatabase.TABLE_MEMO + 
//            " set " + 
//            " ID_VIDEO = '" + videoId + "'" + 
//            " where _id = '" + mMemoId + "'"; 
//  
//            if (MultiMemoActivity.mDatabase != null) { 
//                MultiMemoActivity.mDatabase.rawQuery(SQL); 
//            } 
//  
//            mMediaVideoId = String.valueOf(videoId); 
//        } 
//  
//        String voiceFileName = insertVoice(); 
//        int voiceId = -1; 
//  
//        if (voiceFileName != null) { 
//            // query picture id 
//            SQL = "select _ID from " + MemoDatabase.TABLE_VOICE + " where URI = '" + voiceFileName + "'"; 
//            Log.d("where", "SQL : " + SQL); 
//            if (MultiMemoActivity.mDatabase != null) { 
//                Cursor cursor = MultiMemoActivity.mDatabase.rawQuery(SQL); 
//                if (cursor.moveToNext()) { 
//                    voiceId = cursor.getInt(0); 
//                } 
//                cursor.close(); 
//  
//                mMediaVoiceUri = voiceFileName; 
//  
//                SQL = "update " + MemoDatabase.TABLE_MEMO + 
//                    " set " + 
//                    " ID_VOICE = '" + voiceId + "'" + 
//                    " where _id = '" + mMemoId + "'"; 
//  
//                if (MultiMemoActivity.mDatabase != null) { 
//                    MultiMemoActivity.mDatabase.rawQuery(SQL); 
//                } 
//  
//                mMediaVoiceId = String.valueOf(voiceId); 
//            } 
//        } 
//        else if(isVoiceCanceled && isVoiceFileSaved) 
//        { 
//            SQL = "delete from " + MemoDatabase.TABLE_VOICE + 
//                " where _ID = '" + mMediaVoiceId + "'"; 
//            Log.d("where", "SQL : " + SQL); 
//            if (MultiMemoActivity.mDatabase != null) { 
//                MultiMemoActivity.mDatabase.execSQL(SQL); 
//            } 
//  
//            File voiceFile = new File(BasicDefine.FOLDER_VOICE + mMediaVoiceUri); 
//            if (voiceFile.exists()) { 
//                voiceFile.delete(); 
//            } 
//  
//            SQL = "update " + MemoDatabase.TABLE_MEMO + 
//            " set " + 
//            " ID_VOICE = '" + voiceId + "'" + 
//            " where _id = '" + mMemoId + "'"; 
//  
//            if (MultiMemoActivity.mDatabase != null) { 
//                MultiMemoActivity.mDatabase.rawQuery(SQL); 
//            } 
//  
//            mMediaVoiceId = String.valueOf(voiceId); 
//        } 
  
  
  
        // update memo info 
        SQL = "update " + PhotoBookDB.TABLE_MEMO + 
                    " set " + 
                    " INPUT_DATE = DATETIME('" + mDateStr + "'), " + 
                    " CONTENT_TEXT = '" + mMemoStr + "'" + 
                    " where _id = '" + mMemoId + "'"; 
  
        Log.d("where", "SQL : " + SQL); 
        if (Main.mDatabase != null) { 
        	Main.mDatabase.execSQL(SQL); 
        } 
        
  
        intent.putExtra(BasicDefine.KEY_MEMO_TEXT, mMemoStr); 
        intent.putExtra(BasicDefine.KEY_ID_PHOTO, mMediaPhotoId); 
//        intent.putExtra(BasicDefine.KEY_ID_VIDEO, mMediaVideoId); 
//        intent.putExtra(BasicDefine.KEY_ID_VOICE, mMediaVoiceId); 
//        intent.putExtra(BasicDefine.KEY_ID_HANDWRITING, mMediaHandwritingId); 
        intent.putExtra(BasicDefine.KEY_URI_PHOTO, mMediaPhotoUri); 
//        intent.putExtra(BasicDefine.KEY_URI_VIDEO, mMediaVideoUri); 
//        intent.putExtra(BasicDefine.KEY_URI_VOICE, mMediaVoiceUri); 
//        intent.putExtra(BasicDefine.KEY_URI_HANDWRITING, mMediaHandwritingUri); 
  
        setResult(RESULT_OK, intent); 
        finish(); 
    } 
  
  
  
    /** 
     * 앨범의 사진을 사진 폴더에 복사한 후, PICTURE 테이블에 사진 정보 추가 
     * 이미지의 이름은 현재 시간을 기준으로 한 getTime() 값의 문자열 사용 
     * 
     * @return 새로 추가된 이미지의 이름 
     */
  
    private String insertPhoto() { 
        String photoName = null; 
  
        if (isPhotoCaptured) { // captured Bitmap 
            try { 
                if (mMemoMode != null && mMemoMode.equals(BasicDefine.MODE_MODIFY)) { 
                    Log.d("where", "previous photo is newly created for modify mode."); 
  
                    String SQL = "delete from " + PhotoBookDB.TABLE_PHOTO + 
                    " where _ID = '" + mMediaPhotoId + "'"; 
                    Log.d("where", "SQL : " + SQL); 
                    if (Main.mDatabase != null) { 
                    	Main.mDatabase.execSQL(SQL); 
                    } 
  
                    File previousFile = new File(BasicDefine.FOLDER_PHOTO + mMediaPhotoUri); 
                    if (previousFile.exists()) { 
                        previousFile.delete(); 
                    } 
                } 
  
  
                File photoFolder = new File(BasicDefine.FOLDER_PHOTO); 
  
                //폴더가 없다면 폴더를 생성한다. 
                if(!photoFolder.isDirectory()){ 
                    Log.d("where", "creating photo folder : " + photoFolder); 
                    photoFolder.mkdirs(); 
                } 
  
                // Temporary Hash for photo file name 
                photoName = createFilename(); 
  
                FileOutputStream outstream = new FileOutputStream(BasicDefine.FOLDER_PHOTO + photoName); 
                resultPhotoBitmap.compress(CompressFormat.PNG, 100, outstream); 
                outstream.close(); 
  
  
                if (photoName != null) { 
                    Log.d("where", "isCaptured            : " +isPhotoCaptured); 
  
                    // INSERT PICTURE INFO 
                    String SQL = "insert into " + PhotoBookDB.TABLE_PHOTO + "(URI) values(" + "'" + photoName + "')"; 
                    if (Main.mDatabase != null) { 
                    	Main.mDatabase.execSQL(SQL); 
                    	
                    } 
                } 
  
            } catch (IOException ex) { 
                Log.d("where", "Exception in copying photo : " + ex.toString()); 
            } 
  
  
        } 
        return photoName; 
    } 
  
  
//    private String insertHandwriting() { 
//        String handwritingName = null; 
//        Log.d("where", "isHandwritingMade            : " +isHandwritingMade); 
//        if (isHandwritingMade) { // captured Bitmap 
//            try { 
//  
//                if (mMemoMode != null && mMemoMode.equals(BasicDefine.MODE_MODIFY)) { 
//                    Log.d("where", "previous handwriting is newly created for modify mode."); 
//  
//                    String SQL = "delete from " + MemoDatabase.TABLE_HANDWRITING + 
//                    " where _ID = '" + mMediaHandwritingId + "'"; 
//                    Log.d("where", "SQL : " + SQL); 
//                    if (MultiMemoActivity.mDatabase != null) { 
//                        MultiMemoActivity.mDatabase.execSQL(SQL); 
//                    } 
//  
//                    File previousFile = new File(BasicDefine.FOLDER_HANDWRITING + mMediaHandwritingUri); 
//                    if (previousFile.exists()) { 
//                        previousFile.delete(); 
//                    } 
//                } 
//  
//  
//                File handwritingFolder = new File(BasicDefine.FOLDER_HANDWRITING); 
//  
//                //폴더가 없다면 폴더를 생성한다. 
//                if(!handwritingFolder.isDirectory()){ 
//                    Log.d("where", "creating handwriting folder : " + handwritingFolder); 
//                    handwritingFolder.mkdirs(); 
//                } 
//  
//                // Temporal Hash for handwriting file name 
//  
//                handwritingName = createFilename(); 
//  
//                FileOutputStream outstream = new FileOutputStream(BasicDefine.FOLDER_HANDWRITING + handwritingName); 
//                // MIKE 20101215 
//                resultHandwritingBitmap.compress(CompressFormat.PNG, 100, outstream); 
//                // MIKE END 
//                outstream.close(); 
//  
//  
//                if (handwritingName != null) { 
//                    Log.d("where", "isCaptured            : " +isHandwritingMade); 
//  
//                    // INSERT HANDWRITING INFO 
//                    String SQL = "insert into " + MemoDatabase.TABLE_HANDWRITING + "(URI) values(" + "'" + handwritingName + "')"; 
//                    if (MultiMemoActivity.mDatabase != null) { 
//                        MultiMemoActivity.mDatabase.execSQL(SQL); 
//                    } 
//                } 
//  
//            } catch (IOException ex) { 
//                Log.d("where", "Exception in copying handwriting : " + ex.toString()); 
//            } 
//  
//  
//        } 
//        return handwritingName; 
//    } 
//  
//  
//    private String insertVideo() { 
//        String videoName = null; 
//        Log.d("where", "isVideoRecorded            : " +isVideoRecorded); 
//  
//        if (isVideoRecorded) { // captured Bitmap 
//            if (mMemoMode != null && mMemoMode.equals(BasicDefine.MODE_MODIFY)) { 
//                Log.d("where", "previous video is newly created for modify mode."); 
//  
//                String SQL = "delete from " + MemoDatabase.TABLE_VIDEO + 
//                " where _ID = '" + mMediaVideoId + "'"; 
//                Log.d("where", "SQL : " + SQL); 
//                if (MultiMemoActivity.mDatabase != null) { 
//                    MultiMemoActivity.mDatabase.execSQL(SQL); 
//                } 
//  
//                if(BasicDefine.isAbsoluteVideoPath(mMediaVideoUri)) 
//                { 
//                    File previousFile = new File(BasicDefine.FOLDER_VIDEO + mMediaVideoUri); 
//                    if (previousFile.exists()) { 
//                        previousFile.delete(); 
//                    } 
//                } 
//            } 
//  
//            if(BasicDefine.isAbsoluteVideoPath(tempVideoUri)) 
//            { 
//                File videoFolder = new File(BasicDefine.FOLDER_VIDEO); 
//  
//                //폴더가 없다면 폴더를 생성한다. 
//                if(!videoFolder.isDirectory()){ 
//                    Log.d("where", "creating video folder : " + videoFolder); 
//                    videoFolder.mkdirs(); 
//                } 
//  
//                // Temporal Hash for video file name 
//                videoName = createFilename(); 
//  
//                File tempFile = new File(BasicDefine.FOLDER_VIDEO + "recorded"); 
//                tempFile.renameTo(new File(BasicDefine.FOLDER_VIDEO + videoName)); 
//            } 
//            else
//            { 
//                videoName = tempVideoUri; 
//            } 
//  
//            if (videoName != null) { 
//                Log.d("where", "isVideoRecorded            : " +isVideoRecorded); 
//  
//                // INSERT PICTURE INFO 
//                String SQL = "insert into " + MemoDatabase.TABLE_VIDEO + "(URI) values(" + "'" + videoName + "')"; 
//                if (MultiMemoActivity.mDatabase != null) { 
//                    MultiMemoActivity.mDatabase.execSQL(SQL); 
//                } 
//            } 
//  
//        } 
//  
//        return videoName; 
//    } 
//  
//    private String insertVoice() { 
//        String voiceName = null; 
//        Log.d("where", "isVoiceRecorded            : " +isVoiceRecorded); 
//        if (isVoiceRecorded) { // captured Bitmap 
//            if (mMemoMode != null && mMemoMode.equals(BasicDefine.MODE_MODIFY)) { 
//                Log.d("where", "previous voice is newly created for modify mode."); 
//  
//                String SQL = "delete from " + MemoDatabase.TABLE_VOICE + 
//                " where _ID = '" + mMediaVoiceId + "'"; 
//                Log.d("where", "SQL : " + SQL); 
//                if (MultiMemoActivity.mDatabase != null) { 
//                    MultiMemoActivity.mDatabase.execSQL(SQL); 
//                } 
//  
//                File previousFile = new File(BasicDefine.FOLDER_VOICE + mMediaVoiceUri); 
//                if (previousFile.exists()) { 
//                    previousFile.delete(); 
//                } 
//            } 
//  
//  
//            File voiceFolder = new File(BasicDefine.FOLDER_VOICE); 
//  
//            //폴더가 없다면 폴더를 생성한다. 
//            if(!voiceFolder.isDirectory()){ 
//                Log.d("where", "creating voice folder : " + voiceFolder); 
//                voiceFolder.mkdirs(); 
//            } 
//  
//            // Temporal Hash for voice file name 
//            voiceName = createFilename(); 
//  
//            File tempFile = new File(BasicDefine.FOLDER_VOICE + "recorded"); 
//            tempFile.renameTo(new File(BasicDefine.FOLDER_VOICE + voiceName)); 
//  
//            if (voiceName != null) { 
//                Log.d("where", "isVoiceRecorded            : " +isVoiceRecorded); 
//  
//                // INSERT PICTURE INFO 
//                String SQL = "insert into " + MemoDatabase.TABLE_VOICE + "(URI) values(" + "'" + voiceName + "')"; 
//                if (MultiMemoActivity.mDatabase != null) { 
//                    MultiMemoActivity.mDatabase.execSQL(SQL); 
//                } 
//            } 
//  
//        } 
//  
//        return voiceName; 
//    } 
  
  
  
  
    private String createFilename() { 
        Date curDate = new Date(); 
        String curDateStr = String.valueOf(curDate.getTime()); 
  
        return curDateStr; 
    } 
  
  
    public void setMediaLayout() { 
        isPhotoCaptured = false; 
//        isVideoRecorded = false; 
//        isVoiceRecorded = false; 
//        isHandwritingMade = false; 
  
//        mVideoBtn = (BitmapBtn)findViewById(R.id.insert_videoBtn); 
//        mVoiceBtn = (BitmapBtn)findViewById(R.id.insert_voiceBtn); 
  
    } 
  
    private void setCalendar(){ 
        insertDateButton = (BitmapBtn) findViewById(R.id.insert_dateBtn); 
        insertDateButton.setOnClickListener(new OnClickListener() { 
            public void onClick(View v) { 
                String mDateStr = insertDateButton.getText().toString(); 
                Calendar calendar = Calendar.getInstance(); 
                Date date = new Date(); 
                try { 
                    date = BasicDefine.dateDayNameFormat.parse(mDateStr); 
                } catch(Exception ex) { 
                    Log.d("where", "Exception in parsing date : " + date); 
                } 
  
                calendar.setTime(date); 
  
                new DatePickerDialog( 
                        Modify.this, 
                        dateSetListener, 
                        calendar.get(Calendar.YEAR), 
                        calendar.get(Calendar.MONTH), 
                        calendar.get(Calendar.DAY_OF_MONTH) 
                        ).show(); 
  
            } 
        }); 
  
        Date curDate = new Date(); 
        mCalendar.setTime(curDate); 
  
        int year = mCalendar.get(Calendar.YEAR); 
        int monthOfYear = mCalendar.get(Calendar.MONTH); 
        int dayOfMonth = mCalendar.get(Calendar.DAY_OF_MONTH); 
  
        insertDateButton.setText(year + "년 " + (monthOfYear+1) + "월 " + dayOfMonth + "일"); 
  
    } 
  
  
    /** 
     * 날짜 설정 리스너 
     */
    DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() { 
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) { 
            mCalendar.set(year, monthOfYear, dayOfMonth); 
            insertDateButton.setText(year + "년 " + (monthOfYear+1) + "월 " + dayOfMonth + "일"); 
        } 
    }; 
  
  
    /** 
     * 일자와 메모 확인 
     */
    private boolean parseValues() { 
        String insertDateStr = insertDateButton.getText().toString(); 
  
        Date curDate = new Date(); 
        Calendar curCalendar = Calendar.getInstance(); 
        curCalendar.setTime(curDate); 
  
        int curHour = curCalendar.get(Calendar.HOUR_OF_DAY); 
        int curMinute = curCalendar.get(Calendar.MINUTE); 
        String curHourStr = String.valueOf(curHour); 
        String curMinuteStr = String.valueOf(curMinute); 
        if (curHourStr.length() < 2) { 
            curHourStr = "0" + curHourStr; 
        } 
        if (curMinuteStr.length() < 2) { 
            curMinuteStr = "0" + curMinuteStr; 
        } 
  
        try { 
            String srcDateStr = insertDateStr + " " + curHourStr + "시 " + curMinuteStr + "분"; 
            Log.d("where", "source date string : " + srcDateStr); 
  
            Date insertDate = BasicDefine.dateNameFormat.parse(srcDateStr); 
            mDateStr = BasicDefine.dateFormat.format(insertDate); 
        } catch(ParseException ex) { 
            Log.e("where", "Exception in parsing date : " + insertDateStr); 
        } 
  
        String memotxt = mMemoEdit.getText().toString(); 
        mMemoStr = memotxt; 
  
        if (mMemoStr.trim().length() < 1) { 
            showDialog(BasicDefine.CONFIRM_TEXT_INPUT); 
            return false; 
        } 
  
        return true; 
    } 
  
  
    protected Dialog onCreateDialog(int id) { 
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
  
                mSelectdContentArray = R.array.array_photo; 
                builder.setTitle("선택하세요"); 
                builder.setSingleChoiceItems(mSelectdContentArray, 0, new DialogInterface.OnClickListener() { 
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
  
                mSelectdContentArray = R.array.array_photo_ex; 
                builder.setTitle("선택하세요"); 
                builder.setSingleChoiceItems(mSelectdContentArray, 0, new DialogInterface.OnClickListener() { 
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
                        deleteMemo(); 
                    } 
                }); 
                builder.setNegativeButton("아니오", new DialogInterface.OnClickListener() { 
                     public void onClick(DialogInterface dialog, int whichButton) { 
                         dismissDialog(BasicDefine.CONFIRM_DELETE); 
                     } 
                }); 
  
                break; 
  
//            case BasicDefine.CONTENT_VIDEO: 
//                builder = new AlertDialog.Builder(this); 
//  
//                mSelectdContentArray = R.array.array_video; 
//                builder.setTitle("선택하세요"); 
//                builder.setSingleChoiceItems(mSelectdContentArray, 0, new DialogInterface.OnClickListener() { 
//                    public void onClick(DialogInterface dialog, int whichButton) { 
//                        Log.d("where", "whichButton1 : " + whichButton); 
//                        mChoicedArrayItem = whichButton; 
//                    } 
//                }); 
//                builder.setPositiveButton("선택", new DialogInterface.OnClickListener() { 
//                    public void onClick(DialogInterface dialog, int whichButton) { 
//                        Log.d("where", "Selected Index : " + mChoicedArrayItem); 
//  
//                        if(mChoicedArrayItem == 0) { 
//                            showVideoRecordingActivity(); 
//                        } else if(mChoicedArrayItem == 1) { 
//                            showVideoLoadingActivity(); 
//                        } 
//                    } 
//                }); 
//                builder.setNegativeButton("취소", new DialogInterface.OnClickListener() { 
//                     public void onClick(DialogInterface dialog, int whichButton) { 
//  
//                     } 
//                 }); 
//  
//                break; 
//  
//            case BasicDefine.CONTENT_VIDEO_EX: 
//                builder = new AlertDialog.Builder(this); 
//  
//                mSelectdContentArray = R.array.array_video_ex; 
//                builder.setTitle("선택하세요"); 
//                builder.setSingleChoiceItems(mSelectdContentArray, 0, new DialogInterface.OnClickListener() { 
//                    public void onClick(DialogInterface dialog, int whichButton) { 
//                        Log.d("where", "whichButton1 : " + whichButton); 
//                        mChoicedArrayItem = whichButton; 
//                    } 
//                }); 
//                builder.setPositiveButton("선택", new DialogInterface.OnClickListener() { 
//                    public void onClick(DialogInterface dialog, int whichButton) { 
//                        Log.d("where", "Selected Index : " + mChoicedArrayItem); 
//                        if(mChoicedArrayItem == 0) { 
//                            showVideoPlayingActivity(); 
//                        } else if(mChoicedArrayItem == 1) { 
//                            showVideoRecordingActivity(); 
//                        } else if(mChoicedArrayItem == 2) { 
//                            showVideoLoadingActivity(); 
//                        } else if(mChoicedArrayItem == 3) { 
//                            isVideoCanceled = true; 
//                            isVideoRecorded = false; 
//  
//                            mVideoBtn.setCompoundDrawablesWithIntrinsicBounds(null, getResources().getDrawable 
//                                    (R.drawable.icon_video_empty), null, null); 
//                        } 
//                    } 
//                }); 
//                builder.setNegativeButton("취소", new DialogInterface.OnClickListener() { 
//                     public void onClick(DialogInterface dialog, int whichButton) { 
//  
//                     } 
//                 }); 
//  
//                break; 
//  
//            case BasicDefine.CONTENT_VOICE: 
//                builder = new AlertDialog.Builder(this); 
//  
//                mSelectdContentArray = R.array.array_voice; 
//                builder.setTitle("선택하세요"); 
//                builder.setSingleChoiceItems(mSelectdContentArray, 0, new DialogInterface.OnClickListener() { 
//                    public void onClick(DialogInterface dialog, int whichButton) { 
//                        Log.d("where", "whichButton1 : " + whichButton); 
//                        mChoicedArrayItem = whichButton; 
//                    } 
//                }); 
//                builder.setPositiveButton("선택", new DialogInterface.OnClickListener() { 
//                    public void onClick(DialogInterface dialog, int whichButton) { 
//                        Log.d("where", "whichButton2        ======        " + whichButton); 
//                        if(mChoicedArrayItem == 0 ){ 
//                            showVoiceRecordingActivity(); 
//                        } 
//                    } 
//                }); 
//                builder.setNegativeButton("취소", new DialogInterface.OnClickListener() { 
//                     public void onClick(DialogInterface dialog, int whichButton) { 
//  
//                     } 
//                 }); 
//  
//                break; 
//  
//            case BasicDefine.CONTENT_VOICE_EX: 
//                builder = new AlertDialog.Builder(this); 
//  
//                mSelectdContentArray = R.array.array_voice_ex; 
//                builder.setTitle("선택하세요"); 
//                builder.setSingleChoiceItems(mSelectdContentArray, 0, new DialogInterface.OnClickListener() { 
//                    public void onClick(DialogInterface dialog, int whichButton) { 
//                        Log.d("where", "whichButton1 : " + whichButton); 
//                        mChoicedArrayItem = whichButton; 
//                    } 
//                }); 
//                builder.setPositiveButton("선택", new DialogInterface.OnClickListener() { 
//                    public void onClick(DialogInterface dialog, int whichButton) { 
//                        Log.d("where", "Selected Index : " + mChoicedArrayItem); 
//  
//                        if(mChoicedArrayItem == 0 ) { 
//                            showVoicePlayingActivity(); 
//                        } else if(mChoicedArrayItem == 1) { 
//                            isVoiceCanceled = true; 
//                            isVoiceRecorded = false; 
//  
//                            mVoiceBtn.setCompoundDrawablesWithIntrinsicBounds(null, getResources().getDrawable 
//                                    (R.drawable.icon_voice_empty), null, null); 
//                        } 
//                    } 
//                }); 
//                builder.setNegativeButton("취소", new DialogInterface.OnClickListener() { 
//                     public void onClick(DialogInterface dialog, int whichButton) { 
//  
//                     } 
//                 }); 
//  
//                break; 
  
            default: 
                break; 
        } 
  
        return builder.create(); 
    } 
  
  
    /** 
     * 메모 삭제 
     */
    private void deleteMemo() { 
  
        // delete photo record 
        Log.d("where", "deleting previous photo record and file : " + mMediaPhotoId); 
        String SQL = "delete from " + PhotoBookDB.TABLE_PHOTO + 
                    " where _ID = '" + mMediaPhotoId + "'"; 
        Log.d("where", "SQL : " + SQL); 
        if (Main.mDatabase != null) { 
        	Main.mDatabase.execSQL(SQL); 
        } 
  
        File photoFile = new File(BasicDefine.FOLDER_PHOTO + mMediaPhotoUri); 
        if (photoFile.exists()) { 
            photoFile.delete(); 
        } 
  
  
  
  
//        // delete handwriting record 
//        Log.d("where", "deleting previous handwriting record and file : " + mMediaHandwritingId); 
//        SQL = "delete from " + MemoDatabase.TABLE_HANDWRITING + 
//                    " where _ID = '" + mMediaHandwritingId + "'"; 
//        Log.d("where", "SQL : " + SQL); 
//        if (MultiMemoActivity.mDatabase != null) { 
//            MultiMemoActivity.mDatabase.execSQL(SQL); 
//        } 
//  
//        File handwritingFile = new File(BasicDefine.FOLDER_HANDWRITING + mMediaHandwritingUri); 
//        if (handwritingFile.exists()) { 
//            handwritingFile.delete(); 
//        } 
  
  
        // delete memo record 
        Log.d("where", "deleting previous memo record : " + mMemoId); 
        SQL = "delete from " + PhotoBookDB.TABLE_MEMO + 
                    " where _id = '" + mMemoId + "'"; 
        Log.d("where", "SQL : " + SQL); 
        if (Main.mDatabase != null) { 
        	Main.mDatabase.execSQL(SQL); 
        } 
  
        setResult(RESULT_OK); 
  
        finish(); 
    } 
  
  
    public void showPhotoCaptureActivity() { 
        Intent intent = new Intent(getApplicationContext(), PhotoCaptureActivity.class); 
        startActivityForResult(intent, BasicDefine.REQ_PHOTO_CAPTURE_ACTIVITY); 
    } 
  
    public void showPhotoLoadingActivity() { 
        Intent intent = new Intent(getApplicationContext(), PhotoSelectionActivity.class); 
        startActivityForResult(intent, BasicDefine.REQ_PHOTO_SELECTION_ACTIVITY); 
    } 
  
    /** 
     * 다른 액티비티로부터의 응답 처리 
     */
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) { 
        super.onActivityResult(requestCode, resultCode, intent); 
  
        switch(requestCode) { 
            case BasicDefine.REQ_PHOTO_CAPTURE_ACTIVITY:  // 사진 찍는 경우 
                Log.d("where", "onActivityResult() for REQ_PHOTO_CAPTURE_ACTIVITY."); 
  
                if (resultCode == RESULT_OK) { 
                    Log.d("where", "resultCode : " + resultCode); 
  
                    boolean isPhotoExists = checkCapturedPhotoFile(); 
                    if (isPhotoExists) { 
                        Log.d("where", "image file exists : " + BasicDefine.FOLDER_PHOTO + "captured"); 
  
                        resultPhotoBitmap = BitmapFactory.decodeFile(BasicDefine.FOLDER_PHOTO + "captured"); 
  
                        tempPhotoUri = "captured"; 
  
                        mPhoto.setImageBitmap(resultPhotoBitmap); 
                        isPhotoCaptured = true; 
  
                        mPhoto.invalidate(); 
                    } else { 
                        Log.d("where", "image file doesn't exists : " + BasicDefine.FOLDER_PHOTO + "captured"); 
                    } 
                } 
  
                break; 
  
            case BasicDefine.REQ_PHOTO_SELECTION_ACTIVITY:  // 사진을 앨범에서 선택하는 경우 
                Log.d("where", "onActivityResult() for REQ_PHOTO_LOADING_ACTIVITY."); 
  
                if (resultCode == RESULT_OK) { 
                    Log.d("where", "resultCode : " + resultCode); 
  
                    Uri getPhotoUri = intent.getParcelableExtra(BasicDefine.KEY_URI_PHOTO); 
                    try { 
                    	BitmapFactory.Options opts = new BitmapFactory.Options();
    		    		opts.inSampleSize = 4;
                    	
                        resultPhotoBitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(getPhotoUri), null, opts); 
                    } catch (FileNotFoundException e) { 
                        e.printStackTrace(); 
                    } 
  
                    mPhoto.setImageBitmap(resultPhotoBitmap); 
                    isPhotoCaptured = true; 
  
                    mPhoto.invalidate(); 
                    //resultPhotoBitmap.recycle();
                } 
  
                break; 
  
  
//            case BasicDefine.REQ_HANDWRITING_MAKING_ACTIVITY:  // 손글씨를 저장하는 경우 
//                Log.d("where", "onActivityResult() for REQ_HANDWRITING_MAKING_ACTIVITY."); 
//  
//                if (resultCode == RESULT_OK) { 
//                    boolean isHandwritingExists = checkMadeHandwritingFile(); 
//                    if(isHandwritingExists) { 
//                        resultHandwritingBitmap = BitmapFactory.decodeFile(BasicDefine.FOLDER_HANDWRITING + "made"); 
//                        tempHandwritingUri = "made"; 
//  
//                        isHandwritingMade = true; 
//  
//                        insert_handwriting.setImageBitmap(resultHandwritingBitmap); 
//                    } 
//                } 
//  
//                break; 
//  
//            case BasicDefine.REQ_VIDEO_RECORDING_ACTIVITY:  // 동영상을 녹화하는 경우 
//                Log.d("where", "onActivityResult() for REQ_VIDEO_RECORDING_ACTIVITY."); 
//  
//                if (resultCode == RESULT_OK) 
//                { 
//                    boolean isVideoExists = checkRecordedVideoFile(); 
//                    if(isVideoExists) 
//                    { 
//                        tempVideoUri = "recorded"; 
//  
//                        isVideoRecorded = true; 
//  
//                        mVideoBtn.setCompoundDrawablesWithIntrinsicBounds(null, getResources().getDrawable 
//                                (R.drawable.icon_video), null, null); 
//                    } 
//                } 
//  
//                break; 
//  
//            case BasicDefine.REQ_VIDEO_LOADING_ACTIVITY:  // 동영상을 선택하는 경우 
//                if (resultCode == RESULT_OK) { 
//                    String getVideoUri = intent.getStringExtra(BasicDefine.KEY_URI_VIDEO); 
//                    tempVideoUri = BasicDefine.URI_MEDIA_FORMAT + getVideoUri; 
//                    isVideoRecorded = true; 
//  
//                    mVideoBtn.setCompoundDrawablesWithIntrinsicBounds(null, getResources().getDrawable 
//                            (R.drawable.icon_video), null, null); 
//                } 
//  
//                break; 
//  
//            case BasicDefine.REQ_VOICE_RECORDING_ACTIVITY:  // 녹음하는 경우 
//                Log.d("where", "onActivityResult() for REQ_VOICE_RECORDING_ACTIVITY."); 
//  
//                if (resultCode == RESULT_OK) { 
//                    boolean isVoiceExists = checkRecordedVoiceFile(); 
//                    if(isVoiceExists) { 
//                        tempVoiceUri = "recorded"; 
//  
//                        isVoiceRecorded = true; 
//  
//                        mVoiceBtn.setCompoundDrawablesWithIntrinsicBounds(null, getResources().getDrawable 
//                                (R.drawable.icon_voice), null, null); 
//                    } 
//                } 
//  
//                break; 
  
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
  
  
//    /** 
//     * 저장된 손글씨 파일 확인 
//     */
//    private boolean checkMadeHandwritingFile() { 
//        File file = new File(BasicDefine.FOLDER_HANDWRITING + "made"); 
//        if(file.exists()) { 
//            return true; 
//        } 
//  
//        return false; 
//    } 
//  
//  
//    /** 
//     * 저장된 동영상 파일 확인 
//     */
//    private boolean checkRecordedVideoFile() { 
//        File file = new File(BasicDefine.FOLDER_VIDEO + "recorded"); 
//        if(file.exists()) { 
//            return true; 
//        } 
//  
//        return false; 
//    } 
//  
//    /** 
//     * 저장된 녹음 파일 확인 
//     */
//    private boolean checkRecordedVoiceFile() { 
//        File file = new File(BasicDefine.FOLDER_VOICE + "recorded"); 
//        if(file.exists()) { 
//            return true; 
//        } 
//  
//        return false; 
//    } 


	
	
	
}
