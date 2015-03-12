package com.mopaas_mobile.config;
//
// GlobalConfigV2.java by Administrator at 上午10:22:36
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
public interface GlobalConfigV2 {

	public final static String SERVER_PATH = "http://webservice.tiger.mopaas.com";
	public final static String LOGIN_SERVER_PATH = "http://sso.tiger.mopaas.com/common.html";
	public final static String PUSHSERVER_PATH = "http://pushs.sturgeon.mopaas.com";
						
	final static String API_LOGOUT = "/user/logout.json";
	final static String API_GETUSER = "/user/getUser.json";
	/*
	 * 	获取secretkey,登陆(post)
	 * 	email		不能空	String	用户名
	 * 	password	可以空	String	密码
	 */
	final static String API_LOGIN = SERVER_PATH + "/getSecretkey.json";
	/*
	 *  获取应用列表(post)
	 *	secretkey	不能空	String	登录时候获取
	 *	orgGuid		不能空	String	组织guid
	 *	spaceGuid	不能空	String	空间guid
	 */
	final static String API_APPLIST = SERVER_PATH + "/api/app/apps.json";
	
	/*
	 *  启动应用(post)
	 * 	secretkey	不能空	String	登录时候获取
	 *	spaceGuid	不能空	String	空间guid
	 *	name		不能空	String	应用名称
	 *	orgGuid		不能空	String	组织guid
	 *	command		可以为空	String	命令
	 *	appType	可以为空	String	应用类型
	 */
	final static String API_STARTAPP = SERVER_PATH + "/api/app/start.json";
	
	/*
	 *  停止应用(post)
	 * 	secretkey	不能空	String	登录时候获取
	 *	spaceGuid	不能空	String	空间guid
	 *	name		不能空	String	应用名称
	 *	orgGuid		不能空	String	组织guid
	 */
	final static String API_STOPAPP = SERVER_PATH + "/api/app/stop.json";
	
	/*
	 * 	应用实例列表(post)
	 * 	secretkey	不能空	String	登录时候获取
	 *	spaceGuid	不能空	String	空间guid
	 *	name		不能空	String	应用名称
	 *	orgGuid		不能空	String	组织guid
	 */
	final static String API_GETINSTANCE = SERVER_PATH + "/api/app/instances.json";
	
	/*
	 * 	应用实例调整(post)
	 * 	secretkey	不能空	String	登录时候获取
	 *	spaceGuid	不能空	String	空间guid
	 *	name		不能空	String	应用名称
	 *	memory		不能空	int	每个实例的内存
	 *	instances	不能空	int	实例数量
	 *	orgGuid		不能空	String	组织guid
	 */
	final static String API_MODIFYAPP = SERVER_PATH + "/api/app/scale.json";
	/*
	 * 所有服务实例信息(post)
	 * secretkey	不能空	String	登录时候获取
	 * spaceGuid	不能空	String	空间guid
	 */
	final static String API_SERVICELIST = SERVER_PATH + "/api/service/instances.json";
	/*
	 * 删除一个服务实例
	 *  secretkey	不能空	String	登录时候获取
	 *	spaceGuid	不能空	String	空间guid
	 *	name		不能空	String	服务名称
	 *	orgGuid		不能空	String	组织guid
	 */
	final static String API_DELETESVC = SERVER_PATH + "/api/service/delete.json";
	/*
	 * 获取消费记录
	 * secretkey	不能空	String	登录时候获取
	 */
	final static String API_GETPAYLIST = SERVER_PATH + "/api/pay/payList.json";
	/*
	 * 获取充值记录
	 * secretkey	不能空	String	登录时候获取
	 */
	final static String API_GETBALANCEDETAIL = SERVER_PATH + "/api/pay/getUserBalanceDetails.json";
	/*
	 * 获取访问统计量
	 * secretkey	不能空	String	登录时候获取
		name	不能空	String	应用名
		spaceGuid	不能空	String	空间的guid
		hours	可以空	int	小时数(一小时传1，一天传24，一周传168，一月传720)
		pointNum	可以空	Int	返回数据的点数，可选，默认10
	 */
	final static String API_REQUESTCOUNTCHART =SERVER_PATH + "/api/app/requestCountChart.json";
	
	
	final static String API_MESSAGELIST = "/user/getRemindsList.json";
	final static String API_MESSAGEREAD = "/user/setRemindHaveRead.json";
	final static String API_PUSH_SETTAG = "/api/devices/addDevice.json";
	final static String PUSH_SERVICEKEY = "1bc732ed0e04401898391fc851a363d3";
}
