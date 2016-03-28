package cn.com.byg.collecttest.dragview;

import java.util.List;

import cn.com.byg.collecttest.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class DragGridViewAdapter extends BaseAdapter implements DraggableAdapter {

	
	private List<Channel> mlist = null;
	private Context mCtx;
	public DragGridViewAdapter(Context ctx , List<Channel> list) {
		this.mCtx = ctx;
		this.mlist = list;
	}
	
	
	@Override
	public int getCount() {
		return mlist.size() > 0 ? mlist.size() : 0;
	}

	@Override
	public Channel getItem(int position) {
		return mlist.get(position) != null ? mlist.get(position) : null;
	}

	@Override
	public long getItemId(int position) {
		return position > 0 ? position : 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Holder holder = null;
		if (convertView == null) {
			holder = new Holder();
			convertView = LayoutInflater.from(mCtx).inflate(R.layout.information_channel_grid_item, null);
			holder.mText = (TextView) convertView.findViewById(R.id.textview_channel);
			holder.mImage = (ImageView) convertView.findViewById(R.id.imageview_point);
			convertView.setTag(holder);
		} else {
			holder = (Holder) convertView.getTag();
		}
		Channel data = mlist.get(position);
		if (data != null) {
			holder.mText.setText(data.getName());
			holder.mImage.setVisibility(View.INVISIBLE);
		}
		return convertView;
	}

	
	class Holder {
		TextView mText;
		ImageView mImage;
	}
	
	
	@Override
	public void reorderElements(int position, int newPosition) {
		final List<Channel> objects = mlist;

        Channel previous = objects.get(position);
        int iterator = newPosition < position ? 1 : -1;
        final int afterPosition = position + iterator;
        for (int cellPosition = newPosition; cellPosition != afterPosition; cellPosition += iterator) {
            Channel tmp = objects.get(cellPosition);
            objects.set(cellPosition, previous);
            previous = tmp;
        }
        notifyDataSetChanged();
	}

	@Override
	public void swapElements(int position, int newPosition) {
		List<Channel> channels = mlist;
		Channel temp = channels.get(position);
		channels.set(position,channels.get(newPosition));
		channels.set(newPosition, temp);
		notifyDataSetChanged();
	}
}
