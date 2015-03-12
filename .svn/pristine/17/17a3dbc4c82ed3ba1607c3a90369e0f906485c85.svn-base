package com.mopaas_mobile.fragment;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

import org.json.JSONException;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.mopaas_mobile.activity.R;
import com.mopaas_mobile.adapter.SvcListAdapter;
import com.mopaas_mobile.api.MPSbackendApi;
import com.mopaas_mobile.crouton.Configuration;
import com.mopaas_mobile.crouton.Crouton;
import com.mopaas_mobile.crouton.Style;
import com.mopaas_mobile.domain.SerVice;
import com.mopaas_mobile.pulltorefresh.ptr.PtrClassicFrameLayout;
import com.mopaas_mobile.pulltorefresh.ptr.PtrDefaultHandler;
import com.mopaas_mobile.pulltorefresh.ptr.PtrFrameLayout;
import com.mopaas_mobile.pulltorefresh.ptr.PtrHandler;

public class ServiceListFragment extends Fragment {

	private static final Style STYLE_CONFIRM = new Style.Builder().
			setBackgroundColorValue(Style.holoGreenLight).setTextSize(20).build();
	private static final Style STYLE_ALERT = new Style.Builder().
		    setBackgroundColorValue(Style.holoRedLight).setTextSize(20).build();
	private SharedPreferences cashedata; 
	private String token;
	private String spaceGuid,orgGuid;
	private String usedMemory;
	private String totalMemory;
	private TextView memoryTxt;
	private TextView svclistcautionTxt;
	private ProgressBar memorybar;
	private ListView svcListView;
	private SvcListAdapter adapter; 
	private ArrayList<SerVice> svclist; 
	private ProgressDialog dialog = null;
	private PtrClassicFrameLayout mPtrFrame;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View contentview = inflater.inflate(R.layout.fragment_servicelist, container,false);
		return contentview;
	}
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		initView();
		GetListTask task = new GetListTask();
		task.execute(token,spaceGuid);
	}
	@Override
	public void onResume() {
		super.onResume();
		totalMemory = cashedata.getString("memoryLimit", "0");
		usedMemory = cashedata.getString("usedMemory", "0");
		memoryTxt.setText(usedMemory+"M/"+totalMemory+"M");
		memorybar.setProgress(((100*Integer.valueOf(usedMemory))/Integer.valueOf(totalMemory)));
	}
	private void initView()
	{
		memoryTxt = (TextView)getActivity().findViewById(R.id.svcmemoryinfo);
		memorybar = (ProgressBar)getActivity().findViewById(R.id.svcmemorybar);
		svcListView = (ListView)getActivity().findViewById(R.id.svclistview);
		svclistcautionTxt = (TextView)getActivity().findViewById(R.id.svclistcaution);
		mPtrFrame = (PtrClassicFrameLayout)getActivity().findViewById(R.id.rotate_header_svclistview_frame);
		cashedata = getActivity().getSharedPreferences("MoPaaS_mobile", 0);
		token = cashedata.getString("token", "");
		spaceGuid = cashedata.getString("spaceGuid", "");
		orgGuid = cashedata.getString("orgGuid", "");
		mPtrFrame.setLastUpdateTimeRelateObject(this);
        mPtrFrame.setPtrHandler(new PtrHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
            	mPtrFrame.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                    	GetListTask task = new GetListTask();
                		task.execute(token,spaceGuid);
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
	}
	private void showCrouton(String croutonText, Style croutonStyle, Configuration configuration) {
	    final Crouton crouton;
	    crouton = Crouton.makeText(getActivity(), croutonText, croutonStyle);
	    crouton.setConfiguration(configuration).show();
	  }
	class GetListTask extends AsyncTask<String,String,Map<String,Object>>
	{

		@Override
		protected Map<String,Object> doInBackground(String... params) {
			try {
				return MPSbackendApi.getServiceList(params[0], params[1]);
			} catch (IOException e) {
				e.printStackTrace();
				return null;
			} catch (JSONException e) {
				e.printStackTrace();
				return null;
			}
		}
		@SuppressWarnings("unchecked")
		@Override
		protected void onPostExecute(Map<String,Object> result) {
			super.onPostExecute(result);
			mPtrFrame.refreshComplete();
			if(result!=null)
			{
				if("000".equals(result.get("returnCode")))
				{
					svclist = (ArrayList<SerVice>)result.get("list");
					if(svclist.size()>0)
					{
						svclistcautionTxt.setVisibility(View.GONE);
						adapter = new SvcListAdapter(ServiceListFragment.this, svclist);
						svcListView.setAdapter(adapter);
					}else
						svclistcautionTxt.setVisibility(View.VISIBLE);
				}else
					showCrouton(getString(R.string.delete_error),STYLE_ALERT, Configuration.DEFAULT);
			}else
				showCrouton(getString(R.string.ioexception),STYLE_ALERT, Configuration.DEFAULT);
		}
	}
	class DeleteSvcTask extends AsyncTask<String,String,Map<String,String>>
	{
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			dialog = new ProgressDialog(getActivity());
			dialog.setMessage(getResources().getString(R.string.loadingdelete));
			dialog.setCancelable(true);
			dialog.show();
		}
		@Override
		protected Map<String, String> doInBackground(String... params) {
			Map<String, String> resultMap;
			try {
				resultMap = MPSbackendApi.deleteService(params[0], params[1], params[2], params[3]);
				resultMap.put("position", params[4]);
				return resultMap;
			} catch (IOException e) {
				e.printStackTrace();
				return null;
			} catch (JSONException e) {
				e.printStackTrace();
				return null;
			}
		}
		@Override
		protected void onPostExecute(Map<String, String> result) {
			super.onPostExecute(result);
			if (dialog.isShowing() && !getActivity().isFinishing() && getActivity() != null)
				dialog.dismiss();
			if(result!=null)
			{
				if("000".equals(result.get("returnCode")))
				{
					svclist.remove(Integer.parseInt(result.get("position")));
					adapter.notifyDataSetChanged();
					showCrouton(getString(R.string.delete_svc_success), STYLE_CONFIRM, Configuration.DEFAULT);				
				}else
					showCrouton(getString(R.string.delete_error), STYLE_ALERT, Configuration.DEFAULT);
			}else
				showCrouton(getString(R.string.ioexception), STYLE_ALERT, Configuration.DEFAULT);	
		}
	}
}
