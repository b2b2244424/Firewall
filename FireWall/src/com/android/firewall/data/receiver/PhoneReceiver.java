package com.android.firewall.data.receiver;


import com.android.firewall.constants.FirewallConstants;
import com.android.firewall.constants.DatabaseConstants.URIConstants;
import com.android.firewall.data.provider.DatabaseManager;
import com.android.firewall.model.PBRInfo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.widget.Toast;

public class PhoneReceiver extends BroadcastReceiver {
	public static final String TAG="PhoneReceiver";
	@Override
	public void onReceive(Context context, Intent intent) {
		Toast.makeText(context, "onRecieved:"+intent.getStringExtra("name")+","+intent.getStringExtra("phone_num"), Toast.LENGTH_SHORT).show();
		PBRInfo info=new PBRInfo();
		info.setName(intent.getStringExtra(FirewallConstants.KEY_PHONE));
		info.setPhone_num(intent.getStringExtra(FirewallConstants.KEY_NAME));
		info.setBlock_time(intent.getStringExtra(FirewallConstants.KEY_BLOCK_TIME));
		DatabaseManager.getInstance(context).insertOrUpdatePBR(info);
		
		Cursor cursor=context.getContentResolver().query(URIConstants.URI_PBR, null, null, null, null);
		Toast.makeText(context, "count:"+cursor.getCount(), Toast.LENGTH_SHORT).show();
	}

	
}
