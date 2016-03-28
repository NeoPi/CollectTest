package cn.com.byg.collecttest.net;

import android.content.Context;
import android.net.ConnectivityManager;

public class ConnectStateUtils {

	
	/**
	 * 获取当前网络状态是否可用 
	 * ConnectivityManager 这个是系统的管理网络链接的服务类，
	 * 在能获取网络权限的情况前，必须先添加权限android.permission.ACCESS_NETWORK_STATE"
	 * @return
	 */
	public static boolean getNetEnable(Context mtx){
		ConnectivityManager manager = (ConnectivityManager) mtx.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (manager.getActiveNetworkInfo() != null) {
			return manager.getActiveNetworkInfo().isAvailable();
		}
		return false;
	}
	
	/**
	 * 获取当期网络链接类型
	 * @return
	 */
	public static int getNetType(Context mtx){
		
		ConnectivityManager manager = (ConnectivityManager) mtx.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (manager.getActiveNetworkInfo() != null) {
			return manager.getActiveNetworkInfo().getType();
		}
		return -1;
	}
}
