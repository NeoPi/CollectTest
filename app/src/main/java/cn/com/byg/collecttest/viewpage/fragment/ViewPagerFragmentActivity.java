package cn.com.byg.collecttest.viewpage.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;

import java.util.ArrayList;

import cn.com.byg.collecttest.R;

/**
 * @author NeoPi.
 * @date 2015/8/19
 */
public class ViewPagerFragmentActivity extends FragmentActivity{

    private ViewPager mViewPager = null;
    private PagerTabStrip mTab = null;
    private FragmentManager mFragmentManager = null;

    private ArrayList<String> title = new ArrayList<String>();
    private ArrayList<Fragment> mFragment = new ArrayList<Fragment>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.viewpager_fragment_layout);

        initView();
    }

    /**
     * 初始化视图
     */
    private void initView() {
        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        mTab = (PagerTabStrip) findViewById(R.id.pager_tab);

        title.add("第一页");
        title.add("第二页");
        title.add("第三页");
        title.add("第四页");
        title.add("第五页");
        title.add("第六页");


        mFragment.add(new FistFragment());
        mFragment.add(new SecondFragment());
        mFragment.add(new ThirdFragment());
        mFragment.add(new FistFragment());
        mFragment.add(new SecondFragment());
        mFragment.add(new ThirdFragment());

        mTab.setTabIndicatorColor(Color.BLUE);
        mTab.setTextSpacing(20);
        mViewPager.setOnPageChangeListener(mlistener);
        mFragmentManager = getSupportFragmentManager();
        mViewPager.setAdapter(new ViewPagerFragmentAdapter(mFragmentManager));
    }

    ViewPager.OnPageChangeListener mlistener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int i, float v, int i2) {

        }

        @Override
        public void onPageSelected(int i) {

        }

        @Override
        public void onPageScrollStateChanged(int i) {

        }
    };

    class ViewPagerFragmentAdapter extends FragmentPagerAdapter{

        public ViewPagerFragmentAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int i) {
            return mFragment.get(i);
        }

        @Override
        public int getCount() {
            return title.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return title.get(position);
        }
    }
}
