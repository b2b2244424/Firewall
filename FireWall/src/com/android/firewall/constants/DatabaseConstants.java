package com.android.firewall.constants;

import android.net.Uri;

public class DatabaseConstants {

	public static final String DATABASE_NAME = "firewall.db";
	public static final int DATABASE_VERSION = 1;

	public static final String AUTHORITY = "com.android.firewall";
	
	public static final String IS_FORBIDDEN="isForbidden";

	public interface TokenConstants {
		public static final int TOKEN_BLACKLIST = 0;
		public static final int TOKEN_PHONEBLOCKRECORD = 1;
		public static final int TOKEN_SBR = 2;
	}

	public interface PathConstants {
		public static final String PATH_BLACKLIST = "blacklistitems";
		public static final String PATH_PBR = "phone_block_record";
		public static final String PATH_SBR = "sms_block_record";
	}

	public interface URIConstants {
		public final static Uri URI_BLACKLIST = Uri.parse("content://"
				+ AUTHORITY + "/" + PathConstants.PATH_BLACKLIST);
		public final static Uri URI_PBR= Uri.parse("content://"
				+ AUTHORITY + "/" + PathConstants.PATH_PBR);
		public final static Uri URI_SBR = Uri.parse("content://"
				+ AUTHORITY + "/" + PathConstants.PATH_SBR);
	}

	public interface Tables {
		public static final String BLACKLIST = "blacklist";
		public static final String PBR="phone_block_record";
		public static final String SBR="sms_block_record";
	}
	
	public interface BlacklistConlums {
		public final static String _ID = "_id";
		public final static String NUMBER = "number";
		public final static String NAME = "name";
		public final static String PHONE_MODE = "phone_mode";
		public final static String MESSAGE_MODE = "message_mode";
		public final static String DEFAULT_SORT_ORDER = "desc";
		public static final String NULLCOLUMNHACK = "nullcolumnhack";
	}
	
	public interface PBRConlums {
		public final static String _ID = "_id";
		public final static String NUMBER = "number";
		public final static String NAME = "name";
		public final static String BLOCK_TIME="block_time";
		public static final String NULLCOLUMNHACK = "nullcolumnhack";
	}
	
	public interface SBRConlums {
		public final static String _ID = "_id";
		public final static String NUMBER = "number";
		public final static String NAME = "name";
		public final static String BLOCK_TIME="block_time";
		public final static String CONTENT="content";
		public static final String NULLCOLUMNHACK = "nullcolumnhack";
	}

}
