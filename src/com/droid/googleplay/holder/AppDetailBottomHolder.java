package com.droid.googleplay.holder;

import java.io.File;

import com.droid.googleplay.R;
import com.droid.googleplay.base.BaseHolder;
import com.droid.googleplay.bean.AppInfoBean;
import com.droid.googleplay.manager.DownloadInfo;
import com.droid.googleplay.manager.DownloadManager;
import com.droid.googleplay.manager.DownloadManager.DownLoadObserver;
import com.droid.googleplay.utils.CommonUtils;
import com.droid.googleplay.utils.PrintDownLoadInfo;
import com.droid.googleplay.utils.UIUtils;
import com.droid.googleplay.view.ProgressButton;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

/**
* @author yidong
* @date 2016年11月23日 下午2:52:23
* @version 1.0
* @Description 
*/
public class AppDetailBottomHolder extends BaseHolder<AppInfoBean> implements OnClickListener,DownLoadObserver
{
	@ViewInject(R.id.app_detail_download_btn_share)
	Button				mBtnShare;
	@ViewInject(R.id.app_detail_download_btn_favo)
	Button				mBtnFavo;
	@ViewInject(R.id.app_detail_download_btn_download)
	ProgressButton		mProgressButton;
	private AppInfoBean mData;
	@Override
	public View initHolderView()
	{
		View view = View.inflate(UIUtils.getContext(), R.layout.item_app_detail_bottom, null);
		ViewUtils.inject(this, view);
		
		mBtnShare.setOnClickListener(this);
		mBtnFavo.setOnClickListener(this);
		mProgressButton.setOnClickListener(this);
		return view;
	}

	@Override
	public void refreshHolderView(AppInfoBean data)
	{
		mData = data;
		DownloadInfo info = DownloadManager.getInstance().getDownLoadInfo(data);
		
		refreshProgressBtnUI(info);
	}

	private void refreshProgressBtnUI(DownloadInfo info)
	{
		mProgressButton.setBackgroundResource(R.drawable.selector_app_detail_bottom_normal);
		switch (info.state)
		{
			/**
			 状态(编程记录)  	|  给用户的提示(ui展现)   
			----------------|----------------------
			未下载			|下载				    
			下载中			|显示进度条		   		 
			暂停下载			|继续下载				   
			等待下载			|等待中...				 
			下载失败 			|重试					 
			下载完成 			|安装					 
			已安装 			|打开					 
			 */
			case DownloadManager.STATE_UNDOWNLOAD:// 未下载
				mProgressButton.setText("下载");
				break;
			case DownloadManager.STATE_DOWNLOADING:// 下载中
				mProgressButton.setBackgroundResource(R.drawable.selector_app_detail_bottom_downloading);
				mProgressButton.setProgressEnable(true);
				mProgressButton.setMax(info.max);
				mProgressButton.setProgress(info.curProgress);
				int progress = (int)(info.curProgress*100.f/info.max+0.5f);
				
				mProgressButton.setText(progress+"%");
				break;
			case DownloadManager.STATE_PAUSEDOWNLOAD:// 暂停下载
				mProgressButton.setText("继续下载");
				break;
			case DownloadManager.STATE_WAITINGDOWNLOAD:// 等待下载
				mProgressButton.setText("等待中...");
				break;
			case DownloadManager.STATE_DOWNLOADFAILED:// 下载失败
				mProgressButton.setText("重试");
				break;
			case DownloadManager.STATE_DOWNLOADED:// 下载完成
				mProgressButton.setProgressEnable(false);
				mProgressButton.setText("安装");
				break;
			case DownloadManager.STATE_INSTALLED:// 已安装
				mProgressButton.setText("打开");
				break;
			default:
				break;
			}
	}

