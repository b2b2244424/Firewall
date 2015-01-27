package com.android.firewall.data.provider;

import java.util.List;

import com.android.firewall.constants.DatabaseConstants.BlacklistConlums;
import com.android.firewall.constants.DatabaseConstants.PBRConlums;
import com.android.firewall.constants.DatabaseConstants.URIConstants;
import com.android.firewall.model.BlacklistInfo;
import com.android.firewall.model.PBRInfo;

import android.content.ContentValues;
import android.content.Context;


public class DatabaseManager {

	private static DatabaseManager instance;
	private Context mContext;
	private DatabaseManager(Context context) {
		mContext = context;
	}
	
	public static synchronized DatabaseManager getInstance(Context context){
		if(instance==null){
			return new DatabaseManager(context);
		}
		return instance;
	}
	
	public void insertOrUpdateBlacklist(BlacklistInfo info) {
		ContentValues localValues = new ContentValues();
		if(info.getId()!=-1)
			localValues.put(BlacklistConlums._ID, info.getId());
		localValues.put(BlacklistConlums.NAME, info.getName());
		localValues.put(BlacklistConlums.NUMBER, info.getPhone_num());
		localValues.put(BlacklistConlums.PHONE_MODE, info.getPhone_mode());
		localValues.put(BlacklistConlums.MESSAGE_MODE, info.getMsg_mode());
		mContext.getContentResolver().insert(URIConstants.URI_BLACKLIST,
				localValues);
	}
	
	public void insertOrUpdateManyBlacklist(List<BlacklistInfo> infos) {
		for (BlacklistInfo info : infos) {
			ContentValues localValues = new ContentValues();
			if(info.getId()!=-1)
				localValues.put(BlacklistConlums._ID, info.getId());
			localValues.put(BlacklistConlums.NAME, info.getName());
			localValues.put(BlacklistConlums.NUMBER, info.getPhone_num());
			localValues.put(BlacklistConlums.PHONE_MODE, info.getPhone_mode());
			localValues.put(BlacklistConlums.MESSAGE_MODE, info.getMsg_mode());
			mContext.getContentResolver().insert(URIConstants.URI_BLACKLIST,
					localValues);
		}
	}
	
	public void deleteBlacklistEntry(int id){
		mContext.getContentResolver().delete(URIConstants.URI_BLACKLIST, BlacklistConlums._ID+"="+id, null);
	}
	
	public void clearAllBlacklist(){
		mContext.getContentResolver().delete(URIConstants.URI_BLACKLIST, null, null);
	}
	
	
	public void insertOrUpdatePBR(PBRInfo info){
		ContentValues localValues = new ContentValues();
		localValues.put(PBRConlums.NAME, info.getName());
		localValues.put(PBRConlums.NUMBER, info.getPhone_num());
		localValues.put(PBRConlums.BLOCK_TIME, info.getBlock_time());
		mContext.getContentResolver().insert(URIConstants.URI_PBR,
				localValues);
	}
	
	public void deletePBREntry(int id){
		mContext.getContentResolver().delete(URIConstants.URI_PBR, PBRConlums._ID+"="+id, null);
	}
	
	public void clearAllPBR(){
		mContext.getContentResolver().delete(URIConstants.URI_PBR, null, null);
	}
	
}
