package com.mopaas_mobile.fragment;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

import org.json.JSONException;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.mopaas_mobile.activity.R;
import com.mopaas_mobile.adapter.BalanceListAdapter;
import com.mopaas_mobile.api.MPSbackendApi;
import com.mopaas_mobile.crouton.Configuration;
import com.mopaas_mobile.crouton.Crouton;
import com.mopaas_mobile.crouton.Style;
import com.mopaas_mobile.domain.Balance;
import com.mopaas_mobile.pulltorefresh.ptr.PtrClassicFrameLayout;
import com.mopaas_mobile.pulltorefresh.ptr.PtrDefaultHandler;
import com.mopaas_mobile.pulltorefresh.ptr.PtrFrameLayout;
import com.mopaas_mobile.pulltorefresh.ptr.PtrHandler;


public class BalanceDetailFragment extends Fragment {

	private static final Style STYLE_ALERT = new Style.Builder().
		    setBackgroundColorValue(Style.holoRedLight).setTextSize(20).build();
	private SharedPreferences cashedata; 
	private String token;
	private ListView balancelistview;
	private TextView blclistcautionTxt;
	private BalanceListAdapter adapter;
	private  ArrayList<Balance> balancelist;
	private PtrClassicFrameLayout mPtrFrame;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View contentview = inflater.inflate(R.layout.fragment_balancedetail, container,false);
		return contentview;
	};
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		initView();
		cashedata = getActivity().getSharedPreferences("MoPaaS_mobile", 0);
		token = cashedata.getString("token", "");
		BalanceDetailTask task = new BalanceDetailTask();
		task.execute(token);
	}
	private void initView()
	{
		balancelistview = (ListView)getActivity().findViewById(R.id.balancelist);
		blclistcautionTxt = (TextView)getActivity().findViewById(R.id.blclistcaution);
		mPtrFrame = (PtrClassicFrameLayout)getActivity().findViewById(R.id.rotate_header_blclistview_frame);
		mPtrFrame.setLastUpdateTimeRelateObject(this);
        mPtrFrame.setPtrHandler(new PtrHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
            	mPtrFrame.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                    	BalanceDetailTask task = new BalanceDetailTask();
                		task.execute(token);
                    }
                }, 500);
            }
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header);
            }
        });
        // the following are default settings
        mPtrFrame.setResistance(1.7f);
        mPtrFrame.setRatioOfHeaderHeightToRefresh(1.2f);
        mPtrFrame.setDurationToClose(1200);
        mPtrFrame.setDurationToCloseHeader(1000);
        // default is false
        mPtrFrame.setPullToRefresh(true);
        // default is true
        mPtrFrame.setKeepHeaderWhenRefresh(true);
	}
	private void showCrouton(String croutonText, Style croutonStyle, Configuration configuration) {
	    final Crouton crouton;
	    crouton = Crouton.makeText(getActivity(), croutonText, croutonStyle);
	    crouton.setConfiguration( configuration).show();
	  }
	class BalanceDetailTask extends AsyncTask<String,String,Map<String,Object>>
	{

		@Override
		protected Map<String, Object> doInBackground(String... params) {
			try {
				return MPSbackendApi.getBalanceDetail(params[0]);
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
		protected void onPostExecute(Map<String, Object> result) {
			mPtrFrame.refreshComplete();
			super.onPostExecute(result);
			if(result!=null)
			{
				if("000".equals(result.get("returnCode")))
				{
					balancelist = (ArrayList<Balance>)result.get("list");
					if(balancelist.size()>0)
					{
						blclistcautionTxt.setVisibility(View.GONE);
						if(adapter==null)
						{
							adapter = new BalanceListAdapter(getActivity(), balancelist);
							balancelistview.setAdapter(adapter);
						}else
						{
							balancelist.addAll((ArrayList<Balance>)result.get("list"));
							adapter.notifyDataSetChanged();
						}
					}else
						blclistcautionTxt.setVisibility(View.VISIBLE);
				}else
					showCrouton(getString(R.string.delete_error),STYLE_ALERT, Configuration.DEFAULT);
			}else
				showCrouton(getString(R.string.ioexception),STYLE_ALERT, Configuration.DEFAULT);
		}
	}
}
