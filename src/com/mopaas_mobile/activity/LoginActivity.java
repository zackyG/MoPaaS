package com.mopaas_mobile.activity;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request.Method;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.mopaas_mobile.config.GlobalConfigV2;
import com.mopaas_mobile.domain.User;
import com.mopaas_mobile.parser.ParserBasic;
import com.mopaas_mobile.utils.InputMethodRelativeLayout;
import com.mopaas_mobile.utils.InputMethodRelativeLayout.OnSizeChangedListenner;

public class LoginActivity extends Activity implements OnClickListener,OnSizeChangedListenner{

	EditText edt_username,edt_password;
	String username,password,token;
	Button btn_login;
	SharedPreferences cashedata;
	private ProgressDialog dialog = null;
	boolean IsNewUser=false;
	InputMethodRelativeLayout layout;
	LinearLayout inputlayout,logolayout;
	private Drawable mIconSearchClear;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_login);
		initView();
		
		token = cashedata.getString("token", null);
		username = cashedata.getString("username", null);
		password = cashedata.getString("password", null);
		
		if(username!=null)edt_username.setText(username);
		if(password!=null)edt_password.setText(password);
		if(username!=null&&password!=null)
		{
			doLoginInVolley(username,password);
		}
	}
	public void initView()
	{
		layout = (InputMethodRelativeLayout)findViewById(R.id.loginlayout);
		inputlayout = (LinearLayout)findViewById(R.id.inputlayout);
		logolayout = (LinearLayout)findViewById(R.id.logolayout);
		edt_username = (EditText)findViewById(R.id.edt_username);
		edt_password = (EditText)findViewById(R.id.edt_password);
		btn_login = (Button)findViewById(R.id.btn_login);
		mIconSearchClear = getResources().getDrawable(R.drawable.txt_search_clear);
		btn_login.setOnClickListener(this);
		cashedata = getSharedPreferences("MoPaaS_mobile", 0);
		edt_username.addTextChangedListener(tbxSearch_TextChanged);
		edt_username.setOnTouchListener(txtSearch_OnTouch);
		layout.setOnSizeChangedListenner(this);
	}
	@Override
	public void onSizeChange(boolean flag, int w, int h) {
		if(flag)
		{
			logolayout.setPadding(0, -50, 0, 0);
			inputlayout.setPadding(0, 0, 0, 0);
			layout.setPadding(0, -50, 0, 0);
		}
		else
		{
			logolayout.setPadding(0, 180, 0, 0);
			inputlayout.setPadding(0, 0, 0, 0);
			layout.setPadding(0, 0, 0, 0);
		}
	}
	@Override
	public void onClick(View v) {
		switch(v.getId())
		{
			case R.id.btn_login:
				edt_username.setTextSize(16);
				edt_password.setTextSize(16);
				username = edt_username.getText().toString();
				password = edt_password.getText().toString();
				if(username.trim().equals("")||password.trim().equals(""))
					Toast.makeText(getApplicationContext(), R.string.need_nameandpwd, Toast.LENGTH_LONG).show();
				else
				{
					IsNewUser = true;
					doLoginInVolley(username,password);
				}
				break;
		}
		
	}
	private TextWatcher tbxSearch_TextChanged = new TextWatcher() {
        //缓存上一次文本框内是否为空
        private boolean isnull = true;
        @Override
        public void afterTextChanged(Editable s) {
            if (TextUtils.isEmpty(s)) {
                if (!isnull) {
                	edt_username.setCompoundDrawablesWithIntrinsicBounds(null,null, null, null);
                    isnull = true;
                }
            } else {
                if (isnull) {
                	edt_username.setCompoundDrawablesWithIntrinsicBounds(null,null, mIconSearchClear, null);
                    isnull = false;
                }
            }
        }
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count,
                int after) {
        }
        /**
         * 随着文本框内容改变动态改变列表内容
         */
        @Override
        public void onTextChanged(CharSequence s, int start, int before,
                int count) {
            
        }
    };
    private OnTouchListener txtSearch_OnTouch = new OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch (event.getAction()) {
            case MotionEvent.ACTION_UP:
                int curX = (int) event.getX();
                if (curX > v.getWidth() - 38
                        && !TextUtils.isEmpty(edt_username.getText())) {
                	edt_username.setText("");
                    int cacheInputType = edt_username.getInputType();// backup  the input type
                    edt_username.setInputType(InputType.TYPE_NULL);// disable soft input
                    edt_username.onTouchEvent(event);// call native handler
                    edt_username.setInputType(cacheInputType);// restore input  type
                    return true;// consume touch even
                }
                break;
            }
            return false;
        }
    };
	private void doLoginInVolley(final String useremail,final String userpswd)
	{
		dialog = new ProgressDialog(LoginActivity.this);
		dialog.setMessage(getResources().getString(R.string.loading2login));
		dialog.setCancelable(true);
		dialog.show();
		RequestQueue mQueue = Volley.newRequestQueue(LoginActivity.this);
		StringRequest mRequest = new StringRequest(Method.POST, GlobalConfigV2.API_LOGIN,
				new Response.Listener<String>() {
					@Override
					public void onResponse(String response) {
						if (dialog.isShowing() && !LoginActivity.this.isFinishing() && LoginActivity.this != null)
								dialog.dismiss();
						try {
							Map<String,String> responseMap = ParserBasic.loginParse(response);
							if(("000").equals(responseMap.get("returnCode")))
							{
								IsNewUser = false;
								token = responseMap.get("secretkey");
								cashedata.edit().putString("token", token).commit();
								cashedata.edit().putString("orgGuid", responseMap.get("orgGuid")).commit();
								cashedata.edit().putString("spaceGuid", responseMap.get("spaceGuid")).commit();
								cashedata.edit().putString("memoryLimit", responseMap.get("memoryLimit")).commit();
								cashedata.edit().putString("totalServices", responseMap.get("totalServices")).commit();
								cashedata.edit().putString("username", username).commit();
								cashedata.edit().putString("password", password).commit();
								User.getActiveUser().setToken(token);
								Intent intent_toApplist = new Intent(LoginActivity.this,MainTabActivity.class);
								startActivity(intent_toApplist);
								finish();
							}else if("WEBSERVICE_10020".equals(responseMap.get("returnCode")))
							{
								cashedata.edit().putString("token", null).commit();
								cashedata.edit().putString("password", null).commit();
								Toast.makeText(getApplicationContext(), R.string.passworderror, Toast.LENGTH_SHORT).show();
							}
						} catch (JSONException e) {
							e.printStackTrace();
							Toast.makeText(getApplicationContext(), R.string.dataexception, Toast.LENGTH_SHORT).show();
						}
					}
				},new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
						if (dialog.isShowing() && !LoginActivity.this.isFinishing() && LoginActivity.this != null)
							dialog.dismiss();
						Toast.makeText(getApplicationContext(), R.string.ioexception, Toast.LENGTH_SHORT).show();
					}
				})
		{
			@Override
			protected Map<String, String> getParams() throws AuthFailureError {
				Map<String, String> paramMap = new HashMap<String, String>();
				paramMap.put("email", username);
				paramMap.put("password", password);
				return paramMap;
			}
		};
		mQueue.add(mRequest);
	}
}
