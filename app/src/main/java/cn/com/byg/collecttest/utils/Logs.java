package cn.com.byg.collecttest.utils;

import android.util.Log;

/**
 * @author NeoPi.
 * @date 2015/8/24
 * 此类用于控制Log日志的输出
 */
public class Logs {

    public static boolean isShowLog = true; // 控制Log日志的输出

    public static void setIsShowLog(boolean isShow){
        isShowLog = isShow;
    }

    public static void i(String tag,String msg){
        if (isShowLog)
            Log.i(tag,msg);
    }

    public static void i(String tag,String msg,Throwable tr){
        if (isShowLog){
            Log.i(tag,msg,tr);
        }
    }

    public static void d(String tag,String msg){
        if (isShowLog)
            Log.d(tag,msg);
    }

    public static void d(String tag,String msg,Throwable tr){
        if (isShowLog){
            Log.d(tag,msg,tr);
        }
    }

    public static void e(String tag,String msg){
        if (isShowLog)
            Log.e(tag,msg);
    }

    public static void e(String tag,String msg,Throwable tr){
        if (isShowLog){
            Log.e(tag,msg,tr);
        }
    }

    public static void w(String tag,String msg){
        if (isShowLog)
            Log.w(tag,msg);
    }

    public static void w(String tag,String msg,Throwable tr){
        if (isShowLog){
            Log.w(tag,msg,tr);
        }
    }

    public static void w(String tag,Throwable tr){
        if (isShowLog){
            Log.w(tag,tr);
        }
    }
}
