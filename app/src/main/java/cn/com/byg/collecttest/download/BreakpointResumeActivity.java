package cn.com.byg.collecttest.download;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import cn.com.byg.collecttest.BaseAvtivity;
import cn.com.byg.collecttest.JavaBean.BooksBean;
import cn.com.byg.collecttest.R;
import cn.com.byg.collecttest.download.bean.FileInfo;
import cn.com.byg.collecttest.download.service.FileDownLoadService;
import cn.com.byg.collecttest.utils.Logs;
import cn.com.byg.collecttest.utils.XmlPullParserUtils;
import cn.com.byg.collecttest.widget.CircleProgressBar;

/**
 * @author NeoPi.
 * @date 2015/8/24
 * 断点续传测试类
 */
public class BreakpointResumeActivity extends BaseAvtivity implements View.OnClickListener {


    private static String TAG = BreakpointResumeActivity.class.getSimpleName();

    public String FILE_URL  = "http://www.imooc.com/mobile/mukewang.apk";

    private Button bt_start = null;
    private Button bt_stop = null;
    private ProgressBar progressBar = null;
    private CircleProgressBar circleProgressBar = null;
    private TextView tvFileName = null;

    private FileInfo downFile = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.breakpoint_layout);

        // 初始化视图
        initView();

        IntentFilter filter = new IntentFilter(FileDownLoadService.ACTION_UPDATE);
        this.registerReceiver(mReceiver,filter);
    }

    private void initView() {
        bt_start = (Button) findViewById(R.id.bt_start);
        bt_stop = (Button) findViewById(R.id.bt_stop);
        progressBar = (ProgressBar) findViewById(R.id.pbProgress);
        circleProgressBar = (CircleProgressBar) findViewById(R.id.roundProgressBar2);
        tvFileName = (TextView) findViewById(R.id.tvFileName);

        bt_start.setOnClickListener(this);
        bt_stop.setOnClickListener(this);

        progressBar.setMax(100);
        downFile = new FileInfo(0,FILE_URL,"123.jpg",0,0);
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt_start:
                Intent intent = new Intent(BreakpointResumeActivity.this, FileDownLoadService.class);
                intent.setAction(FileDownLoadService.ACTION_START);
                intent.putExtra(FileDownLoadService.DOWN_KEY, downFile);
                startService(intent);
                startParser(); // 此处用来测试xml解析
                break;
            case R.id.bt_stop:
                Intent intent2 = new Intent(BreakpointResumeActivity.this, FileDownLoadService.class);
                intent2.setAction(FileDownLoadService.ACTION_STOP);
                intent2.putExtra(FileDownLoadService.DOWN_KEY,downFile);
                startService(intent2);
                break;
        }
    }

    BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(FileDownLoadService.ACTION_UPDATE)){
                int progress =intent.getIntExtra("finished",0);
                Logs.i("123","progress:"+progress);
                circleProgressBar.setProgress(progress);
            }
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mReceiver != null)
            unregisterReceiver(mReceiver);
    }

    /**
     * 这里来测试android的Pull解析xml文件
     */
    private void startParser(){

        try {
            InputStream inputStream = getAssets().open("books.xml");
            List<BooksBean> books = XmlPullParserUtils.parser(inputStream);
            for (BooksBean book:books){
                System.out.println(book.toString()+"+++++++++++++++++++++++++++++++++" +
                        "++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
            }
            System.out.println(XmlPullParserUtils.serialize(books));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
