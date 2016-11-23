/**
 * @auth yidong
 * @date 2016年9月25日上午10:57:53
 * @version v1.0
 * @desc
 *
 */

package com.droid.googleplay.base;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Map;

import com.droid.googleplay.constant.Constants;
import com.droid.googleplay.utils.FileUtils;
import com.droid.googleplay.utils.IOUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseStream;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;

public abstract class BaseProtocol<T>
{
	public T loadData(int index) throws Exception
	{
		T loaclBean = getDataFromLocal(index);

		if (loaclBean != null)
		{
			return loaclBean;
		}

		String readString = getDataFromNet(index);

		T homeBean = parseJson(readString);
		return homeBean;
	}

	private T getDataFromLocal(int index)
	{
		File cacheFile = getCacheFile(index);

		if (cacheFile.exists())
		{
			BufferedReader reader = null;
			try
			{
				reader = new BufferedReader(new FileReader(cacheFile));
				String timeMillis = reader.readLine();
				if (System.currentTimeMillis()
						- Long.parseLong(timeMillis) < Constants.TIMEOUT)
				{
					String jsonString = reader.readLine();
					return parseJson(jsonString);
				}
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
			finally
			{
				IOUtils.close(reader);
			}
		}

		return null;
	}

	private File getCacheFile(int index)
	{
		String localPath = FileUtils.getDir("json");
		String name = getIntefaceKey() + "." + index;
		File cacheFile = new File(localPath, name);
		return cacheFile;
	}
	
	public Map<String,String> getExtraParams()
	{
		return null;
	}

	private String getDataFromNet(int index) throws Exception
	{
		HttpUtils httpUtils = new HttpUtils();
		// http://localhost:8080/GooglePlayServer/home?index=0
		String url = Constants.URLS.BASEURL + getIntefaceKey();

		RequestParams params = new RequestParams();
		// 处理额外参数
		if(getExtraParams()==null){
			params.addQueryStringParameter("index", index + "");
		}else{
			for (Map.Entry<String, String> info : getExtraParams().entrySet())
			{
				String name = info.getKey();
				String value = info.getValue();
				params.addQueryStringParameter(name,value);
			}
		}
		ResponseStream responseStream = httpUtils.sendSync(HttpMethod.GET, url, params);
		String readString = responseStream.readString();
		
		System.out.println("write------------>"+getCacheFile(index).getAbsolutePath()+"-----------");
		BufferedWriter writer = null;
		try
		{
			writer = new BufferedWriter(new FileWriter(getCacheFile(index)));
			writer.write(System.currentTimeMillis()+"");
			writer.newLine();
			writer.write(readString);
			writer.flush();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally {
			IOUtils.close(writer);
		}
		
		return readString;
	}

	public abstract String getIntefaceKey();

	public abstract T parseJson(String readString);
}
