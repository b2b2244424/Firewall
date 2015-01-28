package com.android.firewall.ui.dialog;

import com.android.firewall.R;
import com.android.firewall.constants.FirewallConstants;
import com.android.firewall.model.BlacklistInfo;
import com.android.firewall.util.LogUtil;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

public class EntryEditDialog extends DialogFragment {
	public final static String TAG="EntryEditDialog";
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setCancelable(true);
	}
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
		LayoutInflater inflater=getActivity().getLayoutInflater();
		View v=inflater.inflate(R.layout.dialog_edit_entry, null);
		final EditText phoneET=(EditText)v.findViewById(R.id.phone_edittext);
		final EditText nameET=(EditText)v.findViewById(R.id.name_edittext);
		final CheckBox cb_phone_block=(CheckBox)v.findViewById(R.id.phone_checkbox);
		final CheckBox cb_msg_block=(CheckBox)v.findViewById(R.id.msg_checkbox);
		final String num,name;
		final boolean is_phone_block,is_msg_block;
		final int id;
		Bundle args=getArguments();
		if(args!=null){
			id=Integer.parseInt(args.getString(FirewallConstants.KEY_ID));
			num=args.getString(FirewallConstants.KEY_PHONE);
			name=args.getString(FirewallConstants.KEY_NAME);
			is_phone_block=args.getBoolean(FirewallConstants.KEY_PHONE_BLOCK);
			is_msg_block=args.getBoolean(FirewallConstants.KEY_MSG_BLOCK);
		}else{
			num=name="";
			is_phone_block=is_msg_block=true;
			id=-1;
		}
		phoneET.setText(num);
		nameET.setText(name);
		if(is_msg_block)
			cb_msg_block.setChecked(true);
		if(is_phone_block)
			cb_phone_block.setChecked(true);
		builder.setView(v).setPositiveButton("OK", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				OKListener listener=(OKListener)getActivity();
				BlacklistInfo info=new BlacklistInfo();
				info.setPhone_num(phoneET.getText().toString());
				info.setName(nameET.getText().toString());
				if(cb_phone_block.isChecked())
					info.setPhone_mode(FirewallConstants.BLOCK);
				else {
					info.setPhone_mode(FirewallConstants.UNBLOCK);
				}
				if(cb_msg_block.isChecked())
					info.setMsg_mode(FirewallConstants.BLOCK);
				else {
					info.setMsg_mode(FirewallConstants.UNBLOCK);
				}
				info.setId(id);
				listener.onOKComplete(info);
			}
		}).setNegativeButton("Cancel", null);
		return builder.create();
	}

	@Override
	public void onCancel(DialogInterface dialog) {
		super.onCancel(dialog);
	}



	@Override
	public void onDismiss(DialogInterface dialog) {
		super.onDismiss(dialog);
	}
	
	public interface OKListener{
		void onOKComplete(BlacklistInfo info);
	}
	
	

}
