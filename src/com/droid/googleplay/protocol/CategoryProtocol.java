/**
 * @auth yidong
 * @date 2016年10月23日下午4:42:15
 * @version v1.0
 * @desc
 *
 */

package com.droid.googleplay.protocol;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.droid.googleplay.base.BaseProtocol;
import com.droid.googleplay.bean.CategoryInfoBean;

public class CategoryProtocol extends BaseProtocol<List<CategoryInfoBean>>
{

	@Override
	public String getIntefaceKey()
	{
		return "category";
	}

	@Override
	public List<CategoryInfoBean> parseJson(String readString)
	{
		List<CategoryInfoBean> categoryInfoBeans = new ArrayList<CategoryInfoBean>();
		try
		{
			JSONArray rootJsonArray = new JSONArray(readString);
			for(int i=0;i<rootJsonArray.length();i++)
			{
				JSONObject jsonObject = rootJsonArray.getJSONObject(i);
				
				String title = jsonObject.getString("title");
				CategoryInfoBean titleBean = new CategoryInfoBean();
				titleBean.title = title;
				titleBean.isTitle = true;
				
				categoryInfoBeans.add(titleBean);
				
				JSONArray infosJsonArray = jsonObject.getJSONArray("infos");
				for(int j=0;j<infosJsonArray.length();j++)
				{
					JSONObject infoBeanJsonObject = infosJsonArray.getJSONObject(j);
					String name1 = infoBeanJsonObject.getString("name1");
					String name2 = infoBeanJsonObject.getString("name2");
					String name3 = infoBeanJsonObject.getString("name3");
					
					String url1 = infoBeanJsonObject.getString("url1");
					String url2 = infoBeanJsonObject.getString("url2");
					String url3 = infoBeanJsonObject.getString("url3");
					CategoryInfoBean inforBean = new CategoryInfoBean();
					inforBean.name1 = name1;
					inforBean.name2 = name2;
					inforBean.name3 = name3;
					
					inforBean.url1 = url1;
					inforBean.url2 = url2;
					inforBean.url3 = url3;
					categoryInfoBeans.add(inforBean);
				}
			}
			
			return categoryInfoBeans;
		}
		catch (JSONException e)
		{
			e.printStackTrace();
		}
		
		return null;
	}

}
