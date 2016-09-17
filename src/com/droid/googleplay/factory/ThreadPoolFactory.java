/**
 * @auth yidong
 * @date 2016年9月17日下午5:19:58
 * @version v1.0
 * @desc
 *
 */

package com.droid.googleplay.factory;

import com.droid.googleplay.manager.ThreadPoolProxy;

public class ThreadPoolFactory
{
	static ThreadPoolProxy mNormalPool;
	static ThreadPoolProxy mDownloadPool;
	
	public static ThreadPoolProxy getNormalPool()
	{
		if(mNormalPool==null)
		{
			synchronized (ThreadPoolProxy.class)
			{
				if(mNormalPool==null)
				{
					mNormalPool = new ThreadPoolProxy(5, 5, 3000);
				}
			}
		}
		return mNormalPool;
	}
	
	public static ThreadPoolProxy getDownloadPool()
	{
		if(mDownloadPool==null)
		{
			synchronized (ThreadPoolProxy.class)
			{
				if(mDownloadPool==null)
				{
					mDownloadPool = new ThreadPoolProxy(5, 5, 3000);
				}
			}
		}
		return mDownloadPool;
	}
}
