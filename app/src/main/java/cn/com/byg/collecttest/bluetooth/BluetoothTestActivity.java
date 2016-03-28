package cn.com.byg.collecttest.bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;

import cn.com.byg.collecttest.BaseAvtivity;
import cn.com.byg.collecttest.R;

/**
 * Created by NeoPi on 2015/8/14.
 */
public class BluetoothTestActivity extends BaseAvtivity{

    private static String TAG = BluetoothTestActivity.class.getSimpleName();

    private Button mBtn = null;

    private ListView mListView = null;
    private ArrayList<String> mList = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("123", "BluetoothTestActivity onCreate");
        setContentView(R.layout.bluetooth_layout);


        mBtn = (Button) findViewById(R.id.btn_search);
        mListView = (ListView) findViewById(R.id.list);
        mBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();
                if (adapter == null){
                    Log.i(TAG,"该设备不支持蓝牙功能");
                    return ;
                }
                if (!adapter.isEnabled()){
                    Log.i(TAG,"该设备的蓝牙功能不可用，请打开蓝牙");
                    Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                    startActivity(intent);
                }

                Set<BluetoothDevice> device = adapter.getBondedDevices();

                if (device.size() > 0){
                    for (Iterator iterator = device.iterator();iterator.hasNext();){
                        BluetoothDevice dev = (BluetoothDevice) iterator.next();
                        Log.i(TAG,dev.getAddress()+",name:"+dev.getName());
                    }
                }
            }
        });
        initData();
        MyAdapter adapter = new MyAdapter();
        mListView.setAdapter(adapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(BluetoothTestActivity.this,"我点击了item"+position,Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initData() {
        mList = new ArrayList<String>();
        for (int i = 0;i< 20;i++){
            String str = "测试"+i;
            mList.add(str);
        }
    }

    private class MyAdapter extends BaseAdapter implements  View.OnClickListener{

        @Override
        public int getCount() {
            return mList.size();
        }

        @Override
        public Object getItem(int position) {
            return mList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            ViewHolder mHolder = null;
            if (convertView == null){
                mHolder = new ViewHolder();
                convertView = LayoutInflater.from(BluetoothTestActivity.this).inflate(R.layout.list_item,null);
                mHolder.mText = (TextView) convertView.findViewById(R.id.item_txt);
                mHolder.mBtn1 = (Button) convertView.findViewById(R.id.item_btn1);
                mHolder.mBtn2 = (Button) convertView.findViewById(R.id.item_btn2);

                convertView.setTag(mHolder);
            } else {
                mHolder = (ViewHolder) convertView.getTag();
            }

            mHolder.mText.setText(mList.get(position));
            mHolder.mBtn1.setOnClickListener(this);
            mHolder.mBtn2.setOnClickListener(this);
            mHolder.mBtn1.setTag(position);
            mHolder.mBtn2.setTag(position);

            return convertView;
        }

        /**
         * Called when a view has been clicked.
         *
         * @param v The view that was clicked.
         */
        @Override
        public void onClick(View v) {

            int position = (Integer) v.getTag();
            Toast.makeText(BluetoothTestActivity.this,"我点击了"+position,Toast.LENGTH_SHORT).show();
        }
    }


    class ViewHolder{
        TextView mText;
        Button mBtn1;
        Button mBtn2;
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d("123", "BluetoothTestActivity onRestart");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("123", "BluetoothTestActivity onStart");
    }


    @Override
    protected void onResume() {
        super.onResume();
        Log.d("123", "BluetoothTestActivity onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("123", "BluetoothTestActivity onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("123", "BluetoothTestActivity onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("123", "BluetoothTestActivity onDestroy");
    }

}
