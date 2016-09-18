/**
 * @auth yidong
 * @date 2016年9月18日下午9:33:59
 * @version v1.0
 * @desc
 *
 */

package com.droid.googleplay.utils;

import com.lidroid.xutils.BitmapUtils;

import android.view.View;

public class BitmapHelp
{
	public static BitmapUtils bitmapUtils;
	static
	{
		bitmapUtils = new BitmapUtils(UIUtils.getContext());
	}
	
	public static <T extends View> void display(T container, String uri) 
	{
		bitmapUtils.display(container, uri);
	}
}
