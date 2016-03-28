package cn.com.byg.collecttest.utils;

import android.content.*;
import android.preference.*;

/**
 * 配置读写
 * @author wufucheng
 * 
 */
public class PrefUtils {
	
	/**
	 * 清除所有数据
	 * @param context
	 * @return
	 */
	public static boolean clear(Context context) {
		SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
		SharedPreferences.Editor editor = pref.edit();
		editor.clear();
		return editor.commit();
	}
	
	/**
	 * 移除某项数据
	 * @param context
	 * @param key
	 * @return
	 */
	public static boolean remove(Context context, String key) {
		SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
		SharedPreferences.Editor editor = pref.edit();
		editor.remove(key);
		return editor.commit();
	}

	/**
	 * 获取数据
	 */
	public static int getInt(Context context, String key, int defValue) {
		return PreferenceManager.getDefaultSharedPreferences(context).getInt(key, defValue);
	}
	
	public static long getLong(Context context, String key, long defValue) {
		return PreferenceManager.getDefaultSharedPreferences(context).getLong(key, defValue);
	}

	public static String getString(Context context, String key, String defValue) {
		return PreferenceManager.getDefaultSharedPreferences(context).getString(key, defValue);
	}
	
	public static boolean getBoolean(Context context, String key, boolean defValue) {
		return PreferenceManager.getDefaultSharedPreferences(context).getBoolean(key, defValue);
	}

	/**
	 * 存储数据
	 */
	public static boolean putInt(Context context, String key, int value) {
		SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
		SharedPreferences.Editor editor = pref.edit();
		editor.putInt(key, value);
		return editor.commit();
	}
	
	public static boolean putLong(Context context, String key, long value) {
		SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
		SharedPreferences.Editor editor = pref.edit();
		editor.putLong(key, value);
		return editor.commit();
	}
	
	public static boolean putString(Context context, String key, String value) {
		SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
		SharedPreferences.Editor editor = pref.edit();
		editor.putString(key, value);
		return editor.commit();
	}
	
	public static boolean putBoolean(Context context, String key, boolean value) {
		SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
		SharedPreferences.Editor editor = pref.edit();
		editor.putBoolean(key, value);
		return editor.commit();
	}
}
