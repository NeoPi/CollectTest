package cn.com.byg.collecttest.utils;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.support.v4.app.NotificationCompat;

import java.text.SimpleDateFormat;
import java.util.Date;

import cn.com.byg.collecttest.R;

/**
 * @author NeoPi.
 * @date 2015/8/20
 */
public class NotificationUtils {

    private Context mCtx= null;
    private static NotificationUtils notifi = null;
    private NotificationManager manager = null;

    public NotificationUtils(Context mCtx) {
        this.mCtx = mCtx;
        manager = (NotificationManager) mCtx.getSystemService(Context.NOTIFICATION_SERVICE);
    }

    public static NotificationUtils newInstance(Context mCtx){

        if (notifi == null){
            notifi = new NotificationUtils(mCtx);
        }
        return notifi;
    }

    /**
     * 通知Notification 显示
     * @param content
     * @param receiveTime
     * @param senderNumber
     */
    public void notifiSMS(String content, Long receiveTime, String senderNumber,int order) {

        Date date = new Date(receiveTime);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Notification notifi = new NotificationCompat.Builder(mCtx)
                .setContentTitle(senderNumber)
                .setWhen(receiveTime)
                .setContentText(content)
                .setSmallIcon(R.drawable.ic_launcher)
                .setAutoCancel(true).setDefaults(Notification.DEFAULT_ALL)
                .build();
        manager.notify(order,notifi);
    }
}
