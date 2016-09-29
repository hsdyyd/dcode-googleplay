/**
 * @auth yidong
 * @date 2016年9月25日上午10:37:47
 * @version v1.0
 * @desc
 *
 */

package com.droid.googleplay.protocol;

import com.droid.googleplay.base.BaseProtocol;
import com.droid.googleplay.bean.HomeBean;
import com.google.gson.Gson;

public class HomeProtocol extends BaseProtocol<HomeBean>
{

	@Override
	public String getIntefaceKey()
	{
		return "home";
	}

	@Override
	public HomeBean parseJson(String readString)
	{
		Gson gson = new Gson();
		return gson.fromJson(readString, HomeBean.class);
	}
	
}
