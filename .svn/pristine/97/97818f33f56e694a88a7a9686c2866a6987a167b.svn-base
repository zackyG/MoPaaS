package com.mopaas_mobile.http;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

//
// BaseHTTP.java by zlj at 下午1:39:32
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
public class BaseHttpRequester {

	public static String doGET(String urlstr,List<BasicNameValuePair> params) throws IOException
	{
		String result = null;
		String content = "";
	    for (int i = 0; i < params.size(); i++) {
	      content = content + "&" + 
	        URLEncoder.encode(((NameValuePair)params.get(i)).getName(), "UTF-8") + "=" + 
	        URLEncoder.encode(((NameValuePair)params.get(i)).getValue(), "UTF-8");
	    }
	    URL url = new URL(urlstr + "?" + content.substring(1));
	    HttpURLConnection connection = (HttpURLConnection)url.openConnection();
	    connection.setDoInput(true);
	    connection.setRequestMethod("GET");
	    connection.setUseCaches(false);
	    connection.connect();
	    InputStream is = connection.getInputStream();
	    BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"));

	    StringBuffer b = new StringBuffer();
	    int ch;
	    while ((ch = br.read()) != -1)
	    {
	      b.append((char)ch);
	    }
	    result = b.toString().trim();
	    connection.disconnect();
		return result;
	}
	public static String doPOST(String urlstr,List<BasicNameValuePair> params) throws IOException
	{
		String result = null;
		URL url = new URL(urlstr);
	    HttpURLConnection connection = (HttpURLConnection)url.openConnection();
	    connection.setDoOutput(true);
	    connection.setDoInput(true);
	    connection.setRequestMethod("POST");
	    connection.setUseCaches(false);
	    connection.setInstanceFollowRedirects(false);
	    connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
//	    connection.setRequestProperty("token",token);
	    connection.setConnectTimeout(30000);
	    connection.setReadTimeout(30000);
	    connection.connect();

	    DataOutputStream out = new DataOutputStream(
	      connection.getOutputStream());

	    String content = "";
	    if(params!=null&&params.size()>0)
	    {
	    	 for (int i = 0; i < params.size(); i++) {
	   	      content = content + "&" + 
	   	        URLEncoder.encode(((NameValuePair)params.get(i)).getName(), "UTF-8") + "=" + 
	   	        URLEncoder.encode(((NameValuePair)params.get(i)).getValue(), "UTF-8");
	   	    }
	   	    out.writeBytes(content.substring(1));
	    }

	    out.flush();
	    out.close();
	    InputStream is = connection.getInputStream();
	    BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"));

	    StringBuffer b = new StringBuffer();
	    int ch;
	    while ((ch = br.read()) != -1)
	    {
	      b.append((char)ch);
	    }
	    result = b.toString().trim();
	    connection.disconnect();
		return result;
	}
	
	public static String doGETwithHeader(String urlstr,String token,List<BasicNameValuePair> params) throws IOException
	{
		String result = null;
		String content = "";
	    for (int i = 0; i < params.size(); i++) {
	      content = content + "&" + 
	    		URLEncoder.encode(((NameValuePair)params.get(i)).getName(), "UTF-8") + "=" + 
	  	        URLEncoder.encode(((NameValuePair)params.get(i)).getValue(), "UTF-8");
	    }
	    URL url = new URL(urlstr + "?" + content.substring(1));
	    HttpURLConnection connection = (HttpURLConnection)url.openConnection();
	    connection.setRequestProperty("token",token);
	    connection.setDoInput(true);
	    connection.setRequestMethod("GET");
	    connection.setUseCaches(false);
	    connection.connect();
	    InputStream is = connection.getInputStream();
	    BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"));

	    StringBuffer b = new StringBuffer();
	    int ch;
	    while ((ch = br.read()) != -1)
	    {
	      b.append((char)ch);
	    }
	    result = b.toString().trim();
	    connection.disconnect();
		return result;
	}

}
