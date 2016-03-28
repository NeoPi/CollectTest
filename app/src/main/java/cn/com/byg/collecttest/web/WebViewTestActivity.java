package cn.com.byg.collecttest.web;

import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import cn.com.byg.collecttest.BaseAvtivity;
import cn.com.byg.collecttest.R;

/**
 * 
 * @author NeoPi
 */
public class WebViewTestActivity extends BaseAvtivity {

	WebView mWebView = null;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.webview_layout);
		intview();
		
	}
	
	/**
	 * 初始化视图
	 */
	public void intview(){
		mWebView = (WebView) findViewById(R.id.webview);
		//设置WebView属性，能够执行Javascript脚本  
		mWebView.getSettings().setJavaScriptEnabled(true);
		//设置WebView加载的地址URL
		mWebView.loadUrl("http://www.baidu.com/");
		//设置Web视图  
		mWebView.setWebViewClient(new HelloWebViewClient ());  
	}
	
	class HelloWebViewClient extends WebViewClient{
		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url) {
			view.loadUrl(url);
			return true;
		}
	}

    @Override
    public void onBackPressed() {
        customFinishActivity();
    }
}
