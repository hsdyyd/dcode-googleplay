/** 
 * @author:	yidong  
 * @Date    2016年9月12日 下午3:32:56 
 * @version v1.0
 * @since   JDK 1.7   
 * @des 
 */  

package com.droid.googleplay.utils;

import com.droid.googleplay.base.BaseApplication;

import android.content.Context;
import android.content.res.Resources;

public class UIUtils
{
	public static Context getContext()
	{
		return BaseApplication.getContext();
	}
	
	public static Resources getResources()
	{
		return getContext().getResources();
	}
	
	public static String getString(int resId)
	{
		return getResources().getString(resId);
	}
	
	public static String[] getStringArray(int resId)
	{
		return getResources().getStringArray(resId);
	}
	
	public static int getColor(int colorId)
	{
		return getResources().getColor(colorId);
	}
	
	public static String getPackageName()
	{
		return getContext().getPackageName();
	}
}
