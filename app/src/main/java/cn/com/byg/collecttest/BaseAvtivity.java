package cn.com.byg.collecttest;

import cn.com.byg.collecttest.pad.LockReceiver;
import cn.com.byg.collecttest.pad.NumLockActivity;
import cn.com.byg.collecttest.utils.Logs;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

/**
 * Created by NeoPi on 2015/8/11.
 */
public class BaseAvtivity extends Activity{

	private LockReceiver screenBroadcastReceiver = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        registerScreenBroadcastReceiver();
    }

	@Override
	protected void onResume() {
		super.onResume();
		
		//注册这个广播
		
	}
	
	private void registerScreenBroadcastReceiver() {
		screenBroadcastReceiver = new LockReceiver();
		IntentFilter intentFilter = new IntentFilter();
		intentFilter.addAction(Intent.ACTION_SCREEN_OFF);//当屏幕锁屏的时候触发
		intentFilter.addAction(Intent.ACTION_SCREEN_ON);//当屏幕解锁的时候触发
		intentFilter.addAction(Intent.ACTION_USER_PRESENT);//当用户重新唤醒手持设备时触发
		registerReceiver(screenBroadcastReceiver, intentFilter);
		Logs.i("screenBR", "screenBroadcastReceiver注册了");
	}
	
	//重写广播
	class ScreenBroadcastReceiver extends BroadcastReceiver{

		@Override
		public void onReceive(Context context, Intent intent) {
			String strAction = intent.getAction();
			if (Intent.ACTION_SCREEN_OFF.equals(strAction)){
				//屏幕锁屏
				Logs.i("screenBR", "屏幕锁屏：ACTION_SCREEN_OFF触发");
			}else if (Intent.ACTION_SCREEN_ON.equals(strAction)){
				//屏幕解锁(实际测试效果，不能用这个来判断解锁屏幕事件)
				//【因为这个是解锁的时候触发，而解锁的时候广播还未注册】
				Logs.i("screenBR", "屏幕解锁：ACTION_SCREEN_ON触发");
				startActivity(new Intent(context, NumLockActivity.class));
			}else if (Intent.ACTION_USER_PRESENT.equals(strAction)){
				//屏幕解锁(该Action可以通过静态注册的方法注册)
				//在解锁之后触发的，广播已注册
				Logs.i("screenBR", "屏幕解锁：ACTION_USER_PRESENT触发");
			}else{
				//nothing
			}
		}
		
	}
	@Override
	protected void onPause() {
		super.onPause();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		unregisterReceiver(screenBroadcastReceiver);
		Logs.i("screenBR", "screenBroadcastReceiver取消注册了");
	}
	/**
     * 页面跳转
     * @param mainActivity
     * @param class1
     */
    protected void customStartActivity(Context mainActivity , Class<?> class1) {
        Intent intent  = new Intent(mainActivity, class1);
        startActivity(intent);
        overridePendingTransition(R.anim.coming_right, R.anim.coming_stand);
    }

    /**
     *
     */
    public void customFinishActivity(){

        finish();
        overridePendingTransition(0,R.anim.gone_left);
    }

    @Override
    public void onBackPressed() {
        customFinishActivity();
    }
    
}
