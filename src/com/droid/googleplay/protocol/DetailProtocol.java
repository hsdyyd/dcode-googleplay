package com.droid.googleplay.protocol;

import java.util.HashMap;
import java.util.Map;

import com.droid.googleplay.base.BaseProtocol;
import com.droid.googleplay.bean.AppInfoBean;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import android.util.Log;

/**
* @author yidong
* @date 2016年11月23日 上午9:18:23
* @version 1.0
* @Description 
*/
public class DetailProtocol extends BaseProtocol<AppInfoBean>
{
	private String mPackageName;
	
	public DetailProtocol(String mPackageName)
	{
		this.mPackageName = mPackageName;
	}
	
	@Override
	public String getIntefaceKey()
	{
		return "detail";
	}

	@Override
	public AppInfoBean parseJson(String readString)
	{
		Gson gson = new Gson();
		return gson.fromJson(readString, new TypeToken<AppInfoBean>(){}.getType());
	}
	
	@Override
	public Map<String, String> getExtraParams()
	{
		Log.i("LOG", "********************>"+mPackageName);
		Map<String,String> params = new HashMap<String,String>();
		params.put("packageName", mPackageName);
		return params;
	}

}
