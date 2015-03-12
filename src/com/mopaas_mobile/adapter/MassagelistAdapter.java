package com.mopaas_mobile.adapter;

import java.util.List;

import com.mopaas_mobile.activity.AppDetailActivity;
import com.mopaas_mobile.activity.MessageListActivity;
import com.mopaas_mobile.activity.R;
import com.mopaas_mobile.domain.Message;

import android.content.Intent;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

//
// MassagelistAdapter.java by Administrator at 下午1:21:16
//
// Copyright (c) 2012 anchora,inc
// 
// Permission is hereby granted, free of charge, to any person obtaining a copy
// of this software and associated documentation files (the "Software"), to deal
// in the Software without restriction, including without limitation the rights
// to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
// copies of the Software, and to permit persons to whom the Software is
// furnished to do so, subject to the following conditions:
// 
// The above copyright notice and this permission notice shall be included in
// all copies or substantial portions of the Software.
// 
// THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
// IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
// FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
// AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
// LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
// OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
// THE SOFTWARE.
public class MassagelistAdapter extends BaseAdapter {

	private LayoutInflater inflate;
	private List<Message> msglist;
	private MessageListActivity activity;
	
	public MassagelistAdapter(MessageListActivity activity,List<Message> msglist)
	{
		this.activity = activity;
		this.msglist = msglist;
		inflate = LayoutInflater.from(activity.getApplicationContext());
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		if(msglist==null)return 0;
			return msglist.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return msglist.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		final Message msg = msglist.get(position);
		ViewHolder holder = null;
		if(convertView == null || convertView.getTag() == null)
		{
			holder = new ViewHolder();
			convertView = inflate.inflate(R.layout.item_msglist,null);
	//		holder.arrowImg = (ImageView)convertView.findViewById(R.id.arrow);
			holder.msgtime = (TextView)convertView.findViewById(R.id.msgtime);
			holder.msgcontent = (TextView)convertView.findViewById(R.id.msgcontent);
			convertView.setTag(holder);
		}else
		{
			holder = (ViewHolder)convertView.getTag();
//			holder.arrowImg.setVisibility(View.INVISIBLE);
			holder.msgtime.setText("");
			holder.msgcontent.setText("");
		}
		if(msg.isFlag())
			holder.msgcontent.setTextColor(activity.getResources().getColor(R.color.darkgray));
		else
		{
			holder.msgcontent.setTextColor(activity.getResources().getColor(R.color.black));
			holder.msgcontent.setTypeface(Typeface.DEFAULT,Typeface.BOLD);
		}
//		if(msg.getAppID()==null)holder.arrowImg.setVisibility(View.INVISIBLE);
//		else holder.arrowImg.setVisibility(View.VISIBLE);
		holder.msgtime.setText(msg.getCreatetime().substring(0, msg.getCreatetime().indexOf(" ")));
		holder.msgcontent.setText(msg.getContent());
		
		convertView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				ViewHolder vholder = (ViewHolder)v.getTag();
				msg.setFlag(true);
				vholder.msgcontent.setTextColor(activity.getResources().getColor(R.color.darkgray));
				activity.changeMsgState(msg.getId());
				if(msg.getAppID()!=null)
				{
					Intent intent = new Intent();
					intent.setClass(activity, AppDetailActivity.class);
					intent.putExtra("appid", msg.getAppID());
					activity.startActivity(intent);
				}
			}
		});
		
		
		return convertView;
	}

	private class ViewHolder
	{
//		ImageView arrowImg;
		TextView msgtime;
		TextView msgcontent;
	}
}
