package com.droid.googleplay.protocol;

import java.util.List;

import com.droid.googleplay.base.BaseProtocol;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
* @author yidong
* @date 2016年11月21日 下午1:32:40
* @version 1.0
* @Description 
*/
public class RecommendProtocol extends BaseProtocol<List<String>>
{
    // http://localhost:8080/GooglePlayServer/recommend?index=0
	@Override
	public String getIntefaceKey()
	{
		return "recommend";
	}

	@Override
	public List<String> parseJson(String readString)
	{
		Gson gson = new Gson();
		return gson.fromJson(readString, new TypeToken<List<String>>(){}.getType());
	}

}
