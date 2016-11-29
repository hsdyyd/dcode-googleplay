package com.droid.googleplay.manager;

import com.droid.googleplay.bean.AppInfoBean;

/**
* @author yidong
* @date 2016年11月29日 下午4:09:25
* @version 1.0
* @Description 
*/
public class DownloadManager
{
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
	
	

}
