package cn.com.byg.collecttest.pad;

import java.lang.reflect.Method;

import cn.com.byg.collecttest.R;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;

/**
 * @author NeoPi
 * @date 2015-12-8
 * @FileName NumLockActivity.java
 */
public class NumLockActivity extends FragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.num_lock_activity_layout);

		NumLockFragment mFragment = NumLockFragment.newInstance(true);
		FragmentManager fmManager = getSupportFragmentManager();
		FragmentTransaction fTransaction = fmManager.beginTransaction();
		fTransaction.replace(R.id.num_lock_frame_layout, mFragment);
		fTransaction.commitAllowingStateLoss();
	}

//	@Override
//	public boolean onKeyDown(int keyCode, KeyEvent event) {
//		if (keyCode == KeyEvent.KEYCODE_HOME && keyCode == KeyEvent.KEYCODE_BACK) {
//			return true;
//		}
//		return super.onKeyDown(keyCode, event);
//	}
//	
//	@Override
//	public void onBackPressed() {
//	}

	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		// TODO Auto-generated method stub
		super.onWindowFocusChanged(hasFocus);
		try {

			Object service = getSystemService("statusbar");
			Class<?> statusbarManager = Class
					.forName("android.app.StatusBarManager");
			Method test = statusbarManager.getMethod("collapse");
			test.invoke(service);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
