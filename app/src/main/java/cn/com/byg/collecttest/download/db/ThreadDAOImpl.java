package cn.com.byg.collecttest.download.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import cn.com.byg.collecttest.download.bean.ThreadInfo;

/**
 * @author NeoPi.
 * @date 2015/8/24
 * 实现访问数据的接口
 */
public class ThreadDAOImpl implements  ThreadDAO{


    private DBHelper dbHelper = null;

    public ThreadDAOImpl(Context context){
        dbHelper = DBHelper.getInstance(context);
    }

    /**
     * 插入线程信息
     *
     * @param threadInfo
     */
    @Override
    public synchronized void insertThread(ThreadInfo threadInfo) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.execSQL("insert into thread_info(thread_id,url,start,end,finished) values(?,?,?,?,?);",
                new Object[]{threadInfo.getId(),threadInfo.getUrl(),threadInfo.getStart(),threadInfo.getEnd(),threadInfo.getFinished()});
        db.close();
    }

    /**
     * 在删除线程信息的时候，多线程下载文件时，一个文件可能有多个线程，<br>所以这里使用url和线程id仪器来判断</br>
     *
     * @param url
     * @param thread_id
     */
    @Override
    public synchronized void deleteThread(String url, int thread_id) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String sql = "delete from thread_info where url = ? and thread_id = ?";
        db.execSQL(sql,new Object[]{url,thread_id});
        db.close();
    }

    /**
     * 根据线程url直接删除整个线程的数据信息
     *
     * @param url
     */
    @Override
    public synchronized void deleteThread(String url) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String sql = "delete from thread_info where url = "+url;
        db.execSQL(sql);
        db.close();
    }

    /**
     * 更新线程下载进度
     *
     * @param url
     * @param thread_id
     * @param finished
     */
    @Override
    public synchronized void updateThread(String url, int thread_id, int finished) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String sql = "update thread_info set finished = ? where url = ? and thread_id = ?";
        db.execSQL(sql,new Object[]{finished,url,thread_id});
        db.close();
    }

    /**
     * 查询线程信息
     *
     * @param url
     * @return
     */
    @Override
    public List<ThreadInfo> getThreads(String url) {
        List<ThreadInfo> mList = new ArrayList<ThreadInfo>();
        String sql = "select * from thread_info where url = ?";
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor mCursor = db.rawQuery(sql, new String[]{url});
        while(mCursor.moveToNext()){
            ThreadInfo info = new ThreadInfo();
            info.setId(mCursor.getInt(mCursor.getColumnIndex("thread_id")));
            info.setUrl(mCursor.getString(mCursor.getColumnIndex("url")));
            info.setStart(mCursor.getInt(mCursor.getColumnIndex("start")));
            info.setEnd(mCursor.getInt(mCursor.getColumnIndex("end")));
            info.setFinished(mCursor.getInt(mCursor.getColumnIndex("finished")));
            mList.add(info);
        }
        db.close();
        mCursor.close();
        return mList;
    }

    /**
     * 查询线程是否存在
     *
     * @param url
     * @param thread_id
     * @return <br> <b>true </b> 表示已经存在该线程</br>
     * <br><b>false</b> 表示还不存在该线程</br>
     */
    @Override
    public boolean exists(String url, int thread_id) {
        List<ThreadInfo> mList = new ArrayList<ThreadInfo>();
        String sql = "select * from thread_info where url = ? and thread_id = ?";
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor mCursor = db.rawQuery(sql, new String[]{url});

        boolean exists = mCursor.moveToNext();
        db.close();
        mCursor.close();
        return exists;
    }
}
