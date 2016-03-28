package cn.com.byg.collecttest.pad;

import cn.com.byg.collecttest.R;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * @author NeoPi
 * @date 2015-12-8
 * @FileName NetWorkCheckFragment.java
 */
public class NetWorkCheckFragment extends Fragment {

	private static NetWorkCheckFragment instance;
	
	public static NetWorkCheckFragment newInstance(){
		if (instance == null) {
			instance = new NetWorkCheckFragment();
			Bundle mBundle = new Bundle();
			instance.setArguments(mBundle);
		}
		return instance;
	}
	
	
	private View contentView = null;
	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

		if (contentView == null) {
			contentView = inflater.inflate(R.layout.network_check_layout, null);
		
		
		} else {
			ViewGroup parentGroup = (ViewGroup) contentView.getParent();
			if (parentGroup != null) {
				parentGroup.removeAllViewsInLayout();
			}
		}
		
		return contentView;
	}
}


