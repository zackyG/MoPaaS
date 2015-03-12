package com.mopaas_mobile.activity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.json.JSONException;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

import com.mopaas_mobile.adapter.MassagelistAdapter;
import com.mopaas_mobile.api.MPSbackendApi;
import com.mopaas_mobile.domain.Message;
import com.mopaas_mobile.utils.XListView;
import com.mopaas_mobile.utils.XListView.IXListViewListener;

//
// MessageListActivity.java by Administrator at 上午11:14:16
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
public class MessageListActivity extends Activity implements IXListViewListener,OnClickListener{

	SharedPreferences cashedata; 
	XListView msglistview;
	List<Message> msglist= new ArrayList<Message>();
	MassagelistAdapter adapter;
	String token;
	int currentPage = 0,messageCount = 0;
	Button alltagBtn;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_msglist);
		initview();
		token = cashedata.getString("token", null);
		
		MessageTask mTask = new MessageTask();
		mTask.execute(currentPage);
	};
	public void initview()
	{		
		msglistview = (XListView)findViewById(R.id.msglist);
		alltagBtn = (Button)findViewById(R.id.button);
		msglistview.setPullRefreshEnable(false);
		msglistview.setPullLoadEnable(true);
		msglistview.setXListViewListener(this);
		cashedata = getSharedPreferences("MoPaaS_mobile", 0);
		alltagBtn.setOnClickListener(this);
	}
	public void changeMsgState(String rid)
	{
		android.os.Message msg = new android.os.Message();
		msg.what = 1;
		MainTabActivity.readmsgHandler.sendMessage(msg);
		MsgReadTask mrTask = new MsgReadTask();
		mrTask.execute(rid);
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if(v.getId()==R.id.button&&msglist!=null)
		{
			for(int i=0;i<msglist.size();i++)
			{
				msglist.get(i).setFlag(true);
					
			}
			MsgReadTask mrTask = new MsgReadTask();
			mrTask.execute("all");
			adapter.notifyDataSetChanged();
			android.os.Message msg = new android.os.Message();
			msg.what = 10;
			MainTabActivity.readmsgHandler.sendMessage(msg);
			alltagBtn.setClickable(false);
		}
	}
	
	private void onLoad() {
		msglistview.stopRefresh();
		msglistview.stopLoadMore();
	}
	class MessageTask extends AsyncTask<Integer,Integer,Map<String,Object>>
	{
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
		}
		@Override
		protected Map<String, Object> doInBackground(Integer... params) {
			// TODO Auto-generated method stub
			Map<String,Object> resultmap = null;
			try {
				resultmap = MPSbackendApi.getMessages(token, String.valueOf(params[0]), "20");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				resultmap = null;
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				resultmap = null;
			}
			return resultmap;
		}
		@SuppressWarnings("unchecked")
		@Override
		protected void onPostExecute(Map<String, Object> resultmap) {
			// TODO Auto-generated method stub
			super.onPostExecute(resultmap);
			if(resultmap != null&&resultmap.get("returnCode").equals("000"))
			{
				if(currentPage == 0)
				{
					messageCount = Integer.valueOf((String)resultmap.get("count"));
				}
				List<Message> newlist = (List<Message>)resultmap.get("list");
				if(newlist!=null&&newlist.size()>0)
				{
					msglist.addAll(newlist);
					if(adapter==null)
					{
						adapter = new MassagelistAdapter(MessageListActivity.this, msglist);
						msglistview.setAdapter(adapter);
					}else
						adapter.notifyDataSetChanged();
				}else
				{
					Toast.makeText(getApplicationContext(), (String)resultmap.get("returnMessage"), Toast.LENGTH_SHORT).show();
					msglistview.stopLoadMore();
				}
				 int restCount = (currentPage+1)*20-messageCount;
				if(restCount > 20)
				{
					msglistview.stopLoadMore();
					msglistview.mFooterView.setVisibility(View.GONE);
				}
			}else
			{
				if(msglist==null||adapter==null||msglist.size()==0)
					alltagBtn.setVisibility(View.INVISIBLE);
			}
		}
	}
	class MsgReadTask extends AsyncTask<String,Integer,Map<String,String>>
	{

		@Override
		protected Map<String, String> doInBackground(String... params) {
			// TODO Auto-generated method stub
			
			try {
				MPSbackendApi.setMessageRead(token,params[0]);
				} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				}
			return null;
		}
	}
	@Override
	public void onRefresh() {
		// TODO Auto-generated method stub
		return;
	}
	@Override
	public void onLoadMore() {
		// TODO Auto-generated method stub
		int restCount = (currentPage+1)*20-messageCount;
		if(restCount <= 20)
		{
			currentPage++;
			onLoad();
			MessageTask mTask = new MessageTask();
			mTask.execute(currentPage);
		}
		
	}
}
