package cn.com.byg.collecttest.animation;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import cn.com.byg.collecttest.BaseAvtivity;
import cn.com.byg.collecttest.R;
import cn.com.byg.collecttest.utils.ToastUtils;

/**
 * @author NeoPi
 * @date 2015/9/9
 * @TODO 用一句话描述这个类的作用
 */
public class AnimationTextActivity extends BaseAvtivity implements View.OnClickListener {


    private int[] res = {R.id.a,R.id.b,R.id.c,R.id.d,R.id.e,R.id.f,R.id.g};
    private int[] res_ = {R.id.a_,R.id.b_,R.id.c_,R.id.d_,R.id.e_};
    private List<ImageView> imageViewList = new ArrayList<ImageView>();
    private List<ImageView> imageViewList_ = new ArrayList<ImageView>();

    private double angle = Math.PI/2/(res_.length-2);

    private boolean flag = true;
    private boolean flag_ = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.animation_layout);

        initView();

    }

    private void initView() {
        for (int i = 0;i< res.length;i++) {
            ImageView imageView = (ImageView) findViewById(res[i]);
            imageView.setOnClickListener(this);
            imageViewList.add(imageView);
        }

        for (int i = 0;i< res_.length;i++) {
            ImageView imageView = (ImageView) findViewById(res_[i]);
            imageView.setOnClickListener(this);
            imageViewList_.add(imageView);
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.a:
                startAnimation();
                break;
            case R.id.a_:
                startArcAnimation();
                break;
            default:
                ToastUtils.show(AnimationTextActivity.this,"click:"+v.getId());
                break;
        }
    }

    private void startArcAnimation() {
        if (flag_){
            beginArcAnimation();
        } else {
            backArcAnimation();
        }

    }

    private void backArcAnimation() {

        for (int i = 1;i<res_.length;i++){

            PropertyValuesHolder p1 = PropertyValuesHolder.ofFloat("translationX",(float)(Math.cos(angle * (i-1)) * 300),0F);
            PropertyValuesHolder p2 = PropertyValuesHolder.ofFloat("translationY",(float)(Math.sin(angle * (i-1)) * 300),0F);

            ObjectAnimator.ofPropertyValuesHolder(imageViewList_.get(i),p1,p2).setDuration(500).start();

        }

        flag_ = true;

    }

    private void beginArcAnimation() {

        for (int i = 1;i<res_.length;i++){

            Log.i("123",imageViewList_.get(i).getY()+"");

            PropertyValuesHolder p1 = PropertyValuesHolder.ofFloat("translationX", 0F, (float) (Math.cos(angle * (i-1)) * 300));
            PropertyValuesHolder p2 = PropertyValuesHolder.ofFloat("translationY", 0F, (float) (Math.sin(angle * (i-1)) * 300));

            ObjectAnimator.ofPropertyValuesHolder(imageViewList_.get(i),p1,p2).setDuration(500).start();
        }

        flag_ = false;
    }

    private void startAnimation() {
        if (flag){
            beginAnimation();
        } else {
            backAnimation();
        }
    }

    /**
     * 展开动画
     */
    private void beginAnimation() {

        for (int i = 0; i < res.length;i++){
            ObjectAnimator oa = ObjectAnimator.ofFloat(imageViewList.get(i), "translationX", 0F, i * 150F);
            oa.setDuration(500);
            oa.setStartDelay(i*300);
            oa.start();
        }
        flag = false;
    }


    /**
     * 回收动画
     */
    private void backAnimation() {
        for (int i = res.length - 1; i >= 0 ;i--){
            ObjectAnimator oa = ObjectAnimator.ofFloat(imageViewList.get(i), "translationX",i*150F,0F);
            oa.setDuration(500);
            oa.setStartDelay(i * 300);
            oa.start();
        }
        flag = true;
    }

}
