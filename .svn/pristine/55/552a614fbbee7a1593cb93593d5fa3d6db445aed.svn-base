package com.mopaas_mobile.parser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.mopaas_mobile.domain.Balance;

//
// BalanceDetailParser.java by Administrator at 下午9:45:09
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
public class BalanceDetailParser {

	public static Map<String,Object> parse(String source) throws JSONException
	{
		Map<String,Object> resultmap = new HashMap<String,Object>();
		JSONObject jsonObject = new JSONObject(source);
			if(jsonObject.getString("returnCode").equals("000"))
			{
				if(!jsonObject.isNull("list")&&!jsonObject.getString("list").equals("[]"))
				{
					JSONArray jsonlistblc = jsonObject.getJSONArray("list");
					List<Balance> balancelist = new ArrayList<Balance>();
					for(int i=0;i<jsonlistblc.length();i++)
					{
						Balance blc = ParserBasic.balanceParse(jsonlistblc.getString(i));
						balancelist.add(blc);
					}
					resultmap.put("list", balancelist);
				}
			}else
				resultmap.put("returnMessage", jsonObject.getString("returnMessage"));
			resultmap.put("returnCode", jsonObject.getString("returnCode"));
		return resultmap;
	}
}
