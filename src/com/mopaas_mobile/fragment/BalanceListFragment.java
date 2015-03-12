package com.mopaas_mobile.fragment;

import java.io.IOException;
import java.util.Map;

import org.json.JSONException;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mopaas_mobile.activity.R;
import com.mopaas_mobile.api.MPSbackendApi;
import com.mopaas_mobile.crouton.Configuration;
import com.mopaas_mobile.crouton.Crouton;
import com.mopaas_mobile.crouton.Style;

public class BalanceListFragment extends Fragment {

	SharedPreferences cashedata;
	private static final Style STYLE_ALERT = new Style.Builder().
		    setBackgroundColorValue(Style.holoRedLight).setTextSize(20).build();
	private TextView todayconsumptionTxt,todaymemoryTxt,todaypostgresqlTxt,todaymysqlTxt,todayextraTxt;
	private TextView monthconsumptionTxt,monthmemoryTxt,monthpostgresqlTxt,monthmysqlTxt,monthextraTxt;
	private TextView yearconsumptionTxt,yearmemoryTxt,yearpostgresqlTxt,yearmysqlTxt,yearextraTxt;
	private TextView totalconsumptionTxt,totalmemoryTxt,totalpostgresqlTxt,totalmysqlTxt,totalextraTxt;
	private String token;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View contentview = inflater.inflate(R.layout.fragment_balancelist, container,false);
		return contentview;
	}
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		initView();
		GetBalanceTask task = new GetBalanceTask();
		task.execute(token);
	}
	private void initView()
	{
		todayconsumptionTxt = (TextView)getActivity().findViewById(R.id.todayconsumption);
		todaymemoryTxt = (TextView)getActivity().findViewById(R.id.todaymemory);
		todaypostgresqlTxt = (TextView)getActivity().findViewById(R.id.todaypostgresql);
		todaymysqlTxt = (TextView)getActivity().findViewById(R.id.todaymysql);
		todayextraTxt = (TextView)getActivity().findViewById(R.id.todayextra);
		
		monthconsumptionTxt = (TextView)getActivity().findViewById(R.id.monthconsumption);
		monthmemoryTxt = (TextView)getActivity().findViewById(R.id.monthmemory);
		monthpostgresqlTxt = (TextView)getActivity().findViewById(R.id.monthpostgresql);
		monthmysqlTxt = (TextView)getActivity().findViewById(R.id.monthmysql);
		monthextraTxt = (TextView)getActivity().findViewById(R.id.monthextra);
		
		yearconsumptionTxt = (TextView)getActivity().findViewById(R.id.yearconsumption);
		yearmemoryTxt = (TextView)getActivity().findViewById(R.id.yearmemory);
		yearpostgresqlTxt = (TextView)getActivity().findViewById(R.id.yearpostgresql);
		yearmysqlTxt = (TextView)getActivity().findViewById(R.id.yearmysql);
		yearextraTxt = (TextView)getActivity().findViewById(R.id.yearextra);
		
		totalconsumptionTxt = (TextView)getActivity().findViewById(R.id.totalconsumption);
		totalmemoryTxt = (TextView)getActivity().findViewById(R.id.totalmemory);
		totalpostgresqlTxt = (TextView)getActivity().findViewById(R.id.totalpostgresql);
		totalmysqlTxt = (TextView)getActivity().findViewById(R.id.totalmysql);
		totalextraTxt = (TextView)getActivity().findViewById(R.id.totalextra);
		
		cashedata = getActivity().getSharedPreferences("MoPaaS_mobile", 0);
		token = cashedata.getString("token", null);
	}
	private void showCrouton(String croutonText, Style croutonStyle, Configuration configuration) {
	    final Crouton crouton;
	    crouton = Crouton.makeText(getActivity(), croutonText, croutonStyle);
	    crouton.setConfiguration( configuration).show();
	  }
	class GetBalanceTask extends AsyncTask<String,String,Map<String,String>>
	{
		@Override
		protected Map<String,String> doInBackground(String... params) {
			try {
				return MPSbackendApi.getPaylist(params[0]);
			} catch (IOException e) {
				e.printStackTrace();
				return null;
			} catch (JSONException e) {
				e.printStackTrace();
				return null;
			}
		}
		@Override
		protected void onPostExecute(Map<String,String> result) {
			super.onPostExecute(result);
			if(result!=null)
			{
				if("000".equals(result.get("returnCode")))
				{
					todayconsumptionTxt.setText(getActivity().getResources().getString(R.string.rmb)+result.get("day-allMoney"));
					todaymemoryTxt.setText(getActivity().getResources().getString(R.string.rmb)+result.get("day-Memory"));
					todaypostgresqlTxt.setText(getActivity().getResources().getString(R.string.rmb)+result.get("day-Postgresql"));
					todaymysqlTxt.setText(getActivity().getResources().getString(R.string.rmb)+result.get("day-Mysql"));
					todayextraTxt.setText(getActivity().getResources().getString(R.string.rmb)+String.valueOf(
							Float.parseFloat(result.get("day-allMoney"))-
							Float.parseFloat(result.get("day-Memory"))-
							Float.parseFloat(result.get("day-Postgresql"))-
							Float.parseFloat(result.get("day-Mysql"))));
					
					monthconsumptionTxt.setText(getActivity().getResources().getString(R.string.rmb)+result.get("month-allMoney"));
					monthmemoryTxt.setText(getActivity().getResources().getString(R.string.rmb)+result.get("month-Memory"));
					monthpostgresqlTxt.setText(getActivity().getResources().getString(R.string.rmb)+result.get("month-Postgresql"));
					monthmysqlTxt.setText(getActivity().getResources().getString(R.string.rmb)+result.get("month-Mysql"));
					monthextraTxt.setText(getActivity().getResources().getString(R.string.rmb)+String.valueOf(
							Float.parseFloat(result.get("month-allMoney"))-
							Float.parseFloat(result.get("month-Memory"))-
							Float.parseFloat(result.get("month-Postgresql"))-
							Float.parseFloat(result.get("month-Mysql"))));
					
					yearconsumptionTxt.setText(getActivity().getResources().getString(R.string.rmb)+result.get("year-allMoney"));
					yearmemoryTxt.setText(getActivity().getResources().getString(R.string.rmb)+result.get("year-Memory"));
					yearpostgresqlTxt.setText(getActivity().getResources().getString(R.string.rmb)+result.get("year-Postgresql"));
					yearmysqlTxt.setText(getActivity().getResources().getString(R.string.rmb)+result.get("year-Mysql"));
					yearextraTxt.setText(getActivity().getResources().getString(R.string.rmb)+String.valueOf(
							Float.parseFloat(result.get("year-allMoney"))-
							Float.parseFloat(result.get("year-Memory"))-
							Float.parseFloat(result.get("year-Postgresql"))-
							Float.parseFloat(result.get("year-Mysql"))));
					
					totalconsumptionTxt.setText(getActivity().getResources().getString(R.string.rmb)+result.get("total-allMoney"));
					totalmemoryTxt.setText(getActivity().getResources().getString(R.string.rmb)+result.get("total-Memory"));
					totalpostgresqlTxt.setText(getActivity().getResources().getString(R.string.rmb)+result.get("total-Postgresql"));
					totalmysqlTxt.setText(getActivity().getResources().getString(R.string.rmb)+result.get("total-Mysql"));
					totalextraTxt.setText(getActivity().getResources().getString(R.string.rmb)+String.valueOf(
							Float.parseFloat(result.get("total-allMoney"))-
							Float.parseFloat(result.get("total-Memory"))-
							Float.parseFloat(result.get("total-Postgresql"))-
							Float.parseFloat(result.get("total-Mysql"))));
				}else
					showCrouton(getString(R.string.delete_error),STYLE_ALERT, Configuration.DEFAULT);
			}else
				showCrouton(getString(R.string.ioexception),STYLE_ALERT, Configuration.DEFAULT);
		}
	} 
}
