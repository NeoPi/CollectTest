package cn.com.byg.collecttest.dragview;

import java.nio.channels.Channels;
import java.util.ArrayList;
import java.util.List;

public class Channel {

	private int id ;
	private int countId ;
	private String name;
	private boolean isAddNew = false;
	private static String[] channels = {"首页","新车","行业","行情","自媒体","直播","导购","评测",
			"图赏","视频","用车","游记","文化","赛事","技术"};
	
	@Override
	public String toString() {
		return "Channel [id=" + id + ", countId=" + countId + ", name=" + name
				+ ", isAddNew=" + isAddNew + "]";
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public int getCountId() {
		return countId;
	}


	public void setCountId(int countId) {
		this.countId = countId;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public boolean isAddNew() {
		return isAddNew;
	}


	public void setAddNew(boolean isAddNew) {
		this.isAddNew = isAddNew;
	}
	
	/**
	 * 初始化数据
	 * @return
	 */
	public static List<Channel> initListChannel(){
		List<Channel> list = new ArrayList<Channel>();
		for (int i = 0; i < channels.length; i++) {
			Channel channel = new Channel();
			channel.setId(i);
			channel.setName(channels[i]);
			list.add(channel);
			channel = null;
		}
		return list;
	}
	
}
