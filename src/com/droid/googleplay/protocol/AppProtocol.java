/**
 * @auth yidong
 * @date 2016年10月1日下午6:18:51
 * @version v1.0
 * @desc
 *
 */

package com.droid.googleplay.protocol;

import java.util.List;

import com.droid.googleplay.base.BaseProtocol;
import com.droid.googleplay.bean.AppInfoBean;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class AppProtocol extends BaseProtocol<List<AppInfoBean>>
{

	@Override
	public String getIntefaceKey()
	{
		return "app";
	}

	@Override
	public List<AppInfoBean> parseJson(String readString)
	{
		Gson gson = new Gson();
		return gson.fromJson(readString, new TypeToken<List<AppInfoBean>>(){}.getType());
	}

}
