package cn.com.byg.collecttest.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;

import org.apache.http.HttpStatus;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import cn.com.byg.collecttest.download.HandlerCallback;

/**
 * @author NeoPi.
 * @date 2015/8/19
 */
public class AsyncHttpClient {

    private static String DOWNLAOD_PATH =
            Environment.getExternalStorageDirectory().getAbsolutePath()+"/DownLaodTest/";

    public static void downLoadPicture(String url,HandlerCallback callback){
        try {
            URL mUrl = new URL(url);
            new MyAsyncTask(callback).execute(mUrl);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    static class MyAsyncTask extends AsyncTask<URL,Integer,Bitmap>{


        HandlerCallback mcallback = null;
        File picFile = null;
        public MyAsyncTask(HandlerCallback callback){
            this.mcallback = callback;
        }

        protected Bitmap doInBackground(URL... params) {
            Bitmap bmp = null;
            HttpURLConnection conn = null;
            InputStream is = null;
            FileOutputStream os = null;
            try {
                conn = (HttpURLConnection) params[0].openConnection();
                conn.setConnectTimeout(5*1000);
                conn.setRequestMethod("GET");
                conn.connect();
                if (conn.getResponseCode() == HttpStatus.SC_OK) {
                    File downDir = new File(DOWNLAOD_PATH);
                    if (!downDir.exists()) {
                        downDir.mkdir();
                    }

                    picFile = new File(DOWNLAOD_PATH, "123.jpg");
                    is = conn.getInputStream();

                    os = new FileOutputStream(picFile);
                    int len = -1;
                    byte[] buffer = new byte[1024 * 4];
                    while ((len = is.read(buffer)) != -1) {
                        os.write(buffer, 0, len);
                    }

                    is.close();
                    os.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            return bmp;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            mcallback.onResult(getLoacalBitmap(picFile.getAbsolutePath()));
//            mcallback.onResult(bitmap);
        }
    }

    /**
     * 此方法会
     * @param path
     * @return
     */
    public static Bitmap getLoacalBitmap(String path) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        // path是图片所在位置
        BitmapFactory.decodeFile(path, options);
        //这里不加载图片，只是取图片大小
        int imageHeight = options.outHeight;
        int imageWidth = options.outWidth;
        options.inPreferredConfig = Bitmap.Config.RGB_565;
        //这里用2字节显示图片，一般是4字节（默认
        // 判断如果使用内存大于3M，你可以修改这个参数，一般10M应该OK
        int ratio = imageWidth * imageHeight * 2 / 30000;
        Log.i("123","ratio:"+ratio);
        options.inSampleSize = 1;//不缩放，保持原来的大小
        if (ratio >= 1) {
            options.inSampleSize = 2; //宽度和高度将缩小两倍，width/2 and height/2，所以图片会模糊，不过不会消耗很多内存
        }
        if (ratio >= 4) {
            options.inSampleSize = 5;
        }
//        options.inSampleSize = 1;
        options.inJustDecodeBounds = false;
        Bitmap bitmap = BitmapFactory.decodeFile(path, options); //真正的加载图片
        return bitmap;
    }
}

