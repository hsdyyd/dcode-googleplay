package com.droid.googleplay.manager;
/**
* @author yidong
* @date 2016年12月1日 下午4:23:12
* @version 1.0
* @Description 
*/
public class DownloadInfo
{
	public String downloadUrl;
	public String savePath;
	public int state = DownloadManager.STATE_UNDOWNLOAD;
	public String packageName;
	public long max;
	public long curProgress;
	public Runnable task;
}
