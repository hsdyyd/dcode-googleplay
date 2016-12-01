package com.droid.googleplay.manager;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

import com.droid.googleplay.bean.AppInfoBean;
import com.droid.googleplay.constant.Constants;
import com.droid.googleplay.factory.ThreadPoolFactory;
import com.droid.googleplay.utils.IOUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseStream;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;

/**
* @author yidong
* @date 2016年11月29日 下午4:09:25
* @version 1.0
* @Description 
*/
public class DownloadManager
{
	public static final int	STATE_UNDOWNLOAD		= 0;									// 未下载
	public static final int	STATE_DOWNLOADING		= 1;									// 下载中
	public static final int	STATE_PAUSEDOWNLOAD		= 2;									// 暂停下载
	public static final int	STATE_WAITINGDOWNLOAD	= 3;									// 等待下载
	public static final int	STATE_DOWNLOADFAILED	= 4;									// 下载失败
	public static final int	STATE_DOWNLOADED		= 5;									// 下载完成
	public static final int	STATE_INSTALLED			= 6;									// 已安装
	
	private static DownloadManager instance;
	
	private DownloadManager()
	{
		
	}
	
	public static DownloadManager getInstance()
	{
		if(instance==null)
		{
			synchronized (DownloadManager.class)
			{
				if(instance==null)
				{
					instance = new DownloadManager();
				}
			}
		}
		return instance;
	}

	public void getDownLoadInfo(AppInfoBean data)
	{
		
	}

	public void download(DownloadInfo info)
	{
		ThreadPoolFactory.getDownloadPool().execute(new DownLoadTask(info));
		
	}
	
	class DownLoadTask implements Runnable
	{
		private DownloadInfo mInfo;
		public DownLoadTask(DownloadInfo info)
		{
			mInfo = info;
		}
		@Override
		public void run()
		{
			try
			{
				String url = Constants.URLS.DOWNLOADURL;
				HttpUtils httpUtils = new HttpUtils();
				RequestParams params = new RequestParams();
				params.addQueryStringParameter("name", mInfo.downloadUrl);
				params.addQueryStringParameter("range", 0 + "");
				ResponseStream responseStream = httpUtils.sendSync(HttpMethod.GET, url, params);
				
				if(responseStream.getStatusCode()==200){
					InputStream inputStream = null;
					FileOutputStream out = null;
					try
					{
						inputStream = responseStream.getBaseStream();
						File saveFile = new File(mInfo.savePath);
						out = new FileOutputStream(saveFile);
						
						byte[] buffer = new byte[1024];
						int len = -1;
						
						while((len=inputStream.read(buffer))!=-1){
							out.write(buffer, 0, len);
						}
					} 
					finally
					{
						IOUtils.close(out);
						IOUtils.close(inputStream);
					}
				}
			} catch (Exception e)
			{
				e.printStackTrace();
			}
		}
	}

}
