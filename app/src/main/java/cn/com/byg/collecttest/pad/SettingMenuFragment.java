package cn.com.byg.collecttest.pad;

import cn.com.byg.collecttest.R;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * @author NeoPi
 * @date 2015-12-10
 * @FileName SettingMenuFragment.java
 */
public class SettingMenuFragment extends Fragment implements OnClickListener {

	private View convertView;

	private Button bt_ring = null;
	private Button bt_net = null;
	private Button bt_lock = null;
	OnClickMenuItemListener mListener;

	public void setClickMenuListener(OnClickMenuItemListener mListener) {
		this.mListener = mListener;
	}

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		convertView = inflater.inflate(R.layout.setting_menu_layout, null);
		initView();
		return convertView;
	}

	/**
	 * 
	 */
	private void initView() {
		bt_ring = (Button) convertView.findViewById(R.id.bt_ring);
		bt_net = (Button) convertView.findViewById(R.id.bt_network);
		bt_lock = (Button) convertView.findViewById(R.id.bt_numlock);

		bt_ring.setOnClickListener(this);
		bt_net.setOnClickListener(this);
		bt_lock.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.bt_ring:
			setCallBack(PadSettingActivity.INDEX_TAG);
			break;
		case R.id.bt_network:
			setCallBack(PadSettingActivity.INDEX_NET);
			break;
		case R.id.bt_numlock:
			setCallBack(PadSettingActivity.INDEX_LOCK);
			break;
		}
	}

	/**
	 * @param indexTag
	 */
	private void setCallBack(String indexTag) {
		if (mListener != null) {
			mListener.onClickMenu(indexTag);
		}
	}

	public interface OnClickMenuItemListener {
		public void onClickMenu(String type);
	}

}
