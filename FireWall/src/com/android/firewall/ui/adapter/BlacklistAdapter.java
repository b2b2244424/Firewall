package com.android.firewall.ui.adapter;

import com.android.firewall.R;
import com.android.firewall.constants.DatabaseConstants.BlacklistConlums;
import com.android.firewall.constants.FirewallConstants;
import com.android.firewall.util.LogUtil;

import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CursorAdapter;
import android.widget.TextView;

public class BlacklistAdapter extends CursorAdapter {
	public final static String TAG="BlacklistAdapter";
	
	private LayoutInflater mInflater;
	public BlacklistAdapter(Context context, Cursor c, boolean autoRequery) {
		super(context, c, autoRequery);

		mInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}
	

	@Override
	public View newView(Context context, Cursor cursor, ViewGroup parent) {
		final View view = mInflater.inflate(R.layout.list_item_blacklist, null);
		final BlacklistViewHolder holder = new BlacklistViewHolder();
		holder.tv_name = (TextView) view.findViewById(R.id.bl_name_tv);
		holder.tv_phone = (TextView) view.findViewById(R.id.bl_phone_tv);
		holder.tv_id=(TextView)view.findViewById(R.id.bl_id_tv);
		holder.cb_phone_block = (CheckBox) view.findViewById(R.id.bl_cb_phone_block);
		holder.cb_msg_block = (CheckBox) view.findViewById(R.id.bl_cb_msg_block);

		view.setTag(holder);
		return view;
	}

	@Override
	public void bindView(View view, Context context, Cursor cursor) {
		BlacklistViewHolder holder = (BlacklistViewHolder) view.getTag();
		// set data
		int id=cursor.getInt(cursor.getColumnIndex(BlacklistConlums._ID));
		String name = cursor.getString(cursor
				.getColumnIndex(BlacklistConlums.NAME));
		String phone = cursor.getString(cursor.getColumnIndex(BlacklistConlums.NUMBER));
		int phone_block=cursor.getInt(cursor.getColumnIndex(BlacklistConlums.PHONE_MODE));
		int msg_block=cursor.getInt(cursor.getColumnIndex(BlacklistConlums.MESSAGE_MODE));
		holder.tv_id.setText(id+"");
		holder.tv_name.setText(name);
		holder.tv_phone.setText(phone);
		if(phone_block==FirewallConstants.BLOCK)
			holder.cb_phone_block.setChecked(true);
		else {
			holder.cb_phone_block.setChecked(false);
		}
		if(msg_block==FirewallConstants.BLOCK)
			holder.cb_msg_block.setChecked(true);
		else {
			holder.cb_msg_block.setChecked(false);
		}

	}
	
	public static class BlacklistViewHolder {
		public TextView tv_name;
		public TextView tv_phone;
		public TextView tv_id;
		public CheckBox cb_phone_block;
		public CheckBox cb_msg_block;
	}

}
