package com.android.firewall.ui.activity;

import java.util.ArrayList;
import java.util.List;

import com.android.firewall.R;
import com.android.firewall.data.provider.DatabaseManager;
import com.android.firewall.model.BlacklistInfo;
import com.android.firewall.model.ContactInfo;
import com.android.firewall.ui.adapter.ContactsAdapter;
import com.android.firewall.util.LogUtil;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;

public class AddFromContactsActivity extends Activity implements OnClickListener{
	public final static String TAG="AddFromContactsActivity";
	private ListView contacts_lv;
	private ContactsAdapter contacts_adapter;
	private Button btn_add,btn_select_all;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_add_from_contacts);
		
		btn_add=(Button)findViewById(R.id.add_contacts_btn);
		btn_add.setOnClickListener(this);
		
		btn_select_all=(Button)findViewById(R.id.contacts_btn_select_all);
		btn_select_all.setOnClickListener(this);
		
		contacts_lv=(ListView)findViewById(R.id.contacts_listview);
		contacts_adapter=new ContactsAdapter(getApplicationContext(), getContactInfos(getContacts()));
		contacts_lv.setAdapter(contacts_adapter);
//		contacts_adapter=new ContactsAdapter1(getApplicationContext(), getContacts(), 0);
//		contacts_lv.setAdapter(contacts_adapter);
//		
//		contacts_lv.setOnItemClickListener(new OnItemClickListener() {
//
//			@Override
//			public void onItemClick(AdapterView<?> parent, View view,
//					int position, long id) {
//				ContactsViewHolder holder=(ContactsViewHolder)view.getTag();
//				LogUtil.log("AddFromContacts", "click"+position+" "+holder.phone_tv.getText().toString());	
//			}
//		});
	}
	
	private Cursor getContacts(){
		return getContentResolver().query(Phone.CONTENT_URI, null, null, null, null);
	}
	
	private List<ContactInfo> getContactInfos(Cursor cursor){
		LogUtil.log(TAG, "count:"+cursor.getCount());
		List<ContactInfo> infos=new ArrayList<ContactInfo>();
		if(cursor.moveToFirst()){
			while(!cursor.isAfterLast()){
				ContactInfo info=new ContactInfo();
				info.setName(cursor.getString(cursor.getColumnIndex(Phone.DISPLAY_NAME)));
				info.setPhone_num(cursor.getString(cursor.getColumnIndex(Phone.NUMBER)));
				infos.add(info);
				cursor.moveToNext();
			}
			cursor.close();
		}
		return infos;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.add_contacts_btn:
			DatabaseManager.getInstance(getApplicationContext()).insertOrUpdateManyBlacklist(contacts_adapter.getCheckedItems());
			this.finish();
			break;
		case R.id.contacts_btn_select_all:
			contacts_adapter.selectAll();
			contacts_adapter.notifyDataSetChanged();
			break;
		default:
			break;
		}
		
	}	
}
