package cn.com.byg.collecttest.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * @author NeoPi
 * @date 2015-12-9
 * @FileName SharedUtils.java
 */
public class SharedUtils {

	public static void putInt(Context context,int arg){
		SharedPreferences sp = context.getSharedPreferences("test", Activity.MODE_PRIVATE);
	}
}


