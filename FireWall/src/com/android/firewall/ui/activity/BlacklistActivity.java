package com.android.firewall.ui.activity;

import com.android.firewall.MainActivity;
import com.android.firewall.R;
import com.android.firewall.constants.DatabaseConstants.BlacklistConlums;
import com.android.firewall.constants.FirewallConstants;
import com.android.firewall.constants.DatabaseConstants.URIConstants;
import com.android.firewall.data.provider.DatabaseManager;
import com.android.firewall.model.BlacklistInfo;
import com.android.firewall.ui.adapter.BlacklistAdapter;
import com.android.firewall.ui.adapter.BlacklistAdapter.BlacklistViewHolder;
import com.android.firewall.ui.dialog.EntryEditDialog;
import com.android.firewall.ui.dialog.EntryEditDialog.OKListener;
import com.android.firewall.util.LogUtil;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;

public class BlacklistActivity extends Activity implements OKListener {
	private final static String TAG = "BlacklistActivity";
	private ListView lv_blacklist;
	private BlacklistAdapter bl_adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_blacklist);
			
		initViews();

		setListener();
	}
	
	private void initViews(){
		lv_blacklist = (ListView) findViewById(R.id.bl_lv_blacklist);
		registerForContextMenu(lv_blacklist);
		Cursor c = getBlackListCursor();
		bl_adapter = new BlacklistAdapter(getApplicationContext(), c, false);
		lv_blacklist.setAdapter(bl_adapter);
		if (c.getCount() == 0) {
			LogUtil.log("Blacklist Activity", "no data in blacklist.db");
		} else {
			LogUtil.log("Blacklist Activity", "count:" + c.getCount());
		}
	}

	@Override
	protected void onResume() {
		Cursor cursor = getBlackListCursor();
		if (cursor != null)
			bl_adapter.changeCursor(cursor);
		super.onResume();
	}

	@Override
	protected void onDestroy() {
		Cursor cursor = bl_adapter.getCursor();
		if (cursor != null)
			cursor.close();
		super.onDestroy();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Intent intent;
		switch (item.getItemId()) {
		case R.id.add_new_item:
			EntryEditDialog dialog = new EntryEditDialog();
			dialog.show(getFragmentManager(), "Entry Edit Dialog");
			break;
		case R.id.add_from_contacts_item:
			intent = new Intent(this, AddFromContactsActivity.class);
			startActivity(intent);
			break;
		case R.id.delete_all_item:
			DatabaseManager.getInstance(getApplicationContext())
					.clearAllBlacklist();
			dataChanged();
			break;
		case R.id.test_item:
			intent = new Intent(getApplicationContext(), MainActivity.class);
			startActivity(intent);
			break;
		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onOKComplete(BlacklistInfo info) {
		Cursor cursor=null;
		try {
			cursor= DatabaseManager.getInstance(getApplicationContext())
					.queryBlacklistEntry(info.getPhone_num());
			// 已添加到黑名单的号码就更新，否则添加
			if (cursor.getCount()==1) {
				cursor.moveToFirst();//默认cursor在first之前
				int id=cursor.getInt(cursor
						.getColumnIndex(BlacklistConlums._ID));
				info.setId(id);
			}
			DatabaseManager.getInstance(getApplicationContext())
					.insertOrUpdateBlacklist(info);
			dataChanged();
		} catch (Exception e) {
			// TODO: handle exception
		}finally{
			if(cursor!=null)
				cursor.close();
		}


	}

	private Cursor getBlackListCursor() {
		return getContentResolver().query(URIConstants.URI_BLACKLIST, null,
				null, null, null);
	}

	private void setListener() {
	}

	private void dataChanged() {
		bl_adapter.changeCursor(getBlackListCursor());
		bl_adapter.notifyDataSetChanged();
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		getMenuInflater().inflate(R.menu.contextmenu_blacklist, menu);
		super.onCreateContextMenu(menu, v, menuInfo);

	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		AdapterContextMenuInfo info = (AdapterContextMenuInfo) item
				.getMenuInfo();
		BlacklistViewHolder holder = (BlacklistViewHolder) info.targetView
				.getTag();
		switch (item.getItemId()) {
		case R.id.edit_menu:
			Bundle args = new Bundle();
			args.putString(FirewallConstants.KEY_ID, holder.tv_id.getText()
					.toString());
			args.putString(FirewallConstants.KEY_PHONE, holder.tv_phone
					.getText().toString());
			args.putString(FirewallConstants.KEY_NAME, holder.tv_name.getText()
					.toString());
			args.putBoolean(FirewallConstants.KEY_PHONE_BLOCK,
					holder.cb_phone_block.isChecked());
			args.putBoolean(FirewallConstants.KEY_MSG_BLOCK,
					holder.cb_msg_block.isChecked());
			EntryEditDialog dialog = new EntryEditDialog();
			dialog.setArguments(args);
			dialog.show(getFragmentManager(), "edit entry");
			break;
		case R.id.delete_menu:
			int id = Integer.parseInt(holder.tv_id.getText().toString());
			DatabaseManager.getInstance(getApplicationContext())
					.deleteBlacklistEntry(id);
			dataChanged();
			break;
		default:
			break;
		}
		return true;
	}

}
