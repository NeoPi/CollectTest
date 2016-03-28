package cn.com.byg.collecttest.download.db;

import java.util.List;

import cn.com.byg.collecttest.download.bean.ThreadInfo;

/**
 * @author NeoPi.
 * @date 2015/8/24
 * 数据的访问接口
 */
public interface  ThreadDAO {

    /**
     * 插入线程信息
     * @param threadInfo
     */
    public void insertThread(ThreadInfo threadInfo);

    /**
     * 在删除线程信息的时候，多线程下载文件时，一个文件可能有多个线程，<br>所以这里使用url和线程id仪器来判断</br>
     * @param url
     * @param thread_id
     */
    public void deleteThread(String url,int thread_id);

    /**
     * 根据线程url直接删除整个线程的数据信息
      * @param url
     */
    public void deleteThread(String url);

    /**
     * 更新线程下载进度
     * @param url
     * @param thread_id
     * @param finished
     */
    public void updateThread(String url,int thread_id,int finished);

    /**
     * 查询线程信息
     * @param url
     * @return
     */
    public List<ThreadInfo> getThreads(String url);

    /**
     * 查询线程是否存在
     * @param url
     * @param thread_id
     * @return <br> <b>true </b> 表示已经存在该线程</br>
     *         <br><b>false</b> 表示还不存在该线程</br>
     */
    public boolean exists(String url,int thread_id);
}

