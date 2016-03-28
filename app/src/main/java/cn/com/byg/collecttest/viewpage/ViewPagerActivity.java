package cn.com.byg.collecttest.viewpage;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import cn.com.byg.collecttest.R;

/**
 * Created by ypy on 2015/8/7.
 */
public class ViewPagerActivity extends Activity{

    private static String TAG = ViewPagerActivity.class.getSimpleName();


    private List<AdverInfo> list = new ArrayList<AdverInfo>();

    private ViewPager mPager = null;
    private LinearLayout mLL = null;

    private MyViewPagerAdapter adapter ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.viewpage_test_layout);

        initView();
        initData();
    }

    /**
     * 初始化视图
     */
    private void initView() {
        mPager = (ViewPager)findViewById(R.id.viewpager);
        mLL = (LinearLayout)findViewById(R.id.dotLL);

        mPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i2) {
            }

            @Override
            public void onPageSelected(int i) {
                Log.i("123","seleceted:" +i);
                updateDotState();
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }

    /**
     * 初始化数据
     */
    private void initData() {
        list.add(new AdverInfo(R.drawable.a, "巩俐不低俗，我就不能低俗"));
        list.add(new AdverInfo(R.drawable.b, "朴树又回来了，再唱经典老歌引百万人同唱啊"));
        list.add(new AdverInfo(R.drawable.c, "揭秘北京电影如何升级"));
        list.add(new AdverInfo(R.drawable.d, "乐视网TV版大放送"));
        list.add(new AdverInfo(R.drawable.e, "热血屌丝的反杀"));

        adapter = new MyViewPagerAdapter(this,list);
        mPager.setCurrentItem(0);
        mPager.setAdapter(adapter);

        initDot();
        mHandler.sendEmptyMessageDelayed(0, 3000);
    }


    Handler mHandler = new Handler(){

        @Override
        public void handleMessage(Message msg) {
            mPager.setCurrentItem(mPager.getCurrentItem()+1);
            mHandler.sendEmptyMessageDelayed(0,3000);
        }
    };
    /**
     * 初始化viewpager下的小红点
     */
    private void initDot() {
        for (int i = 0;i < list.size();i++){
            View view = new View(this);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(8,8);
            if (i != 0){
                params.leftMargin = 5;
            }

            view.setLayoutParams(params);
            view.setBackgroundResource(R.drawable.dot_select);
            mLL.addView(view);
        }
        updateDotState();
    }

    /**
     * 更新点的状态
     */
    private void updateDotState(){
        int current = mPager.getCurrentItem() % list.size();
        for (int i = 0; i < mLL.getChildCount();i++){
            mLL.getChildAt(i).setEnabled(i == current);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHandler.removeMessages(0);
    }
}
