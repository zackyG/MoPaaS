package com.mopaas_mobile.adapter;

import java.util.List;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.mopaas_mobile.activity.R;
import com.mopaas_mobile.domain.AppLication;
import com.mopaas_mobile.fragment.AppListFragment;

//
// ApplistAdapter.java by Administrator at 下午5:00:27
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
public class ApplistAdapter extends BaseAdapter {

	private LayoutInflater inflate;
	private List<AppLication> applist;
	private AppListFragment fragment;
	
	public ApplistAdapter(AppListFragment fragment,List<AppLication> applist)
	{
		this.fragment = fragment;
		this.applist = applist;
		inflate = LayoutInflater.from(fragment.getActivity());
	};
	@Override
	public int getCount() {
		if(applist==null)
			return 0;
		else
			return applist.size();
	}

	@Override
	public Object getItem(int position) {
		return applist.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@SuppressLint("InflateParams")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		final AppLication currentApp = applist.get(position);
		final int itemid = position; 
		ViewHolder holder = null;
		if(convertView == null || convertView.getTag() == null)
		{
			holder = new ViewHolder();
			convertView = inflate.inflate(R.layout.item_applist,null);
			holder.IconImg = (ImageView)convertView.findViewById(R.id.appicon);
			holder.OnOffBtn = (ImageButton)convertView.findViewById(R.id.appbutton);
			holder.AppName = (TextView)convertView.findViewById(R.id.appname);
			holder.AppInfo = (TextView)convertView.findViewById(R.id.appinfo);	
			convertView.setTag(holder);
		}else
		{
			holder = (ViewHolder)convertView.getTag();
			holder.IconImg.setImageDrawable(null);
			holder.AppName.setText("");
			holder.AppInfo.setText("");
		}
		int appmemory = Integer.valueOf(currentApp.memory)*Integer.valueOf(currentApp.instanceNum);

		int drawableResourceId = fragment.getActivity().getResources()
											.getIdentifier(currentApp.buildpack.toLowerCase(), "drawable", fragment.getActivity().getPackageName());
//		holder.IconImg.setImageResource(switchIcon(currentApp.buildpack));
		holder.IconImg.setImageResource(drawableResourceId);
		holder.AppName.setText(currentApp.appName);
		holder.AppInfo.setText(appmemory+"M 内存/"+currentApp.instanceNum+"实例");
		if(currentApp.state.contains("STOP"))
			holder.OnOffBtn.setBackgroundResource(R.drawable.icon_off);
		else 
			holder.OnOffBtn.setBackgroundResource(R.drawable.icon_on);
		
		holder.OnOffBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(currentApp.state.contains("STOP"))
				{
					fragment.turnOnApp(currentApp.appID, itemid,true);
				}else
				{
					fragment.turnOnApp(currentApp.appID, itemid,false);
				}
			}
		});
		
		return convertView;
	}
	private class ViewHolder
	{
		ImageView IconImg;
		ImageButton OnOffBtn;
		TextView AppName;
		TextView AppInfo;
	}
//	private int switchIcon(String buildpack)
//	{
//		if("go".equals(buildpack))return R.drawable.go;
//		if("java".equals(buildpack))return R.drawable.java;
//		if("jboss".equals(buildpack))return R.drawable.jboss;
//		if("jenkins".equals(buildpack))return R.drawable.jenkins;
//		if("nodejs".equals(buildpack))return R.drawable.nodejs;
//		if("php".equals(buildpack))return R.drawable.php;
//		if("python".equals(buildpack))return R.drawable.python;
//		if("ruby".equals(buildpack))return R.drawable.ruby;
//		return R.drawable.tomcat;
//	}
}
