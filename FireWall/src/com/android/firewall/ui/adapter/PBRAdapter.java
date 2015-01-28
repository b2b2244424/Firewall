package com.android.firewall.ui.adapter;

import com.android.firewall.R;
import com.android.firewall.constants.DatabaseConstants.PBRConlums;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

public class PBRAdapter extends CursorAdapter {
	public final static String TAG="PBRAdapter";
	
	private LayoutInflater mInflater;
	public PBRAdapter(Context context, Cursor c, boolean autoRequery) {
		super(context, c, autoRequery);

		mInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}
	

	@Override
	public View newView(Context context, Cursor cursor, ViewGroup parent) {
		final View view = mInflater.inflate(R.layout.list_item_phone_record, null);
		final PBRViewHolder holder = new PBRViewHolder();
		holder.tv_name = (TextView) view.findViewById(R.id.pbr_name_tv);
		holder.tv_phone = (TextView) view.findViewById(R.id.pbr_phone_tv);
		holder.tv_id=(TextView)view.findViewById(R.id.pbr_id_tv);
		holder.tv_block_time=(TextView)view.findViewById(R.id.pbr_block_time_tv);
		view.setTag(holder);
		return view;
	}

	@Override
	public void bindView(View view, Context context, Cursor cursor) {
		PBRViewHolder holder = (PBRViewHolder) view.getTag();
		// set data
		int id=cursor.getInt(cursor.getColumnIndex(PBRConlums._ID));
		String name = cursor.getString(cursor
				.getColumnIndex(PBRConlums.NAME));
		String phone = cursor.getString(cursor.getColumnIndex(PBRConlums.NUMBER));
		String block_time=cursor.getString(cursor.getColumnIndex(PBRConlums.BLOCK_TIME));
		holder.tv_id.setText(id+"");
		holder.tv_name.setText(name);
		holder.tv_phone.setText(phone);
		holder.tv_block_time.setText(block_time);
	}
	
	public static class PBRViewHolder {
		public TextView tv_name;
		public TextView tv_phone;
		public TextView tv_id;
		public TextView tv_block_time;
	}

}
