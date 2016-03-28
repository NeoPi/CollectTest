package cn.com.byg.collecttest.download.bean;

import java.io.Serializable;

/**
 * @author NeoPi.
 * @date 2015/8/24
 * 此bean用于保存下载文件对应线程的信息
 */
public class ThreadInfo implements Serializable{

    private int id;         // 线程id
    private String url;     // 此url与下载文件的url保持一致
    private int start ;     // 下载开始的位置
    private int end;        // 下载结束的位置
    private  int finished;  // 已下载的文件大小

    public ThreadInfo() {

    }

    public ThreadInfo(int id, String url, int start, int end, int finished) {
        this.id = id;
        this.url = url;
        this.start = start;
        this.end = end;
        this.finished = finished;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getEnd() {
        return end;
    }

    public void setEnd(int end) {
        this.end = end;
    }

    public int getFinished() {
        return finished;
    }

    public void setFinished(int finished) {
        this.finished = finished;
    }

    @Override
    public String toString() {
        return "ThreadInfo{" +
                "id=" + id +
                ", url='" + url + '\'' +
                ", start=" + start +
                ", end=" + end +
                ", finished=" + finished +
                '}';
    }
}
