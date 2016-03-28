package cn.com.byg.collecttest.download.service;

import android.app.Service;
import android.content.Intent;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;

import org.apache.http.HttpStatus;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;

import cn.com.byg.collecttest.download.bean.FileInfo;
import cn.com.byg.collecttest.utils.Logs;

/**
 * @author NeoPi.
 * @date 2015/8/24
 *
 * @
 */
public class FileDownLoadService extends Service{

    public static final String DOWNLOAD_PATH =
            Environment.getExternalStorageDirectory().getAbsolutePath()+"/DownLoadTest/";
    public static final String ACTION_START = "ACTION_START";
    public static final String ACTION_STOP = "ACTION_STOP";
    public static final String ACTION_UPDATE = "ACTION_UPDATE";
    public static final String DOWN_KEY = "FILEINFO";

    private DownLoadTask mTask = null;

    private static final int MSG_INT = 0;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        if (intent != null) {
            String action = intent.getAction();
            if(action != null){

                if (action.equals(ACTION_START)){
                    FileInfo fileInfo = (FileInfo) intent.getSerializableExtra(DOWN_KEY);
                    Logs.i("123",fileInfo.toString()+"");
                    mTask = new DownLoadTask(FileDownLoadService.this,fileInfo);
                    // 初始化线程
                    new InitThread(fileInfo).start();
                }

                if (action.equals(ACTION_STOP)){
                    FileInfo fileInfo = (FileInfo) intent.getSerializableExtra(DOWN_KEY);
                    Logs.i("123",fileInfo.toString()+"");
                    if (mTask != null)
                        mTask.isPause = true;
                }

            }
        }
        return super.onStartCommand(intent, flags, startId);
    }


    class InitThread extends Thread{


        private FileInfo mFileInfo = null;

        public InitThread(FileInfo fileInfo){
            this.mFileInfo = fileInfo;
        }

        @Override
        public void run() {
            HttpURLConnection conn = null;
            RandomAccessFile raf = null;
            try {
                // 链接网络文件
                URL url = new URL(mFileInfo.getUrl());
                conn = (HttpURLConnection) url.openConnection();
                conn.setConnectTimeout(3*1000);
                conn.setRequestMethod("GET");
//                conn.connect();
                // 获得文件长度
                int lenght = -1;
                if (conn.getResponseCode() == HttpStatus.SC_OK){
                    lenght = conn.getContentLength();
                }
                if (lenght <= 0){
                    return;
                }
                //
                File downPath = new File(DOWNLOAD_PATH);
                if (!downPath.exists()){
                    downPath.mkdir();
                }
                // 创建本地文件
                File file = new File(downPath,mFileInfo.getFileName());
                raf = new RandomAccessFile(file,"rwd");
                // 设置文件长度
                raf.setLength(lenght);
                mFileInfo.setLenght(lenght);

                mHandler.obtainMessage(MSG_INT,mFileInfo).sendToTarget();
            } catch (Exception e){
                e.printStackTrace();
            } finally {
                try {
                    conn.disconnect();
                    raf.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {

            switch(msg.what){
                case MSG_INT :
                    FileInfo fileInfo = (FileInfo) msg.obj;
                    Logs.i("123",fileInfo.toString());
                    mTask.downLoad();
                    break;
            }
        }
    };
}
