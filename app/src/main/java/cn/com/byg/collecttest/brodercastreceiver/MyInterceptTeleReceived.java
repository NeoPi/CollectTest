package cn.com.byg.collecttest.brodercastreceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.TelephonyManager;
import android.util.Log;

import cn.com.byg.collecttest.utils.NotificationUtils;

/**
 * @author NeoPi.
 * @date 2015/8/20
 */
public class MyInterceptTeleReceived extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i("123", "您拨打了电话:" + intent.getAction());
        if (intent.getAction().equals(Intent.ACTION_NEW_OUTGOING_CALL)){
            NotificationUtils.newInstance(context).notifiSMS("您拨打了电话",System.currentTimeMillis(),"提示",0);
            Log.i("123","您拨打了电话");
        } else {
            Log.i("123","您有电话进来了");
            TelephonyManager manager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            NotificationUtils.newInstance(context).notifiSMS("您有电话进来了",System.currentTimeMillis(),"提示",0);
        }
    }
}
