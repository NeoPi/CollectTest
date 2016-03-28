package cn.com.byg.collecttest.db;

import cn.com.byg.collecttest.R;
import cn.com.byg.collecttest.R.id;
import android.R.integer;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class InsertDataActivity extends Activity implements OnClickListener {

    private Button okBt = null;
    private Button cancelBt = null;
    private DatabaseManager dbm = null;
    private EditText id_Et = null;
    private EditText name_Et = null;
    private EditText sex_Et = null;
    private EditText Eng_Et = null;
    private EditText Math_Et = null;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.insert_data_layout);
        
        initView();
    }

    /**
     * 初始化视图,并为组件添加监听
     */
    private void initView() {
        okBt      = (Button) findViewById(R.id.ok_btn);
        cancelBt  = (Button) findViewById(R.id.cancel_btn);
        id_Et     = (EditText) findViewById(R.id.stu_id);
        name_Et   = (EditText) findViewById(R.id.stu_name);
        sex_Et    = (EditText) findViewById(R.id.stu_sex);
        Eng_Et    = (EditText) findViewById(R.id.stu_english);
        Math_Et   = (EditText) findViewById(R.id.stu_math);
        
        dbm = DatabaseManager.getInstance(InsertDataActivity.this);
        
        okBt.setOnClickListener(this);
        cancelBt.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
        case R.id.ok_btn:
            commit();
            break;
        case R.id.cancel_btn:
            
            break;
        default:
            break;
        }
    }

    /**
     * 确认数据提交执行插入数据操作
     */
    private void commit() {
        if (dbm == null)
            return;
        
        Student stu = new Student();
        if (id_Et != null) {
            if (!"".equals(id_Et.getText().toString())) {
                stu.setId(Integer.valueOf(id_Et.getText().toString()));
            } else {
              
              return;
            }
        }
        if (name_Et != null) {
            if (!"".equals(name_Et.getText().toString())) {
                stu.setName(name_Et.getText().toString());
            } else {
              
              return;
            }
        }
        if (sex_Et != null) {
            if (!"".equals(sex_Et.getText().toString())) {
                stu.setSex(Integer.valueOf(sex_Et.getText().toString()));
            } else {
              
              return;
            }
        }
        if (Eng_Et != null) {
            if (!"".equals(Eng_Et.getText().toString())) {
                stu.setEnglish(Float.valueOf(Eng_Et.getText().toString()));
            } else {
              
              return;
            }
        }
        if (Math_Et != null) {
            if (!"".equals(Math_Et.getText().toString())) {
                stu.setMath(Float.valueOf(Math_Et.getText().toString()));
            } else {
              
              return;
            }
        }
        // 检查填写的数据是否合法
//        checkData(stu);
        dbm.insertData(stu);
        
    }

    /**
     * 检查输入的数据是否正确合法
     * @param stu
     */
    private void checkData(Student stu) {
        
    }
    
}
