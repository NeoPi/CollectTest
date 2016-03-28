package cn.com.byg.collecttest.viewpage;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import cn.com.byg.collecttest.R;

/**
 * Created by NeoPi on 2015/8/7.
 */
public class MyViewPagerAdapter extends PagerAdapter {


    List<AdverInfo> mList ;
    Context mCtx;

    public MyViewPagerAdapter(Context ctx,List<AdverInfo> mList){
        this.mCtx = ctx;
        this.mList = mList;
    }


    @Override
    public int getCount() {
        return Integer.MAX_VALUE; // 无限循环，此处设置的是最大整形
    }

    @Override
    public boolean isViewFromObject(View view, Object o) {
        return view == o;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = View.inflate(mCtx,R.layout.viewpager_item,null);
        ImageView image = (ImageView)view.findViewById(R.id.item_img);
        TextView title = (TextView)view.findViewById(R.id.item_title);

        AdverInfo info = mList.get(position % mList.size()); // position % mlist.size() 取余此处是循环的关键
        if (info != null){
            title.setText(info.getTitle());
            image.setBackgroundResource(info.getResId());
        }

        container.addView(view); // 必须要吧view添加到容器中
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ((ViewPager)container).removeView((View)object);
    }


}
