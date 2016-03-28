package cn.com.byg.collecttest;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.com.byg.collecttest.animation.AnimationTextActivity;
import cn.com.byg.collecttest.bluetooth.BluetoothTestActivity;
import cn.com.byg.collecttest.db.DatabaseActivity;
import cn.com.byg.collecttest.download.ApkDownLoadTestActivity;
import cn.com.byg.collecttest.download.BreakpointResumeActivity;
import cn.com.byg.collecttest.dragview.DragGridViewActivity;
import cn.com.byg.collecttest.net.ConnectStateUtils;
import cn.com.byg.collecttest.pad.PadSettingActivity;
import cn.com.byg.collecttest.utils.UrlClickSpan;
import cn.com.byg.collecttest.viewpage.ViewPagerActivity;
import cn.com.byg.collecttest.viewpage.fragment.ViewPagerFragmentActivity;
import cn.com.byg.collecttest.web.WebViewTestActivity;

/**
 * @ClassName   NavigationLeftFragment
 * @Description 侧滑栏导航页
 * @author      NeoPi
 *
 * 									_oo0oo_
 * 								   o8888888o
 * 								   88" . "88
 * 								   (| -_- |)
 * 									o\ = /o
 * 								____/'---'\____
 * 							  .   ' \\| |// '   .
 *							   / \\||| : |||// \
 * 							 / _||||| -:- |||||_ \
 *  						   | | \\\ - /// | |
 *  						 | \_| ' \---/'' | |
 *  					      \ .-\__ `-` ___/-. /
 *  					   ___`. .' /--.--\ `. . __
 *                    	."" '< `.___\_<|>_/___.' >'""
 *  				   | | : `- \` .;`\_/`;. `/ -` : | |
 *  					 \ \ `-. \_ __\ /__ _/ .-` / /
 *  			 ======`_.____`_.___\_____/___.-`____.-`======
 *  								'=---='
 *
 * 			    ...............................................
 * 								 佛祖镇楼  , BUG辟易
 * 				佛曰:
 *  						写字楼里写字间  , 写字间里程序猿
 * 							程序人生写程序  , 又拿程序换酒钱
 * 							酒醒只在网上坐  , 酒醉还在网下眠
 * 							酒醉酒醒日复日  , 网上网下年复年
 * 							但愿老死电脑前  , 不愿鞠躬老板前
 * 							奔驰宝马贵者取  , 公交自行程序猿
 * 							别人笑我太疯癫  , 我笑自己命太贱
 * 							不见满街漂亮妹  , 哪个归得程序猿
 */


public class MainActivity extends BaseAvtivity {


	private TextView mtextView = null;
	private String content = "NeoPi的博客----------------------------------------------------------------http://blog.csdn.net/b992379702b";
	private Button mWebBT  = null; // webview 测试按钮
	private Button mDataBt = null; // 数据库 测试按钮
	private Button mDragBt = null; // 可拖拽视图view
	private Button mtest = null;
	private Button mBtn5 = null;
	private Button mBtn6 = null;
	private Button mBtn7 = null;
	private Button mBtn8 = null;
	private Button mBtn9 = null;
    private Button mBtn10 = null;
    private Button mBtn11 = null;
    private Button mBtn12 = null;
    private Button mBtn13 = null;
    private Button mBtn14 = null;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.d("123", "MainActivity onCreate");
		setContentView(R.layout.activity_main);
		
