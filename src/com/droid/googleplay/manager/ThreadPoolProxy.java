/**
 * @auth yidong
 * @date 2016年9月17日下午4:53:46
 * @version v1.0
 * @desc
 *
 */

package com.droid.googleplay.manager;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPoolProxy
{
	ThreadPoolExecutor mExecutor;
	int mCorePoolSize;
    int mMaximumPoolSize;
    long mKeepAliveTime;
	
	public ThreadPoolProxy(int corePoolSize, int maximumPoolSize, long keepAliveTime)
	{
		super();
		mCorePoolSize = corePoolSize;
		mMaximumPoolSize = maximumPoolSize;
		mKeepAliveTime = keepAliveTime;
	}

	private ThreadPoolExecutor initThreadPoolExecutor()
	{
		if(mExecutor==null)
		{
			synchronized (ThreadPoolProxy.class)
			{
				if(mExecutor==null)
				{
					TimeUnit unit = TimeUnit.MILLISECONDS;
					BlockingQueue<Runnable> workQueue = new LinkedBlockingQueue<Runnable>();
					ThreadFactory threadFactory = Executors.defaultThreadFactory();
					RejectedExecutionHandler handler = new ThreadPoolExecutor.AbortPolicy();
					mExecutor = new ThreadPoolExecutor(mCorePoolSize, 
							mMaximumPoolSize, 
							mKeepAliveTime, 
							unit, 
							workQueue, 
							threadFactory, 
							handler);
				}
			}
		}
		return mExecutor;
	}
	
	public void execute(Runnable task)
	{
		initThreadPoolExecutor();
		mExecutor.execute(task);
	}
	
	public void submit(Runnable task)
	{
		initThreadPoolExecutor();
		mExecutor.submit(task);
	}
	
	public void removeTask(Runnable task)
	{
		initThreadPoolExecutor();
		mExecutor.remove(task);
	}
}
