package com.mopaas_mobile.adapter;

import java.util.ArrayList;

import com.mopaas_mobile.activity.R;
import com.mopaas_mobile.domain.SerVice;
import com.mopaas_mobile.fragment.ServiceListFragment;
import com.tencent.mm.sdk.platformtools.Log;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

//
// SvcLIstAdapter.java by Administrator at 下午1:42:34
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
public class SvcListAdapter extends BaseAdapter {

	private LayoutInflater inflate;
	private ArrayList<SerVice> svclist;
	private ServiceListFragment fragment;
	public SvcListAdapter(ServiceListFragment fragment,ArrayList<SerVice> svclist)
	{
		this.fragment = fragment;
		this.svclist = svclist; 
		inflate = LayoutInflater.from(fragment.getActivity());
	}
	@Override
	public int getCount() {
		if(svclist==null) return 0;
		else return svclist.size();
	}

	@Override
	public Object getItem(int position) {
		if(svclist==null) return null;
		else return svclist.get(position);
	}

	@Override
	public long getItemId(int position) {
		if(svclist==null) return 0;
		else return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		
		ViewHolder holder = null;
		final  ViewHolder target;
		if(convertView==null||convertView.getTag()==null)
		{
			holder = new ViewHolder();
			convertView = inflate.inflate(R.layout.item_svclistview, null);
			holder.labelimg = (ImageView)convertView.findViewById(R.id.labelimg);
			holder.deletebtn = (TextView)convertView.findViewById(R.id.deletebtn);
			holder.svcnameTxt = (TextView)convertView.findViewById(R.id.svcname);
			holder.createtimeTxt = (TextView)convertView.findViewById(R.id.createtime);
			holder.appbindTxt = (TextView)convertView.findViewById(R.id.appbind);
			convertView.setTag(holder);
		}else
		{
			holder = (ViewHolder)convertView.getTag();
			holder.svcnameTxt.setText("");
			holder.createtimeTxt.setText("");
			holder.appbindTxt.setText("");
		}
		int drawableResourceId = fragment.getActivity().getResources()
				.getIdentifier(svclist.get(position).label.toLowerCase(), "drawable", fragment.getActivity().getPackageName());
//		holder.labelimg.setImageResource(switchSvcIcon(svclist.get(position).label));
		holder.labelimg.setImageResource(drawableResourceId);
		holder.svcnameTxt.setText(svclist.get(position).name);
		holder.createtimeTxt.setText(svclist.get(position).createdTime.split(" ")[0]);
		holder.appbindTxt.setText(svclist.get(position).serviceBindingses);
		return convertView;
	}
	private int  switchSvcIcon(String label)
	{
		if("MySQL".equals(label))return R.drawable.mysql;
		if("PostgreSQL".equals(label))return R.drawable.postgresql;
		if("Redis".equals(label))return R.drawable.redis;
		if("Memcached".equals(label))return R.drawable.memcached;
		if("MongoDB".equals(label))return R.drawable.mongodb;
		if("Nfs".equals(label))return R.drawable.fastdfs;
		if("OneAPM".equals(label))return R.drawable.oneapm;
		if("Ping++".equals(label))return R.drawable.ping;
		if("weblogic".equals(label))return R.drawable.weblogic;
		return R.drawable.dbfen;
	}
	class ViewHolder
	{
		ImageView labelimg;
		TextView deletebtn;
		TextView svcnameTxt;
		TextView createtimeTxt;
		TextView appbindTxt;
	}
}
