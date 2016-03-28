package cn.com.byg.collecttest.db;

import java.util.List;

import cn.com.byg.collecttest.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class DatabaseActivity extends Activity implements OnClickListener{

    private Button mInsert = null;
    private Button mDelete = null;
    private Button mUpdate = null;
    private Button mSelect = null;
    
    private DatabaseManager dbm = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("123", "second onCreate");
        setContentView(R.layout.database_layout);
        
        initView();
    }

    /**
     * 初始化视图
     */
    private void initView() {
        mInsert = (Button) findViewById(R.id.insert_bt);
        mDelete = (Button) findViewById(R.id.delete_bt);
        mUpdate = (Button) findViewById(R.id.update_bt);
        mSelect = (Button) findViewById(R.id.select_bt);
        
        mInsert.setOnClickListener(this);
        mDelete.setOnClickListener(this);
        mUpdate.setOnClickListener(this);
        mSelect.setOnClickListener(this);
        
        dbm = DatabaseManager.getInstance(DatabaseActivity.this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
        case R.id.insert_bt:
            startActivity(InsertDataActivity.class);
            break;
        case R.id.delete_bt:
            
            break;
        case R.id.select_bt:
            selectData();
            break;
        case R.id.update_bt:
            
            break;
        default:
            break;
        }
    }
    
    /**
     * 页面跳转
     * @param cls 类名
     */
    public void startActivity(Class<?> cls){
        Intent intent = new Intent();
        intent.setClass(DatabaseActivity.this, cls);
        startActivity(intent);
    }
    
    /**
     * 查询数据，并展示出来
     */
    public void selectData(){
        List<Student> list = dbm.selectDataAll();
        if (list != null) {
            for (int i = 0; i < list.size(); i++) {
                Log.i("123", list.get(i).toString()+"\n");
//                Toast.makeText(DatabaseActivity.this, list.get(i).toString(), Toast.LENGTH_SHORT).show();
            }
        }
    }
    
    @Override
	protected void onRestart() {
		super.onRestart();
		Log.d("123", "second onRestart");
	}

	@Override
	protected void onStart() {
		super.onStart();
		Log.d("123", "second onStart");
	}


	@Override
	protected void onResume() {
		super.onResume();
		Log.d("123", "second onResume");
	}

	@Override
	protected void onPause() {
		super.onPause();
		Log.d("123", "second onPause");
	}

	@Override
	protected void onStop() {
		super.onStop();
		Log.d("123", "second onStop");
	}

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (dbm != null) {
            dbm.close();
        }
        Log.d("123", "second onDestroy");
    }
}
