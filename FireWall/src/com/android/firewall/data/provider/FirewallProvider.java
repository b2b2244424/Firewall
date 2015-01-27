package com.android.firewall.data.provider;

import java.util.ArrayList;

import com.android.firewall.constants.DatabaseConstants;
import com.android.firewall.constants.DatabaseConstants.BlacklistConlums;
import com.android.firewall.constants.DatabaseConstants.PBRConlums;
import com.android.firewall.constants.DatabaseConstants.PathConstants;
import com.android.firewall.constants.DatabaseConstants.Tables;
import com.android.firewall.constants.DatabaseConstants.TokenConstants;
import com.android.firewall.util.LogUtil;

import android.content.ContentProvider;
import android.content.ContentProviderOperation;
import android.content.ContentProviderResult;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.OperationApplicationException;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

public class FirewallProvider extends ContentProvider{
	private DataBaseHelper mSQLDatabaseHelper;
	private SQLiteDatabase mSQLiteDatabase;
	private static final UriMatcher URI_MATCHER;
	static {
		URI_MATCHER = new UriMatcher(UriMatcher.NO_MATCH);
		URI_MATCHER.addURI(DatabaseConstants.AUTHORITY, PathConstants.PATH_BLACKLIST, TokenConstants.TOKEN_BLACKLIST);
		URI_MATCHER.addURI(DatabaseConstants.AUTHORITY, PathConstants.PATH_PBR, TokenConstants.TOKEN_PHONEBLOCKRECORD);
		URI_MATCHER.addURI(DatabaseConstants.AUTHORITY, PathConstants.PATH_SBR, TokenConstants.TOKEN_SBR);
	}
	
	@Override
	public boolean onCreate() {
		mSQLDatabaseHelper = new DataBaseHelper(getContext(), DatabaseConstants.DATABASE_NAME, 
				null, DatabaseConstants.DATABASE_VERSION);
		mSQLiteDatabase = mSQLDatabaseHelper.getReadableDatabase();
		if(mSQLiteDatabase == null) {
			return false;
		}
		LogUtil.log("provider", "firewall provider create OK");
		return true;
	}

	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		int result = 0;
		switch (URI_MATCHER.match(uri)) {
		case TokenConstants.TOKEN_BLACKLIST:
			result = mSQLiteDatabase.delete(Tables.BLACKLIST, selection, selectionArgs);
			break;
		case TokenConstants.TOKEN_PHONEBLOCKRECORD:
			result = mSQLiteDatabase.delete(Tables.PBR, selection, selectionArgs);
			break;
		default:
			break;
		}
		getContext().getContentResolver().notifyChange(uri, null);
		return result;
	}

	@Override
	public String getType(Uri uri) {
		switch (URI_MATCHER.match(uri)) {
		case TokenConstants.TOKEN_BLACKLIST:
			return "vnd.android.cursor.dir/blacklist";
		case TokenConstants.TOKEN_PHONEBLOCKRECORD:
			return "vnd.android.cursor.dir/phone_block_record";
		default:
			return null;
		}
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		long rowId = 0;
		Uri insertRowUri = null;
		switch (URI_MATCHER.match(uri)) {
		case TokenConstants.TOKEN_BLACKLIST:
			rowId = mSQLiteDatabase.replace(Tables.BLACKLIST, BlacklistConlums.NULLCOLUMNHACK, values);
			break;
		case TokenConstants.TOKEN_PHONEBLOCKRECORD:
			rowId = mSQLiteDatabase.replace(Tables.PBR, PBRConlums.NULLCOLUMNHACK, values);
			break;
		default:
			break;
		}
		if(rowId > 0) {
			insertRowUri = ContentUris.withAppendedId(uri, rowId);
		}
		getContext().getContentResolver().notifyChange(uri, null);
		return insertRowUri;
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {
		Cursor cursor = null;
		switch (URI_MATCHER.match(uri)) {
		case TokenConstants.TOKEN_BLACKLIST:
			cursor = mSQLiteDatabase.query(Tables.BLACKLIST, projection, selection, selectionArgs, null, null, sortOrder);
			break;
		case TokenConstants.TOKEN_PHONEBLOCKRECORD:
			cursor = mSQLiteDatabase.query(Tables.PBR, projection, selection, selectionArgs, null, null, sortOrder);
			break;
		default:
			break;
		}
		cursor.setNotificationUri(getContext().getContentResolver(), uri);
		return cursor;
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {
		String tableName = null;
		switch (URI_MATCHER.match(uri)) {
		case TokenConstants.TOKEN_BLACKLIST:
			tableName = Tables.BLACKLIST;
			break;
		case TokenConstants.TOKEN_PHONEBLOCKRECORD:
			tableName = Tables.PBR;
			break;
		default:
			break;
		}
		int updateCount = mSQLiteDatabase.update(tableName, values, selection,
				selectionArgs);
		getContext().getContentResolver().notifyChange(uri, null);
		return updateCount;
	}
	
	@Override
	public ContentProviderResult[] applyBatch(
			ArrayList<ContentProviderOperation> operations)
			throws OperationApplicationException {
		mSQLiteDatabase.beginTransaction();
		try {
			ContentProviderResult[] results = super.applyBatch(operations);
			mSQLiteDatabase.setTransactionSuccessful();
			return results;
		} finally {
			mSQLiteDatabase.endTransaction();
		}
	}

}

