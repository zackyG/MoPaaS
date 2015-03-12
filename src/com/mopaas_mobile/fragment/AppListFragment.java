package com.mopaas_mobile.fragment;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.json.JSONException;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.mopaas_mobile.activity.AppDetailActivity;
import com.mopaas_mobile.activity.R;
import com.mopaas_mobile.adapter.ApplistAdapter;
import com.mopaas_mobile.api.MPSbackendApi;
import com.mopaas_mobile.crouton.Configuration;
import com.mopaas_mobile.crouton.Crouton;
import com.mopaas_mobile.crouton.Style;
import com.mopaas_mobile.domain.AppLication;
import com.mopaas_mobile.pulltorefresh.ptr.PtrClassicFrameLayout;
import com.mopaas_mobile.pulltorefresh.ptr.PtrDefaultHandler;
import com.mopaas_mobile.pulltorefresh.ptr.PtrFrameLayout;
import com.mopaas_mobile.pulltorefresh.ptr.PtrHandler;

public class AppListFragment extends Fragment {
	
	private static final Style STYLE_CONFIRM = new Style.Builder().
			setBackgroundColorValue(Style.holoGreenLight).setTextSize(20).build();
	private static final Style STYLE_ALERT = new Style.Builder().
		    setBackgroundColorValue(Style.holoRedLight).setTextSize(20).build();
	SharedPreferences cashedata;
	private String token,totalMemory,spaceGuid,orgGuid,usedMemory;
	private List<AppLication> applist;
	private ApplistAdapter appAdapter;
	private TextView memoryTxt,applistcautionTxt;
	private ProgressBar memorybar;
	private ListView applistview;
	private ProgressDialog dialog = null;
	private PtrClassicFrameLayout mPtrFrame;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View contentview = inflater.inflate(R.layout.fragment_applist, container,false);
		return contentview;
	}
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		initView();
	}
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if(20==resultCode)  
		   {   
			   int itemid = data.getIntExtra("itemid", -1);
			   applist.get(itemid).state = data.getStringExtra("state");
			   applist.get(itemid).memory = data.getStringExtra("memory");
			   applist.get(itemid).instanceNum = String.valueOf(data.getIntExtra("instancenum", 0));
		       appAdapter.notifyDataSetChanged();
		       int usedMemoryCount = 0;
		       for(AppLication app:applist)
				{
					usedMemoryCount +=Integer.valueOf(app.memory)*Integer.valueOf(app.instanceNum);
				}
				usedMemory = String.valueOf(usedMemoryCount);
				memoryTxt.setText(usedMemory+"M/"+totalMemory+"M");
				memorybar.setProgress(((100*usedMemoryCount)/Integer.valueOf(totalMemory)));
		   }  
	}
	@Override
	public void onPause() {
		super.onPause();
		cashedata.edit().putString("usedMemory", usedMemory).commit();
	}
	private void initView()
	{
		applistview = (ListView)getActivity().findViewById(R.id.applistview);
		memoryTxt = (TextView)getActivity().findViewById(R.id.memoryinfo);
		memorybar = (ProgressBar)getActivity().findViewById(R.id.memorybar);
		mPtrFrame = (PtrClassicFrameLayout)getActivity().findViewById(R.id.rotate_header_applistview_frame);
		cashedata = getActivity().getSharedPreferences("MoPaaS_mobile", 0);
		token = cashedata.getString("token", "");
		applistcautionTxt = (TextView)getActivity().findViewById(R.id.applistcaution);
		totalMemory = cashedata.getString("memoryLimit", "0");
		orgGuid = cashedata.getString("orgGuid", "");
		spaceGuid = cashedata.getString("spaceGuid", "");
		AppListTask task = new AppListTask();
		task.execute(token,orgGuid,spaceGuid);
		
		mPtrFrame.setLastUpdateTimeRelateObject(this);
        mPtrFrame.setPtrHandler(new PtrHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
            	mPtrFrame.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                    	AppListTask task = new AppListTask();
                		task.execute(token,orgGuid,spaceGuid);
                    }
                }, 500);
            }

            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header);
            }
        });
        mPtrFrame.setResistance(1.7f);
        mPtrFrame.setRatioOfHeaderHeightToRefresh(1.2f);
        mPtrFrame.setDurationToClose(1200);
        mPtrFrame.setDurationToCloseHeader(1000);
        mPtrFrame.setPullToRefresh(true);
        mPtrFrame.setKeepHeaderWhenRefresh(true);
		applistview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {
				if(applist.size()>0)
				{
					Intent dtlIntent = new Intent();
					if(applist.size()>1)
					{
						int count = Integer.valueOf(totalMemory);
						count = count - Integer.valueOf(usedMemory) + Integer.valueOf(applist.get(position).memory);
						dtlIntent.putExtra("totalMemory", String.valueOf(count));
					}else
						dtlIntent.putExtra("totalMemory", totalMemory);
						String appName = applist.get(position).appName;
						dtlIntent.putExtra("itemid", position);
						dtlIntent.putExtra("appId", applist.get(position).appID);
						dtlIntent.putExtra("appImage", applist.get(position).buildpack);
						dtlIntent.putExtra("appMemory", applist.get(position).memory);
						dtlIntent.putExtra("appState", applist.get(position).state);
						dtlIntent.putExtra("instanceNum", applist.get(position).instanceNum);
						dtlIntent.putExtra("appName", appName);
						dtlIntent.putExtra("appuris", applist.get(position).uris);
						dtlIntent.setClass(getActivity(), AppDetailActivity.class);
						startActivityForResult(dtlIntent,100);
				}
			}
		});
		
	}
	 private void showCrouton(String croutonText, Style croutonStyle, Configuration configuration) {
		    final Crouton crouton;
		    crouton = Crouton.makeText(getActivity(), croutonText, croutonStyle);
		    crouton.setConfiguration( configuration).show();
		  }
	public void turnOnApp(String appid,int itemid,boolean isTurnOn)
	{
		AppOnOffTask onofftask = new AppOnOffTask();
		if(isTurnOn)
		{
			dialog = new ProgressDialog(getActivity());
			dialog.setMessage(getResources().getString(R.string.doing_turnon));
			dialog.setCancelable(true);
			onofftask.execute("start",token,applist.get(itemid).appName,spaceGuid,orgGuid,String.valueOf(itemid));
		}
		else
		{
			dialog = new ProgressDialog(getActivity());
			dialog.setMessage(getResources().getString(R.string.doing_turnoff));
			dialog.setCancelable(true);
			onofftask.execute("stop",token,applist.get(itemid).appName,spaceGuid,orgGuid,String.valueOf(itemid));
		}
	}
	
	class AppListTask extends AsyncTask<String,Integer,Map<String,Object>>
	{
		@Override
		protected Map<String,Object> doInBackground(String... params) {
			try {
				Map<String,Object> resultmap = MPSbackendApi.getAppList(params[0], params[1],params[2]);			
				return resultmap;
			} catch (IOException e) {
				e.printStackTrace();
			} catch (JSONException e) {
				e.printStackTrace();
			}
			return null;
		}
		@SuppressWarnings("unchecked")
		@Override
		protected void onPostExecute(Map<String, Object> resultmap) {
			super.onPostExecute(resultmap);
			mPtrFrame.refreshComplete();
			if(resultmap!=null)
			{
				if(resultmap.get("returnCode").equals("000"))
				{
					applist = (List<AppLication>)resultmap.get("list");
					if(applist.size()>0)
					{
						applistcautionTxt.setVisibility(View.GONE);
						int usedMemoryCount = 0;
							appAdapter = new ApplistAdapter(AppListFragment.this, applist);
							applistview.setAdapter(appAdapter);
				
						for(AppLication app:applist)
						{
							usedMemoryCount +=Integer.valueOf(app.memory)*Integer.valueOf(app.instanceNum);
						}
						usedMemory = String.valueOf(usedMemoryCount);
						memoryTxt.setText(usedMemory+"M/"+totalMemory+"M");
						memorybar.setProgress(((100*usedMemoryCount)/Integer.valueOf(totalMemory)));
					}else
						applistcautionTxt.setVisibility(View.VISIBLE);
				}else
				showCrouton(getString(R.string.delete_error),STYLE_ALERT, Configuration.DEFAULT);
			}else
				showCrouton(getString(R.string.ioexception),STYLE_ALERT, Configuration.DEFAULT);
		}
	}
	class AppOnOffTask extends AsyncTask<String,Integer,Map<String,String>>
	{
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			dialog.show();
		}
		@Override
		protected Map<String,String> doInBackground(String... params) {
			if("start".equals(params[0]))
			{
				try {
					Map<String,String> valuesmap = MPSbackendApi.startApp(params[1], params[2],params[3], params[4]);
						valuesmap.put("itemid", params[5]);
					return valuesmap;
				} catch (IOException e) {
					e.printStackTrace();
					return null;
				} catch (JSONException e) {
					e.printStackTrace();
					return null;
				}
			}else
			{
				try {
					Map<String,String> valuesmap = MPSbackendApi.stopApp(params[1], params[2],params[3], params[4]);
						valuesmap.put("itemid", params[5]);
					return valuesmap;
				} catch (IOException e) {
					e.printStackTrace();
					return null;
				} catch (JSONException e) {
					e.printStackTrace();
					return null;
				}
			}
		}
		@Override
		protected void onPostExecute(Map<String,String> resultmap) {
			super.onPostExecute(resultmap);
			if (dialog.isShowing() && !getActivity().isFinishing() && getActivity() != null)
				dialog.dismiss();
			if(resultmap!=null)
			{
				int itemid = Integer.valueOf(resultmap.get("itemid"));
				if(resultmap.get("returnCode").equals("000"))
				{
					if(applist.get(itemid).state.contains("STOP"))
					{
						applist.get(itemid).state="STARTED";
						showCrouton(getString(R.string.done_turnon), STYLE_CONFIRM, Configuration.DEFAULT);
					}
					else
					{
						applist.get(itemid).state="STOPPED";
						showCrouton(getString(R.string.done_turnoff), STYLE_CONFIRM, Configuration.DEFAULT);
					}	
				}else
				{
					if(applist.get(itemid).state.contains("STOP"))
					{
						showCrouton(getString(R.string.fail_turnon), STYLE_ALERT, Configuration.DEFAULT);
					}
					else
					{
						showCrouton(getString(R.string.fail_turnoff), STYLE_ALERT, Configuration.DEFAULT);
					}	
				}
			}else
			{
				showCrouton(getString(R.string.ioexception),STYLE_ALERT, Configuration.DEFAULT);
			}
			appAdapter.notifyDataSetChanged();
		}
		
	}
}