	@Override
	public void onClick(View v)
	{
		switch (v.getId())
		{
			case R.id.app_detail_download_btn_download:
				DownloadInfo info = DownloadManager.getInstance().getDownLoadInfo(mData);
				
				switch (info.state)
				{
					/**
						状态(编程记录)     | 用户行为(触发操作) 
						----------------| -----------------
						未下载			| 去下载
						下载中			| 暂停下载
						暂停下载			| 断点继续下载
						等待下载			| 取消下载
						下载失败 			| 重试下载
						下载完成 			| 安装应用
						已安装 			| 打开应用				 
					 */
					case DownloadManager.STATE_UNDOWNLOAD:// 未下载
						doDownLoad(info);
						break;
					case DownloadManager.STATE_DOWNLOADING:// 下载中
						pauseDownLoad(info);
						break;
					case DownloadManager.STATE_PAUSEDOWNLOAD:// 暂停下载
						doDownLoad(info);
						break;
					case DownloadManager.STATE_WAITINGDOWNLOAD:// 等待下载
						cancelDownLoad(info);
						break;
					case DownloadManager.STATE_DOWNLOADFAILED:// 下载失败
						doDownLoad(info);
						break;
					case DownloadManager.STATE_DOWNLOADED:// 下载完成
						installApk(info);
						break;
					case DownloadManager.STATE_INSTALLED:// 已安装
						openApk(info);
						break;
					default:
						break;
					}
				
				break;
			case R.id.app_detail_download_btn_share:
				
				break;
			case R.id.app_detail_download_btn_favo:
				
				break;
			default:
				break;
		}
	}
	
	/**
	 * 打开应用
	 * @param info
	 */
	private void openApk(DownloadInfo info) {
		CommonUtils.openApp(UIUtils.getContext(), info.packageName);
	}

	/**
	 * 安装应用
	 * @param info
	 */
	private void installApk(DownloadInfo info) {
		File apkFile = new File(info.savePath);
		CommonUtils.installApp(UIUtils.getContext(), apkFile);
	}

	/**
	 * 取消下载
	 * @param info
	 */
	private void cancelDownLoad(DownloadInfo info) {
		System.out.println("=========================cancel");
		DownloadManager.getInstance().cancel(info);

	}

	/**
	 * 暂停下载
	 * @param info
	 */
	private void pauseDownLoad(DownloadInfo info) {
		DownloadManager.getInstance().pause(info);
	}

	/**
	 * 开始下载
	 * @param info
	 */
	private void doDownLoad(DownloadInfo info) {
		/*=============== 根据不同的状态触发不同的操作 ===============*/
		/*// 下载apk放置的目录
		String dir = FileUtils.getDir("download");// sdcard/android/data/包名/download
		File file = new File(dir, mData.packageName + ".apk");// sdcard/android/data/包名/download/com.itheima.www.apk
		// 保存路径
		String savePath = file.getAbsolutePath();// sdcard/android/data/包名/download/com.itheima.www.apk

		DownLoadInfo info = new DownLoadInfo();
		info.savePath = savePath;
		info.downloadUrl = mData.downloadUrl;
		info.packageName = mData.packageName;*/

		DownloadManager.getInstance().download(info);
	}

	@Override
	public void onDownLoadInfoChange(final DownloadInfo info)
	{
		if(info.packageName!=mData.packageName)
		{
			return;
		}
		
		PrintDownLoadInfo.printDownLoadInfo(info);
		
		UIUtils.postTaskSafely(new Runnable()
		{
			@Override
			public void run()
			{
				refreshProgressBtnUI(info);
			}
		});
	}

//	private void doDownLoad()
//	{
//		String dir = FileUtils.getDir("download");
//		File file = new File(dir,mData.packageName+".apk");
//		String savePath = file.getAbsolutePath();
//		
//		DownloadInfo info = new DownloadInfo();
//		info.downloadUrl = mData.downloadUrl;
//		info.savePath = savePath;
//		info.packageName = mData.packageName;
//		
//		DownloadManager.getInstance().download(info);
//		
//	}
	
	public void addObserverAndRefresh()
	{
		DownloadManager.getInstance().addObserver(this);
		DownloadInfo info = DownloadManager.getInstance().getDownLoadInfo(mData);
		refreshProgressBtnUI(info);
	}

}
