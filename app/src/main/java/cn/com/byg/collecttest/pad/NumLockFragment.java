package cn.com.byg.collecttest.pad;

import cn.com.byg.collecttest.R;
import cn.com.byg.collecttest.utils.PrefUtils;
import cn.com.byg.collecttest.utils.ToastUtils;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

/**
 * @author NeoPi
 * @date 2015-12-8
 * @FileName NumLockFragment.java
 */
public class NumLockFragment extends Fragment implements OnClickListener {

	private static NumLockFragment instance = null;

	private boolean ISLOCKED;

	public static NumLockFragment newInstance(boolean islock) {
		NumLockFragment instance = new NumLockFragment();
		Bundle mBundle = new Bundle();
		mBundle.putBoolean("islock", islock);
		instance.setArguments(mBundle);
		return instance;
	}

	private View convertView; // 主布局
	private TextView tvInputFirst = null; // 密码第一位 ，
	private TextView tvInputSecond = null;// 密码第二位 ，
	private TextView tvInputThird = null; // 密码第三位 ，
	private TextView tvInputForth = null; // 密码第四位 ，
	private TextView tvKeyOne = null; // 数字1
	private TextView tvKeyTwo = null; // 数字2
	private TextView tvKeyThr = null; // 数字3
	private TextView tvKeyFor = null; // 数字4
	private TextView tvKeyFiv = null; // 数字5
	private TextView tvKeySix = null; // 数字6
	private TextView tvKeySev = null; // 数字7
	private TextView tvKeyEig = null; // 数字8
	private TextView tvKeyNine = null; // 数字9
	private TextView tvKeyZero = null; // 数字0
	private TextView tvKeyDelete = null; // X
	private TextView tvKeyClear = null; // C

	private int[] password = new int[4];

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

		if (convertView == null) {
			convertView = inflater.inflate(R.layout.num_lock_layout, null);
			ISLOCKED = getArguments().getBoolean("islock",false);
			initView();
			initData();
			setListener();

		} else {
			ViewGroup parentGroup = (ViewGroup) convertView.getParent();
			if (parentGroup != null) {
				parentGroup.removeAllViewsInLayout();
			}
		}
		return convertView;
	}

	private void initView() {
		tvInputFirst = (TextView) convertView.findViewById(R.id.tv_input_first);
		tvInputSecond = (TextView) convertView
				.findViewById(R.id.tv_input_second);
		tvInputThird = (TextView) convertView.findViewById(R.id.tv_input_thrid);
		tvInputForth = (TextView) convertView
				.findViewById(R.id.tv_input_fourth);
		tvKeyOne = (TextView) convertView.findViewById(R.id.tv_key_one);
		tvKeyTwo = (TextView) convertView.findViewById(R.id.tv_key_two);
		tvKeyThr = (TextView) convertView.findViewById(R.id.tv_key_three);
		tvKeyFor = (TextView) convertView.findViewById(R.id.tv_key_four);
		tvKeyFiv = (TextView) convertView.findViewById(R.id.tv_key_five);
		tvKeySix = (TextView) convertView.findViewById(R.id.tv_key_six);
		tvKeySev = (TextView) convertView.findViewById(R.id.tv_key_seven);
		tvKeyEig = (TextView) convertView.findViewById(R.id.tv_key_eight);
		tvKeyNine = (TextView) convertView.findViewById(R.id.tv_key_nine);
		tvKeyZero = (TextView) convertView.findViewById(R.id.tv_key_zero);
		tvKeyDelete = (TextView) convertView.findViewById(R.id.tv_key_back);
		tvKeyClear = (TextView) convertView.findViewById(R.id.tv_key_clear);

	}

	/**
	 * 
	 */
	private void initData() {

	}

	/**
	 * 
	 */
	private void setListener() {
		tvKeyOne.setOnClickListener(this);
		tvKeyTwo.setOnClickListener(this);
		tvKeyThr.setOnClickListener(this);
		tvKeyFor.setOnClickListener(this);
		tvKeyFiv.setOnClickListener(this);
		tvKeySix.setOnClickListener(this);
		tvKeySev.setOnClickListener(this);
		tvKeyEig.setOnClickListener(this);
		tvKeyNine.setOnClickListener(this);
		tvKeyZero.setOnClickListener(this);
		tvKeyDelete.setOnClickListener(this);
		tvKeyClear.setOnClickListener(this);
	}

	int position = 0;
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tv_key_one:
			position++;
			setPassword(1);
			break;
		case R.id.tv_key_two:
			position++;
			setPassword(2);
			break;
		case R.id.tv_key_three:
			position++;
			setPassword(3);
			break;
		case R.id.tv_key_four:
			position++;
			setPassword(4);
			break;
		case R.id.tv_key_five:
			position++;
			setPassword(5);
			break;
		case R.id.tv_key_six:
			position++;
			setPassword(6);
			break;
		case R.id.tv_key_seven:
			position++;
			setPassword(7);
			break;
		case R.id.tv_key_eight:
			position++;
			setPassword(8);
			break;
		case R.id.tv_key_nine:
			position++;
			setPassword(9);
			break;
		case R.id.tv_key_zero:
			position++;
			setPassword(0);
			break;
		case R.id.tv_key_back:
			delete();
			break;
		case R.id.tv_key_clear:
			clearPassword();
			break;
		}
	}

	/**
	 * 回退一位
	 */
	private void delete() {
		ToastUtils.show(getActivity(), position+"");
		switch (position) {
		case 1:
			tvInputFirst.setText("");
			break;
		case 2:
			tvInputSecond.setText("");
			break;
		case 3:
			tvInputThird.setText("");
			break;
		case 4:
			tvInputForth.setText("");
			break;
		}
		-- position;
	}

	/**
	 * 清除密码
	 */
	private void clearPassword() {
		for (int i = 0; i < password.length; i++) {
		}
		tvInputFirst.setText("");
		tvInputSecond.setText("");
		tvInputThird.setText("");
		tvInputForth.setText("");
		position = 0;
	}

	/**
	 * 
	 */
	private void setPassword(int arg) {

		if (position > 4) {
			return ;
		}
		switch (position) {
		case 1:
			tvInputFirst.setText("*");
			password[0] = arg;
			break;
		case 2:
			tvInputSecond.setText("*");
			password[1] = arg;
			break;
		case 3:
			tvInputThird.setText("*");
			password[2] = arg;
			break;
		case 4:
			tvInputForth.setText("*");
			password[3] = arg;
			break;
		}
		
		if (position == 4) {
			StringBuffer sBuffer = new StringBuffer();
			sBuffer.append(password[0]).append(password[1]).append(password[2]).append(password[3]);
			if (ISLOCKED) {
				String password = PrefUtils.getString(getActivity(), "password", "");
				if (sBuffer.toString().trim().equals(password)) {
					getActivity().finish();
				}
			} else {
				PrefUtils.putString(getActivity(), "password", sBuffer.toString().trim());
				ToastUtils.show(getActivity(), sBuffer.toString().trim());
			}
			
		}
	}
}
