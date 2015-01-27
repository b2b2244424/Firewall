package com.android.firewall.util;

import android.util.Log;


public class LogUtil {
	public static void log(String tag,String msg) {
		Log.i(tag, msg);
	}
	
	public static void logd(String tag,String msg){
		Log.d(tag, msg);
	}
	
	public static void loge(String tag,String msg) {
		Log.e(tag, msg);
	}
}
