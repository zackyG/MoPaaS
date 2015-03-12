package com.mopaas_mobile.config;
//
// GlobalConfig.java by Administrator at 上午9:18:29
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
public interface GlobalConfig {
	
//	public final static String SERVER_PATH = "http://www.mopaas.com";
	public final static String SERVER_PATH = "http://www.tiger.mopaas.com";
//	public final static String TESTSERVER_PATH = "http://mopaastest.sturgeon.mopaas.com";
	public final static String PUSHSERVER_PATH = "http://pushs.sturgeon.mopaas.com";
	final static String API_LOGIN = "/login.json";						
	final static String API_LOGOUT = "/user/logout.json";
	final static String API_GETUSER = "/user/getUser.json";
	final static String API_ORGANIZATION = "/organizations/list.json";
	final static String API_APPLIST = "/apps/list.json";
	final static String API_QUOTA = "/users/quota.json";
	final static String API_COUNTCHART = "/apps/requestCountChart.json";
	final static String API_STARTAPP = "/apps/start.json";
	final static String API_STOPAPP = "/apps/stop.json";
	final static String API_GETINSTANCE = "/apps/instances.json";
	final static String API_MODIFYAPP = "/apps/updateApps.json";
	final static String API_MESSAGELIST = "/user/getRemindsList.json";
	final static String API_MESSAGEREAD = "/user/setRemindHaveRead.json";
	final static String API_PUSH_SETTAG = "/api/devices/addDevice.json";
	final static String PUSH_SERVICEKEY = "1bc732ed0e04401898391fc851a363d3";
}
