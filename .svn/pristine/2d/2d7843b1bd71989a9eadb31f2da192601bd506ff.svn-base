package com.mopaas_mobile.parser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

//
// OrganizationParser.java by Administrator at 下午3:52:59
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
public class OrganizationParser {

	public static Map<String,Object> doParse(String source) throws JSONException
	{
		JSONObject jsonObject = new JSONObject(source);
		ArrayList<BasicNameValuePair> spacelist = new ArrayList<BasicNameValuePair>();
		Map<String,Object> resultmap = new HashMap<String,Object>();
		if(!jsonObject.isNull("code"))
			resultmap.put("code", jsonObject.getString("code"));
		JSONArray jsonlistorg = jsonObject.getJSONArray("organizations");
			JSONObject jsonItem = jsonlistorg.getJSONObject(0);
			if(!jsonItem.isNull("guid"))
			resultmap.put("guid", jsonItem.getString("guid"));
			JSONArray jsonlistitem = jsonItem.getJSONArray("spaceses");
			for(int j=0;j < jsonlistitem.length();j++)
			{
				JSONObject jsonapace = jsonlistitem.getJSONObject(j);
				spacelist.add(new BasicNameValuePair(jsonapace.getString("guid"), jsonapace.getString("name")));
			}
		resultmap.put("list", spacelist);
		return resultmap;
		
	}
}
