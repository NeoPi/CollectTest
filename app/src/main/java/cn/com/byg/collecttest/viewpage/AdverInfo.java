package cn.com.byg.collecttest.viewpage;

/**
 * Created by NeoPi on 2015/8/7.
 */
public class AdverInfo {

    String title; // 广告的title

    int resId; // 广告的图片资源id
    boolean isSecelted = false ; // 是否显示的当前

    public AdverInfo(int resId,String title) {
        this.title = title;
        this.resId = resId;
    }

    public int getResId() {
        return resId;
    }

    public void setResId(int resId) {
        this.resId = resId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "AdverInfo{" +"title='" + title + '\'' +", resId='" + resId + '\'' +'}';
    }

}
