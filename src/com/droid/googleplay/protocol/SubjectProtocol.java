package com.droid.googleplay.protocol;

import java.util.List;

import com.droid.googleplay.base.BaseProtocol;
import com.droid.googleplay.bean.SubjectInfoBean;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
* @author yidong
* @date 2016年10月20日 下午12:19:55
* @version 1.0
* @Description 
*/
public class SubjectProtocol extends BaseProtocol<List<SubjectInfoBean>>
{

	@Override
	public String getIntefaceKey()
	{
		return "subject";
	}

	@Override
	public List<SubjectInfoBean> parseJson(String readString)
	{
		Gson gson = new Gson();
		return gson.fromJson(readString, new TypeToken<List<SubjectInfoBean>>(){}.getType());
	}

}
