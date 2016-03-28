package cn.com.byg.collecttest.brodercastreceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;

import cn.com.byg.collecttest.utils.NotificationUtils;

/**
 * @author NeoPi.
 * @date 2015/8/20
 */
public class MyInterceptSmsReceived extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i("123", "您接收到短信:" + intent.getAction());
        Bundle mb = intent.getExtras();
        if (mb != null){
            Object[] pdus = (Object[]) mb.get("pdus");
            for(Object p : pdus){
                byte[] pdu = (byte[]) p;
                SmsMessage message = SmsMessage.createFromPdu(pdu);
                String content = message.getMessageBody();
                String senderNumber = message.getOriginatingAddress();
                NotificationUtils.newInstance(context).notifiSMS(content, message.getTimestampMillis(), senderNumber,1);

                    abortBroadcast();//终止广播
            }
        }
    }
}
