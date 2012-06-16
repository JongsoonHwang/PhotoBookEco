package com.appcreator.photobook;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Toast;

public class MemoListAdapter extends BaseAdapter{

	private Context mContext;
	MemoListItemView itemView;
	NewPhotoBook newPhotoBook = new NewPhotoBook();
	
	int count = 0;

	private List<MemoListItem> mItems = new ArrayList<MemoListItem>();

	public MemoListAdapter(Context context) {
		mContext = context;
	}

	public void clear() {
		mItems.clear();
	}

	public void addItem(MemoListItem it) {
		mItems.add(it);
	}

	public void setListItems(List<MemoListItem> lit) {
		mItems = lit;
	}

	public int getCount() {
		return mItems.size();
	}

	public Object getItem(int position) {
		return mItems.get(position);
	}

	public boolean areAllItemsSelectable() {
		return false;
	}

	public boolean isSelectable(int position) {
		try {
			return mItems.get(position).isSelectable();
		} catch (IndexOutOfBoundsException ex) {
			return false;
		}
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(final int position, View convertView, ViewGroup parent) {
		Log.i("position", "" + position);
		
		if (convertView == null) {
			itemView = new MemoListItemView(mContext, position);
//			count++;
		} else {
			itemView = (MemoListItemView) convertView;
		}
		itemView.findViewById(R.id.itemPhoto).setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Toast.makeText(mContext, "asdf", Toast.LENGTH_SHORT).show();
//				String filename = ((String) mItems.get(position).getData(3));
				String filename = ((String) mItems.get(position).getData(3));
				Intent intent = new Intent(mContext, OriginalPicture.class);
				intent.putExtra("filename", filename);
				mContext.startActivity(intent);
			}
		});

		// set current item data
		itemView.setContents(0, ((String) mItems.get(position).getData(0)));
		itemView.setContents(1, ((String) mItems.get(position).getData(1)));
//		itemView.setContents(2, ((String) mItems.get(position).getData(3)));
		itemView.setContents(3, ((String) mItems.get(position).getData(3)));
//
//		itemView.setMediaState(mItems.get(position).getData(7),
//				mItems.get(position).getData(9));

		return itemView;
	}
}
