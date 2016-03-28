package cn.com.byg.collecttest.download.bean;

import java.io.Serializable;

/**
 * @author NeoPi.
 * @date 2015/8/24
 * 此bean用于保存文件信息
 */
public class FileInfo implements Serializable{
    private int id;             //文件id
    private String Url;         //网络文件的url
    private String fileName;    //文件名
    private int lenght;         //文件的长度大小（字节）
    private int isFinished;     //文件已经下载的长度

    public int getId() {
        return id;
    }

    public String getUrl() {
        return Url;
    }

    public int getIsFinished() {
        return isFinished;
    }

    public int getLenght() {
        return lenght;
    }

    public String getFileName() {
        return fileName;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setIsFinished(int isFinished) {
        this.isFinished = isFinished;
    }

    public void setLenght(int lenght) {
        this.lenght = lenght;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public void setUrl(String url) {
        Url = url;
    }

    @Override
    public String toString() {
        return "FileInfo{" +
                "id=" + id +
                ", Url='" + Url + '\'' +
                ", fileName='" + fileName + '\'' +
                ", lenght=" + lenght +
                ", isFinished=" + isFinished +
                '}';
    }

    public FileInfo(int id, String url, String fileName, int lenght, int isFinished) {
        this.id = id;
        Url = url;
        this.fileName = fileName;
        this.lenght = lenght;
        this.isFinished = isFinished;
    }

    public FileInfo() {

    }
}
