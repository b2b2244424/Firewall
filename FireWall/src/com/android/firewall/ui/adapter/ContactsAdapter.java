package com.android.firewall.ui.adapter;

import java.util.ArrayList;
import java.util.List;

import com.android.firewall.R;
import com.android.firewall.model.BlacklistInfo;
import com.android.firewall.model.ContactInfo;

import android.R.raw;
import android.content.Context;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;

public class ContactsAdapter extends BaseAdapter {

	Context mContext;
	LayoutInflater mInflater;
	ArrayList<ContactInfo> mList;
	SparseBooleanArray mSparseBooleanArray;

	public ContactsAdapter(Context context, List<ContactInfo> list) {
		this.mContext = context;
		mInflater = LayoutInflater.from(mContext);
		mSparseBooleanArray = new SparseBooleanArray();
		mList = new ArrayList<ContactInfo>();
		this.mList = (ArrayList<ContactInfo>) list;

	}
	
	public void selectAll(){
		for (int i = 0; i < mList.size(); i++) {
			mSparseBooleanArray.put(i, true);
		}
	}
	
	public ArrayList<BlacklistInfo> getCheckedItems() {
		ArrayList<BlacklistInfo> mTempArry = new ArrayList<BlacklistInfo>();

		for (int i = 0; i < mList.size(); i++) {
			if (mSparseBooleanArray.get(i)) {
				ContactInfo contactInfo=mList.get(i);
				BlacklistInfo info = new BlacklistInfo();
				info.setId(-1);
				info.setMsg_mode(1);
				info.setPhone_mode(1);
				info.setName(contactInfo.getName());
				info.setPhone_num(contactInfo.getPhone_num());
				mTempArry.add(info);
			}
		}

		return mTempArry;
	}

	@Override
	public int getCount() {
		return mList.size();
	}

	@Override
	public Object getItem(int position) {
		return mList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.list_item_contacts, null);

		}

		ContactInfo info = mList.get(position);

		TextView tv_name = (TextView) convertView
				.findViewById(R.id.contacts_name_tv);
		tv_name.setText(info.getName());

		TextView tv_phone = (TextView) convertView
				.findViewById(R.id.contacts_phone_tv);
		tv_phone.setText(info.getPhone_num());

		CheckBox mCheckBox = (CheckBox) convertView
				.findViewById(R.id.choose_cb);
		mCheckBox.setTag(position);
		mCheckBox.setChecked(mSparseBooleanArray.get(position));
		mCheckBox.setOnCheckedChangeListener(mCheckedChangeListener);

		return convertView;
	}

	OnCheckedChangeListener mCheckedChangeListener = new OnCheckedChangeListener() {

		@Override
		public void onCheckedChanged(CompoundButton buttonView,
				boolean isChecked) {
			mSparseBooleanArray.put((Integer) buttonView.getTag(), isChecked);
		}
	};

}
