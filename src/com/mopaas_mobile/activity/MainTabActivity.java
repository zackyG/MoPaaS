package com.mopaas_mobile.activity;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.json.JSONException;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mopaas_mobile.api.MPSbackendApi;
import com.mopaas_mobile.domain.AppLication;
import com.mopaas_mobile.domain.Message;
import com.mopaas_mobile.fragment.AppListFragment;
import com.mopaas_mobile.fragment.BalanceDetailFragment;
import com.mopaas_mobile.fragment.BalanceListFragment;
import com.mopaas_mobile.fragment.ServiceListFragment;
import com.mopaas_mobile.utils.BadgeView;
import com.tencent.mm.sdk.platformtools.Log;

public class MainTabActivity extends FragmentActivity implements OnClickListener{
	
	SharedPreferences cashedata;
	public static List<AppLication> applist;
	public static List<Message> msglist;
	public static int memoryCount;
	private static BadgeView unread_tv;
	private String token,username;
	private static String unreadnumber;
	private static int unreadcount;
	
	private DrawerLayout drawerLayout;
	private Button dawerBtn;
	private TextView userintoTxt,applistTxt,svclistTxt,balanceTxt,payTxt,logoutTxt;
	private TextView frameheadTxt;
    private FragmentManager fragmentManager; 
    private AppListFragment applistFragment;
    private ServiceListFragment servicelistFragment;
    private BalanceListFragment paylistFragment;
    private BalanceDetailFragment balanceFragment;
    private Dialog LogoutDialog;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_maintab);
		initView();
		initDialog();
		fragmentManager = getSupportFragmentManager();
		setFragment(0);
	}
	private void setFragment(int number)
	{
		// 开启一个Fragment事务
		FragmentTransaction transaction = fragmentManager.beginTransaction();
		// 先隐藏掉所有的Fragment，以防止有多个Fragment显示在界面上的情况
		removeFragments(transaction);
		switch(number)
		{
			case 0:
				frameheadTxt.setText(getResources().getString(R.string.applist));
				if(applistFragment==null)
				{
					applistFragment = new AppListFragment();
					transaction.add(R.id.contentlayout, applistFragment);
				}else
					transaction.show(applistFragment);
				break;
			case 1:
				frameheadTxt.setText(getResources().getString(R.string.svclist));
				if(servicelistFragment==null)
				{
					servicelistFragment = new ServiceListFragment();
					transaction.add(R.id.contentlayout, servicelistFragment);
				}else
					transaction.show(servicelistFragment);
				break;
			case 2:
				frameheadTxt.setText(getResources().getString(R.string.balancedetail));
				if(paylistFragment==null)
				{
					paylistFragment = new BalanceListFragment();
					transaction.add(R.id.contentlayout, paylistFragment);
				}else
					transaction.show(paylistFragment);
				break;
			case 3:
				frameheadTxt.setText(getResources().getString(R.string.payrecord));
				if(balanceFragment==null)
				{
					balanceFragment = new BalanceDetailFragment();
					transaction.add(R.id.contentlayout, balanceFragment);
				}else
					transaction.show(balanceFragment);
				break;
		}
		transaction.commit();
	}
	/** 
     * 将所有的Fragment都置为隐藏状态。 
     *  
     * @param transaction 
     *            用于对Fragment执行操作的事务 
     */  
    private void removeFragments(FragmentTransaction transaction) {  
        if (applistFragment != null) {
            transaction.remove(applistFragment);  
            applistFragment=null;
        }  
        if (servicelistFragment != null) {  
            transaction.remove(servicelistFragment);  
            servicelistFragment = null;
        }  
        if (paylistFragment != null) {  
            transaction.remove(paylistFragment);  
            paylistFragment = null;
        }  
        if (balanceFragment != null) {  
            transaction.remove(balanceFragment);  
            balanceFragment = null;
        }  
    }  
	private void initView()
	{
		cashedata = getSharedPreferences("MoPaaS_mobile", 0);
		username = cashedata.getString("username", "");
		drawerLayout = (DrawerLayout)findViewById(R.id.drawerlayout);
		userintoTxt = (TextView)findViewById(R.id.userinto_txt);
		userintoTxt.setText(username.substring(0, username.indexOf("@")));
		dawerBtn = (Button)findViewById(R.id.dawerbtn);
		frameheadTxt = (TextView)findViewById(R.id.frameheadtext);
		applistTxt = (TextView)findViewById(R.id.applist_txt);
		svclistTxt = (TextView)findViewById(R.id.svclist_txt);
		balanceTxt = (TextView)findViewById(R.id.balance_txt);
		payTxt = (TextView)findViewById(R.id.pay_txt);
		logoutTxt = (TextView)findViewById(R.id.logout_txt);
		applistTxt.setOnClickListener(this);
		svclistTxt.setOnClickListener(this);
		balanceTxt.setOnClickListener(this);
		payTxt.setOnClickListener(this);
		dawerBtn.setOnClickListener(this);
		logoutTxt.setOnClickListener(this);
	}
	private void initDialog()
	{
		LayoutInflater inflater = getLayoutInflater();
		View logoutdialogView = inflater.inflate(R.layout.logout_dialog,null);
		Button logoutsubmitbtn  = (Button)logoutdialogView.findViewById(R.id.savebtn);
		Button logoutcancelbtn  = (Button)logoutdialogView.findViewById(R.id.cancelbtn);
		logoutsubmitbtn.setOnClickListener(new OnClickListener() {		
			@Override
			public void onClick(View v) {
				cashedata.edit().clear().commit();
				LogoutDialog.dismiss();
				Intent logoutint = new Intent();
				logoutint.setClass(getApplicationContext(), LoginActivity.class);
				startActivity(logoutint);
				finish();
			}
		});
		logoutcancelbtn.setOnClickListener(new OnClickListener() {	
			@Override
			public void onClick(View v) {
				LogoutDialog.dismiss();
			}
		});
		LogoutDialog = new Dialog(this, R.style.dialog);
		LogoutDialog.setContentView(logoutdialogView);
		LogoutDialog.setCancelable(true);
	}
	@Override
	public void onClick(View view) {
		switch(view.getId())
		{
			case R.id.dawerbtn:
				if(!drawerLayout.isDrawerOpen(Gravity.LEFT))
					drawerLayout.openDrawer(Gravity.LEFT);
				else
					drawerLayout.closeDrawer(Gravity.LEFT);
				break;
			case R.id.applist_txt:
				setFragment(0);
				if(drawerLayout.isDrawerOpen(Gravity.LEFT))
					drawerLayout.closeDrawer(Gravity.LEFT);
				break;
			case R.id.svclist_txt:
				setFragment(1);
				if(drawerLayout.isDrawerOpen(Gravity.LEFT))
					drawerLayout.closeDrawer(Gravity.LEFT);
				break;
			case R.id.balance_txt:
				setFragment(2);
				if(drawerLayout.isDrawerOpen(Gravity.LEFT))
					drawerLayout.closeDrawer(Gravity.LEFT);
				break;
			case R.id.pay_txt:
				setFragment(3);
				if(drawerLayout.isDrawerOpen(Gravity.LEFT))
					drawerLayout.closeDrawer(Gravity.LEFT);
				break;
			case R.id.logout_txt:
				cashedata.edit().putString("token", null).commit();
				cashedata.edit().putString("password", null).commit();
				LogoutDialog.show();
				break;
		}	 
	}
	public static Handler readmsgHandler = new Handler()
	{
		public void handleMessage(android.os.Message msg) 
		{
			if(msg.what==1&&unreadcount>0)
			{
				unreadcount--;
				unread_tv.setVisibility(View.VISIBLE);
				unread_tv.setText(String.valueOf(unreadcount));
			}else if(msg.what==10)
			{
				unread_tv.setVisibility(View.INVISIBLE);
			}
			if(msg.arg1 ==10)
			{
			}	
		};
	};
	class MessageTask extends AsyncTask<String,Integer,Map<String,Object>>
	{

		@Override
		protected Map<String, Object> doInBackground(String... arg0) {
			// TODO Auto-generated method stub
			Map<String,Object> resultmap = null;
			try {
				resultmap = MPSbackendApi.getMessages(token, "0", "20");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return resultmap;
		}
		@SuppressWarnings("unchecked")
		@Override
		protected void onPostExecute(Map<String, Object> resultmap) {
			super.onPostExecute(resultmap);
			if(resultmap.get("returnCode").equals("000"))
			{
				msglist = (List<Message>)resultmap.get("list");
				unreadnumber = (String)resultmap.get("unreadcount");
				unreadcount = Integer.valueOf(unreadnumber);
				if(!unreadnumber.equals("0"))
				{
					unread_tv.setVisibility(View.VISIBLE);
					unread_tv.setText(unreadnumber);
				}
				else
					unread_tv.setVisibility(View.INVISIBLE);
			}
		}
	}
}
