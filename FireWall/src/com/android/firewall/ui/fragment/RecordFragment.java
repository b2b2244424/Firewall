package com.android.firewall.ui.fragment;


import com.android.firewall.R;
import com.android.firewall.constants.DatabaseConstants.URIConstants;
import com.android.firewall.ui.adapter.PBRAdapter;
import com.android.firewall.util.LogUtil;

import android.app.Fragment;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ListView;

public class RecordFragment extends Fragment implements OnClickListener {

	private ListView pbr_lv;
	private PBRAdapter pbr_adapter;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_record, null);
		init(v);

		return v;
	}

	private void init(View v) {
		pbr_lv=(ListView)v.findViewById(R.id.record_lv_pbr);
		pbr_adapter=new PBRAdapter(getActivity(), getPBR(), false);
		pbr_lv.setAdapter(pbr_adapter);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {

		default:
			break;
		}
	}
	
	private Cursor getPBR(){
		Cursor cursor= getActivity().getContentResolver().query(URIConstants.URI_PBR, null, null, null, null);
		LogUtil.log("record fragment", "count:"+cursor.getCount()+"");
		return cursor;
	}

}

