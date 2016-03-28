package cn.com.byg.collecttest.download.service;

import android.content.Context;
import android.content.Intent;

import org.apache.http.HttpStatus;

import java.io.File;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import cn.com.byg.collecttest.download.bean.FileInfo;
import cn.com.byg.collecttest.download.bean.ThreadInfo;
import cn.com.byg.collecttest.download.db.ThreadDAOImpl;

/**
 * @author NeoPi.
 * @date 2015/8/24
 * 下载任务类
 */
public class DownLoadTask {

    private Context context = null;
    private FileInfo mFileInfo = null;
    private ThreadDAOImpl mDao = null;
    int mFinished = 0;
    public boolean isPause = false;

    public DownLoadTask(Context context, FileInfo mFileInfo) {
        this.context = context;
        this.mFileInfo = mFileInfo;
        mDao  = new ThreadDAOImpl(context);
    }

    /**
     * 开始下载线程任务
     */
    public void downLoad(){
        // 读取数据库的线程信息
        List<ThreadInfo> threads = mDao.getThreads(mFileInfo.getUrl());
        ThreadInfo threadInfo = null;
        if (threads.size() == 0){
            threadInfo = new ThreadInfo(0,mFileInfo.getUrl(),0,mFileInfo.getLenght(),0);
        } else {
            threadInfo = threads.get(0);
        }
        // 创建子线程进行下载
        new DownLoadThread(threadInfo).start();
    }

    class DownLoadThread extends Thread{

        private ThreadInfo mThreadInfo = null;

        public DownLoadThread(ThreadInfo mThreadInfo) {
            this.mThreadInfo = mThreadInfo;
        }

        @Override
        public void run() {
            // 向数据库中插入数据
            if (!mDao.exists(mThreadInfo.getUrl(),mThreadInfo.getId())){
                mDao.insertThread(mThreadInfo);
            }

            HttpURLConnection conn = null;
            InputStream input = null;
            RandomAccessFile raf = null;
            Intent intent = new Intent(FileDownLoadService.ACTION_UPDATE);

            try{
                URL url = new URL(mThreadInfo.getUrl());
                conn = (HttpURLConnection) url.openConnection();
                conn.setConnectTimeout(5*1000);
                conn.setRequestMethod("GET");
                // 设置下载位置
                int start = mThreadInfo.getStart()+mThreadInfo.getFinished();
                conn.setRequestProperty("Range","bytes="+start+"-"+mThreadInfo.getEnd());

                // 设置文件写入位置
                File file = new File(FileDownLoadService.DOWNLOAD_PATH,mFileInfo.getFileName());
                raf = new RandomAccessFile(file,"rwd");
                raf.seek(start);

                mFinished += mThreadInfo.getFinished();
                // 开始下载
                if (conn.getResponseCode() == HttpStatus.SC_PARTIAL_CONTENT){
                    // 读取数据
                    input = conn.getInputStream();
                    byte[] buffer = new byte[1024 * 4];
                    int len = -1;
                    long time = System.currentTimeMillis();
                    while((len = input.read(buffer)) != -1){
                        // 写入文件
                        raf.write(buffer,0,len);
                        // 把下载进度发送广播给activity
                        mFinished += len;
                        if (System.currentTimeMillis() - time > 500){
                            time= System.currentTimeMillis();
                            intent.putExtra("finished",mFinished * 100 / mFileInfo.getLenght());
                            context.sendBroadcast(intent);
                        }
                        // 在下载暂停时保存下载进度
                        if (isPause){
                            mDao.updateThread(mThreadInfo.getUrl(),mThreadInfo.getId(),mFinished);
                            return ;
                        }
                    }
                    // 删除下载完成的线程信息
                    mDao.deleteThread(mThreadInfo.getUrl(),mThreadInfo.getId());
                }
            } catch (Exception e){
                e.printStackTrace();
            } finally {
                try {
                    conn.disconnect();
                    input.close();
                    raf.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }
    }
}
