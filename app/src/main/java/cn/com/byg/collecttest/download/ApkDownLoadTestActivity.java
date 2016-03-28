package cn.com.byg.collecttest.download;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DecimalFormat;

import cn.com.byg.collecttest.BaseAvtivity;
import cn.com.byg.collecttest.R;
import cn.com.byg.collecttest.utils.AsyncHttpClient;

/**
 * Created by NeoPi on 2015/8/18.
 */
public class ApkDownLoadTestActivity extends BaseAvtivity{

    private static String TAG = ApkDownLoadTestActivity.class.getSimpleName();

    private String url = "http://ftp-apk.pcauto.com.cn/pub/autoclub_4.3.0.apk";
    private String url2 = "http://www1.pcauto.com.cn/mobile/PCauto.apk";
    private String urlP = "http://attach.bbs.miui.com/forum/201409/05/164822jzs3tc77rjpd7t25.jpg";

    private View  mView = null;
    private Button downBtn = null;  // 下载APK Button
    private Button downPic = null;  // 下载图片 Button
    private ImageView imv = null;

    private ProgressBar bar = null;
    private TextView proTv = null;
    private TextView proSize = null;
    private Dialog dialog = null;
    private String downDir = "";
    int progress = 0;

    private File apkFile;
    private boolean isContinue = true;
    private String size = "";

    int lenght = 0;
    int count = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.download_layout);

        downDir = Environment.getExternalStorageDirectory()+"/DownLaodTest/";
        Log.i("123",downDir);
        initView();

    }

    /**
     * 初始化布局
     */
    private void initView() {
        imv = (ImageView) findViewById(R.id.img);
        downBtn = (Button) findViewById(R.id.down_btn1);
        downBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                showDownlaodDialog();
//                downLoadApk();
                Log.d("123",isContinue+"");
                new MyAsync().execute(url2);
            }
        });
        size = getResources().getString(R.string.progress_size);

        downPic = (Button) findViewById(R.id.down_btn2);
        downPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AsyncHttpClient.downLoadPicture(urlP, callback);
            }
        });
    }

    HandlerCallback callback = new HandlerCallback() {
        @Override
        public void onResult(Bitmap bmp) {
            if (bmp != null)
                Log.i("123",bmp.toString());
            Bundle mb = new Bundle();
            mb.putParcelable("TAG", bmp);
            Message msg = new Message();
            msg.setData(mb);
            msg.what = 0x001;
            mHandler.sendMessage(msg);
//            imv.setImageBitmap(bmp);
        }
    };

    /**
     * 下载方法
     */
    private void downLoadApk() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                InputStream is = null;
                OutputStream os = null;
                try {
                    URL mUrl = new URL(url);
                    HttpURLConnection conn = (HttpURLConnection) mUrl.openConnection();
                    conn.connect();
                    lenght = conn.getContentLength();
                    is = conn.getInputStream();
                    byte[] bs = new byte[1024];

                    File file = new File(downDir);
                    if (!file.exists()){
                        file.mkdir();
                    }
                    File saveFile = new File(downDir+url.substring(url.lastIndexOf('/')+1));
                    os = new FileOutputStream(saveFile);
                    int len  = -1;

                    while(isContinue){
                        if ((len = is.read(bs)) != -1){
                            os.write(bs, 0, len);
                            count += len;
                            progress = (int) (((float) count / lenght) * 100);
                            mHandler.sendEmptyMessage(0X002);
                        } else
                            break;

                    }
                    is.close();
                    os.close();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    /**
     * 显示下载进度
     */
    private void showDownlaodDialog(){
        mView = LayoutInflater.from(this).inflate(R.layout.progressbar_dialog_layout,null);
        dialog = new Dialog(ApkDownLoadTestActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(mView);
        dialog.setCanceledOnTouchOutside(false);
        bar = (ProgressBar) mView.findViewById(R.id.dialog_bar);
        bar.setMax(100);
        proTv = (TextView) mView.findViewById(R.id.dialog_progress);
        proSize = (TextView) mView.findViewById(R.id.dialog_size);
        mView.findViewById(R.id.dialog_stop).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        mView.findViewById(R.id.dialog_cancle).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isContinue = false;
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            Log.i("123","msg.arg1:"+msg.arg1);
            switch (msg.what){
                case 0x001:
                    imv.setImageBitmap((Bitmap) msg.getData().getParcelable("TAG"));
                    break;
                case 0x002:
                    bar.setProgress(progress);
                    proTv.setText(getResources().getString(R.string.progress_bar).replace("?",progress+""));
                    if (progress >= 100)
                        dialog.dismiss();
                    proSize.setText(size.replace("x",byte2M(count)+"").replace("y",byte2M(lenght)+""));
                    break;
            }

        }
    };

    public String byte2M(long leng){
        DecimalFormat df = new DecimalFormat("#.##");
        return df.format(((double) leng / 1024) / 1024);
    }

    public String byte2M(int leng){
        DecimalFormat df = new DecimalFormat("#.##");
        return  df.format(((double) leng / 1024) / 1024);
    }

    class MyAsync extends AsyncTask<String,Integer,Long>{
        @Override
        protected void onPreExecute() {
            showDownlaodDialog();
        }

        @Override
        protected Long doInBackground(String... params) {
            int i = params.length;
            String url = params[0];
            long totalSize = 0;
            int lenght = 0;
            try {
                URL newUrl = new URL(url);
                HttpURLConnection conn = (HttpURLConnection) newUrl.openConnection();
                conn.connect();
                lenght = conn.getContentLength();
                File file = new File(downDir);
                if (!file.exists()){
                    file.mkdir();
                }
                apkFile = new File(downDir+url.substring(url.lastIndexOf('/')+1));
                InputStream is = conn.getInputStream();
                OutputStream os = new FileOutputStream(apkFile);
                byte[] bs = new byte[1024];
                int len = 0;

                while(isContinue){
                    if ((len = is.read(bs)) != -1){
                        os.write(bs, 0, len);
                        totalSize += len;
                        progress = (int) (((float) totalSize / lenght) * 100);
                        publishProgress(progress);
                    }
                    else
                        break;
                }
//                is.close();
//                os.close();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Log.d("123",totalSize+"");
            return totalSize;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            bar.setProgress(values[0]);
            proTv.setText(getResources().getString(R.string.progress_bar).replace("?",values[0]+""));
            proSize.setText(size.replace("x",byte2M(count)).replace("y",byte2M(lenght)));
            Log.i("123","values:"+values[0]);
        }

        @Override
        protected void onPostExecute(Long aLong) {
            Log.d("123",aLong.toString());
            dialog.dismiss();
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_VIEW);
            intent.setDataAndType(Uri.fromFile(apkFile), "application/vnd.android.package-archive");
            startActivity(intent);
        }
    }
}
