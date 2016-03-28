package cn.com.byg.collecttest.pad;

import cn.com.byg.collecttest.R;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * @author NeoPi
 * @date 2015-12-8
 * @FileName SettingRingFragment.java
 */
public class SettingRingFragment extends Fragment {
	
	private static SettingRingFragment instance = null;
	
	public static SettingRingFragment newInstance(){
		if (instance == null) {
			instance = new SettingRingFragment();
			Bundle args = new Bundle();
			instance.setArguments(args);
		}
		return instance;
	}
	

	private View contentView = null;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if (contentView == null) {
			contentView = inflater.inflate(R.layout.setting_ring_layout, null);
		
			
		} else {
			ViewGroup parent = (ViewGroup) contentView.getParent();
			if (parent != null) {
				parent.removeAllViewsInLayout();
			}
		}
		return contentView;
	}
}
