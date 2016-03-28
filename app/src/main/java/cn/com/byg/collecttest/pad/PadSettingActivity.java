package cn.com.byg.collecttest.pad;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import cn.com.byg.collecttest.R;
import cn.com.byg.collecttest.pad.SettingMenuFragment.OnClickMenuItemListener;
import cn.com.byg.collecttest.utils.Logs;

/**
 * @author NeoPi
 * @date 2015-12-8
 * @FileName PadSettingActivity.java
 */
public class PadSettingActivity extends FragmentActivity implements
		OnClickListener, OnClickMenuItemListener {

	private String TAG = PadSettingActivity.class.getSimpleName();

	public final static String INDEX_TAG = "index_tag";
	public final static String INDEX_NET = "index_net";
	public final static String INDEX_LOCK = "index_lock";

	/** 铃声设置fragment */
	private Fragment settingRingFragment = null;
	/** 网络检测fragment */
	private Fragment checkNetWorkFragment = null;
	/** 锁屏页面 */
	private Fragment numLockFragment = null;

	private String mCurFragName = "";

	private Fragment mCurrentFragment = null;
	private FragmentManager mFManager = null;
	private Bundle mBundle = new Bundle();

	SettingMenuFragment menuFragment = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pad_setting_layout);
		menuFragment = new SettingMenuFragment();
		menuFragment.setClickMenuListener(this);
		FragmentManager fm = getSupportFragmentManager();
		FragmentTransaction ft = fm.beginTransaction();
		ft.replace(R.id.fram_menu, menuFragment);
		ft.commit();
		initFragment(INDEX_TAG);
	}

	/**
	 * 根据不同的tag，初始化不同的fragment
	 * 
	 * @param index
	 */
	private void initFragment(String index) {
		if (INDEX_TAG.equals(index)) {
			if (settingRingFragment == null) {
				settingRingFragment = SettingRingFragment.newInstance();
			}
			showFragment(settingRingFragment, INDEX_TAG);
		} else if (INDEX_NET.equals(index)) {
			if (checkNetWorkFragment == null) {
				checkNetWorkFragment = NetWorkCheckFragment.newInstance();
			}
			showFragment(checkNetWorkFragment, INDEX_NET);
		} else if (INDEX_LOCK.equals(index)) {
			if (numLockFragment == null) {
				numLockFragment = NumLockFragment.newInstance(false);
			}
			showFragment(numLockFragment, INDEX_LOCK);
		} else {
			Logs.d(TAG, "切换 其他");
		}
	}

	/**
	 * 根据不同的传参，显示不同的fragment
	 * 
	 * @param fragment
	 * @param destTab
	 */
	private void showFragment(Fragment fragment, String destTab) {
		try {
			if (mCurrentFragment == fragment) {
				return;
			}
			if (mFManager == null) {
				mFManager = getSupportFragmentManager();
			}
			FragmentTransaction mFTransaction = mFManager.beginTransaction();

			Fragment frag = mFManager.getFragment(mBundle, destTab);
			if (null != mCurrentFragment) {
				mFManager.saveFragmentInstanceState(mCurrentFragment);
				mFManager.putFragment(mBundle, mCurFragName, mCurrentFragment);
			}
			if (null != frag) {
				Logs.d(TAG, "Use saved Fragment");
				mFTransaction.attach(frag);
			} else {
				Logs.d(TAG, "Create New Fragment");
				mFTransaction.add(R.id.frame_layout, fragment);
			}
			if (mCurrentFragment != null) {
				mFTransaction.detach(mCurrentFragment);
			}

			mFTransaction.commitAllowingStateLoss();
			mFManager.executePendingTransactions();

			mCurrentFragment = fragment;
			mCurFragName = destTab;

		} catch (Exception e) {
			Logs.e(TAG, "showFragment Exception --> " + e.toString());
			e.printStackTrace();
		}

	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.bt_network:
			initFragment(INDEX_NET);
			break;
		case R.id.bt_ring:
			initFragment(INDEX_TAG);
			break;
		case R.id.bt_numlock:
			initFragment(INDEX_LOCK);
			break;
		}
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		outState.putString("fragment", mCurFragName);
		super.onSaveInstanceState(outState);
	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		showFragment(mCurrentFragment, mCurFragName);
		super.onRestoreInstanceState(savedInstanceState);
	}

	@Override
	public void onClickMenu(String type) {
		initFragment(type);
	}

	// @Override
	// protected void onResume() {
	// showFragment(mCurrentFragment, mCurFragName);
	// super.onResume();
	// }
}
