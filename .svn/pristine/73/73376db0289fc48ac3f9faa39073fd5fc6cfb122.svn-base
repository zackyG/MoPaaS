package com.mopaas_mobile.activity;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

public class LogoActivity extends Activity {

	LinearLayout myLayout;
	protected int height, width;
	SharedPreferences sp;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.logolayout);
		sp = getSharedPreferences("UserInfo", 0);
		myLayout = (LinearLayout) findViewById(R.id.myLayout);
		
//		ViewTreeObserver vto = myLayout.getViewTreeObserver();
//
//		vto.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
//			public boolean onPreDraw() {
//				height = myLayout.getMeasuredHeight();
//				width = myLayout.getMeasuredWidth();
//				sp.edit().putInt("height", height).commit();
//				sp.edit().putInt("width", width).commit();
//				// 获取到宽度和高度后，可用于计算
//				return true;
//			}
//		});
		toNextActivity();
	}

	void toNextActivity() {
		final Intent intent = new Intent();
		intent.setClass(LogoActivity.this, LoginActivity.class);
		Timer mTimer = new Timer();// 添加时间机器
		TimerTask task = new TimerTask() {// add the TimerTask

			@Override
			public void run() {
				// TODO Auto-generated method stub
				startActivity(intent);
			}
		};
		mTimer.schedule(task, 1 * 2000);// 1秒后调用
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		this.finish();
	}

}
