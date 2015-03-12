package com.mopaas_mobile.parser;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.mopaas_mobile.domain.AppLication;
import com.mopaas_mobile.domain.Balance;
import com.mopaas_mobile.domain.Instance;
import com.mopaas_mobile.domain.Message;
import com.mopaas_mobile.domain.SerVice;
import com.mopaas_mobile.domain.User;

//
// ParserBasic.java by Administrator at 下午3:58:04
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
public class ParserBasic {

		public static User userParse(String source) throws JSONException
		{
			JSONObject jsonObject = new JSONObject(source);
			User user = new User();
			if(!jsonObject.isNull("token"))
				user.setToken(jsonObject.getString("token"));
			return user;
		}
		public static Instance instanceParse(String source) throws JSONException
		{
			JSONObject jsonObject = new JSONObject(source);
			Instance instance = new Instance();
			if(!jsonObject.isNull("id"))
				instance.instanceID =Integer.toString(jsonObject.getInt("id")+1);
			if(!jsonObject.isNull("state"))
				instance.state = jsonObject.getString("state");
			if(!jsonObject.isNull("uptime"))
			{
				int time = jsonObject.getInt("uptime");
				int d = time/(3600*24);
				int h = (time-d*3600*24)/3600;
				int m = (time-d*3600*24-h*3600)/60;
				instance.time = d+"d:"+h+"h:"+m+"m";
			}
			if(!jsonObject.isNull("usage"))
			{
				JSONObject jsonUsage = jsonObject.getJSONObject("usage");
				if(!jsonUsage.isNull("cpu"))
				{
					double cpucount = jsonUsage.getDouble("cpu");
					cpucount = cpucount*100;
					BigDecimal b = new BigDecimal(cpucount);  
					double f = b.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();  
					instance.stateCpu= f + "%";
				}
				if(!jsonUsage.isNull("disk"))
				{
					double diskcount = jsonUsage.getDouble("disk");
					double diskvalue = diskcount/(1024.00*1024.00);
					BigDecimal b = new BigDecimal(diskvalue);  
					double f = b.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();  
					instance.disk = f+"M";
				}
				if(!jsonUsage.isNull("mem"))
				{
					double memorycount = jsonUsage.getDouble("mem");
					double memoryvalue = memorycount/(1024.00*1024.00);
					BigDecimal b = new BigDecimal(memoryvalue);  
					double f = b.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();  
					instance.mems = f+"M";
				}
			}
			return instance;
		}
		public static AppLication applicationParse(String source) throws JSONException
		{
			JSONObject jsonObject = new JSONObject(source);
			AppLication app = new AppLication();
			if(!jsonObject.isNull("guid"))
				app.appID = jsonObject.getString("guid");
			if(!jsonObject.isNull("buildpack"))
				app.buildpack = jsonObject.getString("buildpack");	
			if(!jsonObject.isNull("name"))
				app.appName = jsonObject.getString("name");				
			if(!jsonObject.isNull("memory"))
				app.memory = jsonObject.getString("memory");
			if(!jsonObject.isNull("instances"))
				app.instanceNum = jsonObject.getString("instances");
			if(!jsonObject.isNull("createdAt"))
				app.createtime = jsonObject.getString("createdAt");
			if(!jsonObject.isNull("state"))
				app.state =jsonObject.getString("state");
			if(!jsonObject.isNull("routeses"))
			{
				String host = null,domain = null;
				JSONObject jsonroute = jsonObject.getJSONArray("routeses").getJSONObject(0);
				if(!jsonroute.isNull("host"))
				{
					host = jsonroute.getString("host");
				}
				if(!jsonroute.isNull("domains"))
				{
					JSONObject jsondomain = jsonroute.getJSONObject("domains");
					domain = jsondomain.getString("name");
				}
				app.uris=host+"."+domain;
			}
			return app;	
		}
		public static SerVice serviceParse(String source) throws JSONException
		{
			JSONObject jsonObject = new JSONObject(source);
			SerVice svc = new SerVice();
			if(!jsonObject.isNull("id"))
				svc.id = jsonObject.getString("id");
			if(!jsonObject.isNull("createdAt"))
				svc.createdTime = jsonObject.getString("createdAt");
			if(!jsonObject.isNull("guid"))
				svc.guid = jsonObject.getString("guid");
			if(!jsonObject.isNull("label"))
				svc.label = jsonObject.getString("label");
			if(!jsonObject.isNull("name"))
				svc.name = jsonObject.getString("name");
			if(!jsonObject.isNull("serviceBindingses")
					&&!"[]".equals(jsonObject.getString("serviceBindingses")))
			{
				svc.serviceBindingses="";
				JSONArray jsonlist = jsonObject.getJSONArray("serviceBindingses");
				for(int i=0;i<jsonlist.length();i++)
				{
					JSONObject jsonApp = jsonlist.getJSONObject(i).getJSONObject("apps");
					if(!jsonApp.isNull("name"))
					{
						svc.serviceBindingses += ","+ jsonApp.getString("name");
					}
				}
				svc.serviceBindingses = svc.serviceBindingses.substring(1);
			}
			
			return svc;
		}
		public static Balance balanceParse(String source) throws JSONException
		{
			JSONObject jsonObject = new JSONObject(source);
			Balance blc = new Balance();
			if(!jsonObject.isNull("balanceDate"))
				blc.balanceDate = jsonObject.getString("balanceDate");
			if(!jsonObject.isNull("balanceStatus"))
				blc.balanceStatus = jsonObject.getString("balanceStatus");
			else
				blc.balanceStatus = "1";
			if(!jsonObject.isNull("balanceType"))
				blc.balanceType = jsonObject.getString("balanceType");
			else 
				blc.balanceType = "1";
			if(!jsonObject.isNull("detailBalance"))
				blc.detailBalance = jsonObject.getString("detailBalance");
			else 
				blc.detailBalance = "0";
			if(!jsonObject.isNull("tradeNo"))
				blc.tradeNo = jsonObject.getString("tradeNo");
			return blc;
		}
		public static Message messageParse(String source) throws JSONException
		{
			JSONObject jsonObject = new JSONObject(source);
			Message msg = new Message();
			if(!jsonObject.isNull("id"))
				msg.setId(jsonObject.getString("id"));
			if(!jsonObject.isNull("info"))
				msg.setContent(jsonObject.getString("info"));
			if(!jsonObject.isNull("time"))
				msg.setCreatetime(jsonObject.getString("time"));
			if(!jsonObject.isNull("flag"))
				msg.setFlag(jsonObject.getInt("flag")==0?false:true);
			return msg;	
		}
		public static Map<String,String> loginParse(String source) throws JSONException
		{
			JSONObject jsonObject = new JSONObject(source);
			Map<String,String> values = new HashMap<String, String>();
			if(!jsonObject.isNull("returnCode"))
				values.put("returnCode", jsonObject.getString("returnCode"));
			if(!jsonObject.isNull("returnMessage"))
				values.put("returnMessage", jsonObject.getString("returnMessage"));
			if(!jsonObject.isNull("result"))
			{
				JSONObject jsonResult = jsonObject.getJSONObject("result");
				if(!jsonResult.isNull("secretkey"))
					values.put("secretkey", jsonResult.getString("secretkey"));
				if(!jsonResult.isNull("usertype"))
				{
					if(jsonResult.getInt("usertype")>0)
							values.put("returnCode","WEBSERVICE_10020");
				}
			}
			if(!jsonObject.isNull("orgGuid"))
				values.put("orgGuid", jsonObject.getString("orgGuid"));
			if(!jsonObject.isNull("spaceGuid"))
				values.put("spaceGuid", jsonObject.getString("spaceGuid"));
			if(!jsonObject.isNull("memoryLimit"))
				values.put("memoryLimit", jsonObject.getString("memoryLimit"));
			if(!jsonObject.isNull("totalServices"))
				values.put("totalServices", jsonObject.getString("totalServices"));
			return values;	
		}
		public static Map<String,String> QuotaParse(String source) throws JSONException
		{
			JSONObject jsonObject = new JSONObject(source);
			Map<String,String> values = new HashMap<String, String>();
			if(!jsonObject.isNull("code"))
				values.put("code", jsonObject.getString("code"));
			if(!jsonObject.isNull("message"))
			{
				values.put("message", jsonObject.getString("message"));
			}else
			{
				if(!jsonObject.isNull("quota"))
				{
					JSONObject jsonQuota = jsonObject.getJSONObject("quota");
					if(!jsonQuota.isNull("memoryLimit"))
						values.put("memoryLimit", jsonQuota.getString("memoryLimit"));
					if(!jsonQuota.isNull("name"))
						values.put("name", jsonQuota.getString("name"));
					if(!jsonQuota.isNull("totalServices"))
						values.put("totalServices", jsonQuota.getString("totalServices"));
					if(!jsonQuota.isNull("usedMemory"))
						values.put("usedMemory", jsonQuota.getString("usedMemory"));
					if(!jsonQuota.isNull("usedServices"))
						values.put("usedServices", jsonQuota.getString("usedServices"));
				}
			}
			return values;	
		}
		