		initView();
		setListener();

//        IntentFilter filter = new IntentFilter();
//        filter.setPriority(1000);
//        filter.addAction("android.provider.Telephony.SMS_RECEIVED");
//        registerReceiver(reveive,filter);
	}

    /**
	 * 初始化视图
	 */
	private void initView() {

		mtextView = (TextView) findViewById(R.id.title);
		mWebBT = (Button) findViewById(R.id.webview_bt);
		mDataBt = (Button) findViewById(R.id.db_bt);
		mDragBt = (Button) findViewById(R.id.drag_bt);
		mtest = (Button) findViewById(R.id.drag_bt1);
		mBtn5 = (Button) findViewById(R.id.btn5);
		mBtn6 = (Button) findViewById(R.id.btn6);
		mBtn7 = (Button) findViewById(R.id.btn7);
		mBtn8 = (Button) findViewById(R.id.btn8);
		mBtn9 = (Button) findViewById(R.id.btn9);
        mBtn10 = (Button) findViewById(R.id.btn10);
        mBtn11 = (Button) findViewById(R.id.btn11);
        mBtn12 = (Button) findViewById(R.id.btn12);
        mBtn13 = (Button) findViewById(R.id.btn13);
        mBtn14 = (Button) findViewById(R.id.btn14);
	}
	
	/**
	 * 设置监听
	 */
	private void setListener() {
		mWebBT.setOnClickListener(mListener);
		mDataBt.setOnClickListener(mListener);
		mDragBt.setOnClickListener(mListener);
		mtest.setOnClickListener(mListener);
		mBtn5.setOnClickListener(mListener);
		mBtn6.setOnClickListener(mListener);
		mBtn7.setOnClickListener(mListener);
		mBtn8.setOnClickListener(mListener);
		mBtn9.setOnClickListener(mListener);
        mBtn10.setOnClickListener(mListener);
        mBtn11.setOnClickListener(mListener);
        mBtn12.setOnClickListener(mListener);
        mBtn13.setOnClickListener(mListener);
        mBtn14.setOnClickListener(mListener);

		CharSequence richText = Html.fromHtml(content);
		setClickSpan();
		mtextView.setText(richText);
	}

	View.OnClickListener mListener = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			// 跳转到 webview 测试页
			case R.id.webview_bt:
				if (ConnectStateUtils.getNetEnable(MainActivity.this)) { // 网络可用
					customStartActivity(MainActivity.this, WebViewTestActivity.class);
				} else { //网络不可用的时候，需要跳转到网络设置页面
					startSystemSetting();
				}
				break;
			// 跳转到数据库测试页
			case R.id.db_bt:
                customStartActivity(MainActivity.this, DatabaseActivity.class);
			    break;
			// 跳转到可拖拽页面
			case R.id.drag_bt:
			    customStartActivity(MainActivity.this, DragGridViewActivity.class);
				break;
		    // 弹出来一个Dialog
			case R.id.drag_bt1:
				showMyDialog();
				break;
			case R.id.btn5:
				String str = "链接可用";
				if (!ConnectStateUtils.getNetEnable(MainActivity.this)) {
					str = "链接不可用";
				}
				Toast.makeText(MainActivity.this, "当前网络状态:"+str, Toast.LENGTH_SHORT).show();
				break;
			case R.id.btn6:
				customStartActivity(MainActivity.this, ViewPagerFragmentActivity.class);
				break;
            // 跳转到无线循环播放viewpager测试页
			case R.id.btn7:
                customStartActivity(MainActivity.this, ViewPagerActivity.class);
				break;
            // 跳转到蓝牙测试页
			case R.id.btn8:
				customStartActivity(MainActivity.this,BluetoothTestActivity.class);
				break;
            // 下载测试
			case R.id.btn9:
				customStartActivity(MainActivity.this, ApkDownLoadTestActivity.class);
				break;
            // 断点续传测试
            case R.id.btn10:
                customStartActivity(MainActivity.this, BreakpointResumeActivity.class);
                break;
            case R.id.btn11:
				customStartActivity(MainActivity.this,AnimationTextActivity.class);
                break;
            case R.id.btn12:
            	customStartActivity(MainActivity.this, PadSettingActivity.class);
                break;
            case R.id.btn13:

                break;
            case R.id.btn14:

                break;
			default:
				break;
			}
		}
	};
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

    }

	private void setClickSpan(){

		Pattern pattern = Pattern.compile("[http|https]+[://]",Pattern.CASE_INSENSITIVE);
		Matcher m = pattern.matcher(content);

		int startPointer = 0;
		SpannableString sps = new SpannableString(content);
		while(m.find(startPointer)){
			int end = m.end();
			String hint = m.group();
			UrlClickSpan clickSpan = new UrlClickSpan(this,hint);
			sps.setSpan(clickSpan,end-hint.length(),end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
			startPointer = end;
		}
	}

    /**
	 * 跳转到系统网络设置页面
	 */
	protected void startSystemSetting() {
		Intent intent = null;
		int sdkVersion = android.os.Build.VERSION.SDK_INT;
		if (sdkVersion > 11) {
			intent = new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS);
		} else {
			intent = new Intent();
			ComponentName mName = new ComponentName("com.android.settings", "com.android.settings.WirelessSettings");
			intent.setComponent(mName);
			intent.setAction("android.intent.action.View");
		}
		startActivity(intent);
	}



	/**
	 * 
	 */
	protected void showMyDialog() {
		AlertDialog.Builder mBuilder = new Builder(this);
		mBuilder.setMessage("这是一个dialog").setTitle("提示").create().show();
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	@Override
	protected void onRestart() {
		super.onRestart();
		Log.d("123", "MainActivity onRestart");
	}

	@Override
	protected void onStart() {
		super.onStart();
		Log.d("123", "MainActivity onStart");
	}


	@Override
	protected void onResume() {
		super.onResume();
		Log.d("123", "MainActivity onResume");
	}

	@Override
	protected void onPause() {
		super.onPause();
		Log.d("123", "MainActivity onPause");
	}

	@Override
	protected void onStop() {
		super.onStop();
		Log.d("123", "MainActivity onStop");
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
//        unregisterReceiver(reveive);
		Log.d("123", "MainActivity onDestroy");
	}
	
	
}
