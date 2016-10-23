/**
 * @auth yidong
 * @date 2016年10月23日上午8:38:41
 * @version v1.0
 * @desc
 *
 */

package com.droid.googleplay.protocol;

import java.util.List;

import com.droid.googleplay.base.BaseProtocol;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class HotProtocol extends BaseProtocol<List<String>>
{

	@Override
	public String getIntefaceKey()
	{
		return "hot";
	}

	@Override
	public List<String> parseJson(String readString)
	{
		Gson gson = new Gson();
		return gson.fromJson(readString, new TypeToken<List<String>>(){}.getType());
	}

}
