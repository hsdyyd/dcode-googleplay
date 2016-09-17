/** 
 * @author:	yidong  
 * @Date    2016年9月12日 下午3:20:51 
 * @version v1.0
 * @since   JDK 1.7  
 * @des 定义一个全局的盒子,里面的对象，属性，方法都是全局可以调用 
 */

package com.droid.googleplay.base;

import android.app.Application;
import android.content.Context;
import android.os.Handler;

public class BaseApplication extends Application
{
	private static Context mContext;
	private static Thread mMainThread;
	private static long mMainThreadId;
	private static Handler mHandler;

	@Override
	public void onCreate()
	{
		mContext = getApplicationContext();

		mMainThread = Thread.currentThread();

		mMainThreadId = android.os.Process.myTid();
		
		mHandler = new Handler();

		super.onCreate();
	}

	public static Context getContext()
	{
		return mContext;
	}

	public static Thread getMainThread()
	{
		return mMainThread;
	}

	public static long getMainThreadId()
	{
		return mMainThreadId;
	}

	public static Handler getmHandler()
	{
		return mHandler;
	}
	
}
