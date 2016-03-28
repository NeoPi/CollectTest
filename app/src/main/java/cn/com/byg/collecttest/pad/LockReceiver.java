package cn.com.byg.collecttest.pad;

import cn.com.byg.collecttest.utils.Logs;
import cn.com.byg.collecttest.utils.ToastUtils;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * @author NeoPi
 * @date 2015-12-8
 * @FileName LockReceiver.java
 */
public class LockReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		ToastUtils.show(context, intent.getAction());
		String strAction = intent.getAction();
		if (Intent.ACTION_SCREEN_OFF.equals(strAction)){
			//屏幕锁屏
			Logs.i("screenBR", "屏幕锁屏：ACTION_SCREEN_OFF触发");
			context.startActivity(new Intent(context, NumLockActivity.class));
		}else if (Intent.ACTION_SCREEN_ON.equals(strAction)){
			//屏幕解锁(实际测试效果，不能用这个来判断解锁屏幕事件)
			//【因为这个是解锁的时候触发，而解锁的时候广播还未注册】
			Logs.i("screenBR", "屏幕解锁：ACTION_SCREEN_ON触发");
//			context.startActivity(new Intent(context, NumLockActivity.class));
		}else if (Intent.ACTION_USER_PRESENT.equals(strAction)){
			//屏幕解锁(该Action可以通过静态注册的方法注册)
			//在解锁之后触发的，广播已注册
			Logs.i("screenBR", "屏幕解锁：ACTION_USER_PRESENT触发");
		}else{
			//nothing
		}
	}

}