		public static Map<String,String> ChartParse(String source) throws JSONException
		{
			JSONObject jsonObject = new JSONObject(source);
			int dailyQuantity=0,weeklyQuantity=0;
			Map<String,String> values = new HashMap<String, String>();
			if(!jsonObject.isNull("returnCode"))
				values.put("returnCode", jsonObject.getString("returnCode"));
			if(!jsonObject.isNull("returnMessage"))
				values.put("returnMessage", jsonObject.getString("returnMessage"));
			if(!jsonObject.isNull("list"))
			{
				int count = 0;
				JSONArray jsonlist = jsonObject.getJSONArray("list");
				for(int i=0;i<jsonlist.length();i++)
				{
					JSONObject jsonItem = jsonlist.getJSONObject(i);
					if(!jsonItem.isNull("count"))
						count = count + jsonItem.getInt("count");
					if(i==23)dailyQuantity = count;
					if(i==167)weeklyQuantity = count;
				}
				values.put("dailyQuantity", String.valueOf(dailyQuantity));
				values.put("weeklyQuantity", String.valueOf(weeklyQuantity));
			}
			return values;	
		}
		
		public static Map<String,String> ParserHelper(String source,List<String> keylist) throws JSONException
		{
			JSONObject jsonObject = new JSONObject(source);
			Map<String,String> values = new HashMap<String, String>();
			for(int i=0;i<keylist.size();i++)
			{
				if(!jsonObject.isNull(keylist.get(i)))
					values.put(keylist.get(i), jsonObject.getString(keylist.get(i)));
			}
			return values;
		}
		
}
