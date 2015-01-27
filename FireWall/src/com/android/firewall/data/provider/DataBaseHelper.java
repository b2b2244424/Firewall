package com.android.firewall.data.provider;

import com.android.firewall.constants.DatabaseConstants.BlacklistConlums;
import com.android.firewall.constants.DatabaseConstants.PBRConlums;
import com.android.firewall.constants.DatabaseConstants.Tables;
import com.android.firewall.util.LogUtil;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBaseHelper extends SQLiteOpenHelper {

	public DataBaseHelper(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
	}

	private void createTable(SQLiteDatabase db) {
		try {
			db.execSQL("CREATE TABLE " + Tables.BLACKLIST + " ("
					+ BlacklistConlums._ID
					+ " INTEGER PRIMARY KEY AUTOINCREMENT,"
					+ BlacklistConlums.NUMBER + " TEXT,"
					+ BlacklistConlums.NAME + " TEXT,"
					+ BlacklistConlums.PHONE_MODE + " INTEGER,"
					+ BlacklistConlums.MESSAGE_MODE + " INTEGER,"
					+ BlacklistConlums.NULLCOLUMNHACK + " TEXT"
					 + ");");
			db.execSQL("CREATE TABLE " + Tables.PBR + " ("
					+ PBRConlums._ID
					+ " INTEGER PRIMARY KEY AUTOINCREMENT,"
					+ PBRConlums.NUMBER + " TEXT,"
					+ PBRConlums.NAME + " TEXT,"
					+ PBRConlums.BLOCK_TIME + " TEXT,"
					+ PBRConlums.NULLCOLUMNHACK + " TEXT"
					 + ");");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void upgradeVersionFrom1To2(SQLiteDatabase db) {
		try {
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		createTable(db);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		if (oldVersion == 1) {
			upgradeVersionFrom1To2(db);
			oldVersion++;
		}
	}
}
