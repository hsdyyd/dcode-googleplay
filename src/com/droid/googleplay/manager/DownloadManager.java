package com.droid.googleplay.manager;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.sql.Savepoint;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.droid.googleplay.bean.AppInfoBean;
import com.droid.googleplay.constant.Constants;
import com.droid.googleplay.factory.ThreadPoolFactory;
import com.droid.googleplay.utils.CommonUtils;
import com.droid.googleplay.utils.FileUtils;
import com.droid.googleplay.utils.IOUtils;
import com.droid.googleplay.utils.UIUtils;
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
	
	private Map<String,DownloadInfo> mDownLoadInfoMaps = new HashMap<String,DownloadInfo>();
	
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

	public void download(DownloadInfo info)
	{
		mDownLoadInfoMaps.put(info.packageName, info);
		
		info.state = DownloadManager.STATE_UNDOWNLOAD;
		notifyObservers(info);
		
		info.state = DownloadManager.STATE_WAITINGDOWNLOAD;
		notifyObservers(info);
		
		DownLoadTask task = new DownLoadTask(info);
		info.task = task;
		ThreadPoolFactory.getDownloadPool().execute(task);
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
				mInfo.state = DownloadManager.STATE_DOWNLOADING;
				notifyObservers(mInfo);
				
				long initRange = 0;
				File saveApk = new File(mInfo.savePath);
				if(saveApk.exists())
				{
					initRange = saveApk.length();
				}
				mInfo.curProgress = initRange;
				String url = Constants.URLS.DOWNLOADURL;
				HttpUtils httpUtils = new HttpUtils();
				RequestParams params = new RequestParams();
				params.addQueryStringParameter("name", mInfo.downloadUrl);
				params.addQueryStringParameter("range", initRange + "");
				ResponseStream responseStream = httpUtils.sendSync(HttpMethod.GET, url, params);
				
				if(responseStream.getStatusCode()==200){
					InputStream inputStream = null;
					FileOutputStream out = null;
					boolean isPause = false;
					try
					{
						inputStream = responseStream.getBaseStream();
						File saveFile = new File(mInfo.savePath);
						out = new FileOutputStream(saveFile,true);
						
						byte[] buffer = new byte[1024];
						int len = -1;
						
						while((len=inputStream.read(buffer))!=-1){
							if(mInfo.state==STATE_PAUSEDOWNLOAD){
								isPause = true;
								break;
							}
							
							out.write(buffer, 0, len);
							mInfo.curProgress += len;
							
							mInfo.state = DownloadManager.STATE_DOWNLOADING;
							notifyObservers(mInfo);
						}
						
						if(isPause)
						{
							mInfo.state = STATE_PAUSEDOWNLOAD;
							notifyObservers(mInfo);
						}else{
							mInfo.state = DownloadManager.STATE_DOWNLOADED;
							notifyObservers(mInfo);
						}
						
					} 
					finally
					{
						IOUtils.close(out);
						IOUtils.close(inputStream);
					}
				}else{
					mInfo.state = DownloadManager.STATE_DOWNLOADFAILED;
					notifyObservers(mInfo);
				}
			} catch (Exception e)
			{
				e.printStackTrace();
				mInfo.state = DownloadManager.STATE_DOWNLOADFAILED;
				notifyObservers(mInfo);
			}
		}
	}
	
	

	public DownloadInfo getDownLoadInfo(AppInfoBean data)
	{
		
		if(CommonUtils.isInstalled(UIUtils.getContext(), data.packageName))
		{
			DownloadInfo infor = generateDownLoadInfo(data);
			infor.state = STATE_INSTALLED;
			return infor;
		}

		DownloadInfo infor = generateDownLoadInfo(data);
		File saveApk = new File(infor.savePath);
		if(saveApk.exists())
		{
			if(saveApk.length()==data.size)
			{
				infor.state = STATE_DOWNLOADED;
				return infor;
			}
		}
		
		DownloadInfo downloadInfo = mDownLoadInfoMaps.get(data.packageName);
		if(downloadInfo!=null)
		{
			return downloadInfo;
		}
		
		DownloadInfo tempInfor = generateDownLoadInfo(data);
		tempInfor.state = STATE_UNDOWNLOAD;
		
		return tempInfor;
	}

	private DownloadInfo generateDownLoadInfo(AppInfoBean data)
	{
		String dir = FileUtils.getDir("download");
		File file = new File(dir,data.packageName+".apk");
		String savePath = file.getAbsolutePath();
		DownloadInfo infor = new DownloadInfo();

		infor.downloadUrl = data.downloadUrl;
		infor.savePath = savePath;
		infor.packageName = data.packageName;
		infor.max = data.size;
		infor.curProgress = 0;
		return infor;
	}

	public void cancel(DownloadInfo info)
	{
		Runnable task = info.task;
		ThreadPoolFactory.getDownloadPool().removeTask(task);
		
		info.state = STATE_UNDOWNLOAD;
		notifyObservers(info);
	}

	public void pause(DownloadInfo info)
	{
		info.state = STATE_PAUSEDOWNLOAD;
		notifyObservers(info);
	}

	/*=============== 自定义观察者设计模式  begin ===============*/
	public interface DownLoadObserver {
		void onDownLoadInfoChange(DownloadInfo info);
	}

	List<DownLoadObserver>	downLoadObservers	= new LinkedList<DownLoadObserver>();

	/**添加观察者*/
	public void addObserver(DownLoadObserver observer) {
		if (observer == null) {
			throw new NullPointerException("observer == null");
		}
		synchronized (this) {
			if (!downLoadObservers.contains(observer))
				downLoadObservers.add(observer);
		}
	}

	/**删除观察者*/
	public synchronized void deleteObserver(DownLoadObserver observer) {
		downLoadObservers.remove(observer);
	}

	/**通知观察者数据改变*/
	public void notifyObservers(DownloadInfo info) {
		for (DownLoadObserver observer : downLoadObservers) {
			observer.onDownLoadInfoChange(info);
		}
	}

	/*=============== 自定义观察者设计模式  end ===============*/
}
