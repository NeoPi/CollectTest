package cn.com.byg.collecttest.utils;

import android.content.Context;
import android.widget.Toast;


/**
 * @author NeoPi.
 * @date 2015/8/24
 */
public class ToastUtils{

    public static Toast mToast = null;


    /**
     *
     * @param mctx
     * @param sequence
     * @param time
     */
    public synchronized static void show(Context mctx,CharSequence sequence,int time){
        if (mctx == null){
            return ;
        }
        if (mToast == null){
            mToast = Toast.makeText(mctx,sequence,time);
        } else {
            mToast.setText(sequence);
            mToast.setDuration(time);
        }
        mToast.show();
    }

    /**
     *
     * @param mctx
     * @param sequence
     *
     */
    public synchronized static void show(Context mctx,CharSequence sequence){
        if (mctx == null){
            return ;
        }
        if (mToast == null){
            mToast = Toast.makeText(mctx,sequence,Toast.LENGTH_SHORT);
        } else {
            mToast.setText(sequence);
            mToast.setDuration(Toast.LENGTH_SHORT);
        }
        mToast.show();
    }

    /**
     *
     * @param mctx
     * @param id
     * @param time
     */
    public synchronized static void show(Context mctx,int id,int time){
        if (mctx == null){
            return ;
        }
        if (mToast == null){
            mToast = Toast.makeText(mctx,id,time);
        } else {
            mToast.setText(id);
            mToast.setDuration(time);
        }
        mToast.show();
    }


}
