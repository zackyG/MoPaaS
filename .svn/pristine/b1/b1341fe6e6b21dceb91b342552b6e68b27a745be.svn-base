package com.mopaas_mobile.parser;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

//
// PayListParser.java by Administrator at 下午4:41:58
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
public class PayListParser {

	public static Map<String,String> parse(String source) throws JSONException
	{
		Map<String,String> resultMap = new HashMap<String, String>();
		JSONObject jsonroot = new JSONObject(source);
		if("000".equals(jsonroot.getString("returnCode")))
		{
			JSONObject jsonResult = jsonroot.getJSONObject("result");
			if(!jsonResult.isNull("month"))
			{
				JSONObject jsonMonth = jsonResult.getJSONObject("month");
				if(!jsonMonth.isNull("Postgresql"))
					resultMap.put("month-Postgresql", jsonMonth.getString("Postgresql"));
				else
					resultMap.put("month-Postgresql", "0");
				if(!jsonMonth.isNull("Memory"))
					resultMap.put("month-Memory", jsonMonth.getString("Memory"));
				else
					resultMap.put("month-Memory", "0");
				if(!jsonMonth.isNull("allMoney"))
					resultMap.put("month-allMoney", jsonMonth.getString("allMoney"));
				else
					resultMap.put("month-allMoney", "0");
				if(!jsonMonth.isNull("Mysql"))
					resultMap.put("month-Mysql", jsonMonth.getString("Mysql"));
				else
					resultMap.put("month-Mysql", "0");
			}
			if(!jsonResult.isNull("year"))
			{
				JSONObject jsonYear = jsonResult.getJSONObject("year");
				if(!jsonYear.isNull("Postgresql"))
					resultMap.put("year-Postgresql", jsonYear.getString("Postgresql"));
				else
					resultMap.put("year-Postgresql", "0");
				if(!jsonYear.isNull("Memory"))
					resultMap.put("year-Memory", jsonYear.getString("Memory"));
				else
					resultMap.put("year-Memory", "0");
				if(!jsonYear.isNull("allMoney"))
					resultMap.put("year-allMoney", jsonYear.getString("allMoney"));
				else
					resultMap.put("year-allMoney", "0");
				if(!jsonYear.isNull("Mysql"))
					resultMap.put("year-Mysql", jsonYear.getString("Mysql"));
				else
					resultMap.put("year-Mysql", "0");
			}
			if(!jsonResult.isNull("day"))
			{
				JSONObject jsonDay = jsonResult.getJSONObject("day");
				if(!jsonDay.isNull("Postgresql"))
					resultMap.put("day-Postgresql", jsonDay.getString("Postgresql"));
				else
					resultMap.put("day-Postgresql", "0");
				if(!jsonDay.isNull("Memory"))
					resultMap.put("day-Memory", jsonDay.getString("Memory"));
				else
					resultMap.put("day-Memory", "0");
				if(!jsonDay.isNull("allMoney"))
					resultMap.put("day-allMoney", jsonDay.getString("allMoney"));
				else
					resultMap.put("day-allMoney", "0");
				if(!jsonDay.isNull("Mysql"))
					resultMap.put("day-Mysql", jsonDay.getString("Mysql"));
				else
					resultMap.put("day-Mysql", "0");
			}
			if(!jsonResult.isNull("all"))
			{
				JSONObject jsonTotal = jsonResult.getJSONObject("all");
				if(!jsonTotal.isNull("Postgresql"))
					resultMap.put("total-Postgresql", jsonTotal.getString("Postgresql"));
				else
					resultMap.put("total-Postgresql", "0");
				if(!jsonTotal.isNull("Memory"))
					resultMap.put("total-Memory", jsonTotal.getString("Memory"));
				else
					resultMap.put("total-Memory", "0");
				if(!jsonTotal.isNull("allMoney"))
					resultMap.put("total-allMoney", jsonTotal.getString("allMoney"));
				else
					resultMap.put("total-allMoney", "0");
				if(!jsonTotal.isNull("Mysql"))
					resultMap.put("total-Mysql", jsonTotal.getString("Mysql"));
				else
					resultMap.put("total-Mysql", "0");
			}
		}else
			resultMap.put("returnMessage", jsonroot.getString("returnMessage"));
		
		resultMap.put("returnCode", jsonroot.getString("returnCode"));
		return resultMap;
	}
}
