/** 
 * @author:	yidong  
 * @Date    2016年9月12日 下午3:42:06 
 * @version v1.0
 * @since   JDK 1.7   
 * @des 
 */  

package com.droid.googleplay.constant;

import com.droid.googleplay.utils.LogUtils;

public class Constants
{
	public static final int DEBUGLEVEL = LogUtils.LEVEL_ALL;
	public static final int PAGESIZE = 20;
	public static final int TIMEOUT = 5 * 60 *1000;
	
	
	public static final class URLS
	{
		public static final String BASEURL = "http://10.0.3.2:8080/GooglePlayServer/";
		public static final String IMAGEBASEURL = BASEURL+"image?name=";
		public static final String DOWNLOADURL = BASEURL+"download";
		
	}
}
