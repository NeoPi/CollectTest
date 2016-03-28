package cn.com.byg.collecttest.dragview;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;

import cn.com.byg.collecttest.BaseAvtivity;
import cn.com.byg.collecttest.R;
import cn.com.byg.collecttest.dragview.DragGridView.OnDragListener;

public class DragGridViewActivity extends BaseAvtivity{

	
	private static String TAG = DragGridViewActivity.class.getSimpleName();
	
	private DragGridView mDrag = null;
	private DragGridViewAdapter mAdapter = null;
	
	private List<Channel> mList = new ArrayList<Channel>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.d("123", "DragGridViewActivity onCreate");
		setContentView(R.layout.drag_view_layout);
		
		initData();
		initView();
		setListener();
	}
	
	/**
	 * 初始化视图
	 */
	private void initView() {
		mDrag = (DragGridView) findViewById(R.id.drag_grid_view);
		mAdapter = new DragGridViewAdapter(this,mList);
		mDrag.setGridNumColumns(3);
		mDrag.setAdapter(mAdapter);
		mDrag.setOnDragListener(onDragListener); // 设置拖拽监听
		mDrag.setOnItemClickListener(onItemClickListener);
		
	}
	
	OnDragListener onDragListener = new DragGridView.OnDragListener() {
		
		@Override
		public void onDragStart() {
			
		}
		
		@Override
		public void onDragComplete() {
//			mList.remove(2);
			mAdapter.notifyDataSetChanged();
		}
	};
	
	OnItemClickListener onItemClickListener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,long id) {
			
		}
		
	};
	
	/**
	 * 初始化内容
	 */
	private void initData() {
		mList = Channel.initListChannel();
	}

	/**
	 * 设置监听
	 */
	private void setListener() {
		
	}
	
	@Override
	protected void onRestart() {
		super.onRestart();
		Log.d("123", "DragGridViewActivity onRestart");
	}

	@Override
	protected void onStart() {
		super.onStart();
		Log.d("123", "DragGridViewActivity onStart");
	}


	@Override
	protected void onResume() {
		super.onResume();
		Log.d("123", "DragGridViewActivity onResume");
	}

	@Override
	protected void onPause() {
		super.onPause();
		Log.d("123", "DragGridViewActivity onPause");
	}

	@Override
	protected void onStop() {
		super.onStop();
		Log.d("123", "DragGridViewActivity onStop");
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		Log.d("123", "DragGridViewActivity onDestroy");
	}
}
