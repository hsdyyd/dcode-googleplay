/**
 * @auth yidong
 * @date 2016年9月17日下午8:49:45
 * @version v1.0
 * @desc
 *
 */

package com.droid.googleplay.holder;

import java.io.File;

import com.droid.googleplay.R;
import com.droid.googleplay.base.BaseHolder;
import com.droid.googleplay.bean.AppInfoBean;
import com.droid.googleplay.constant.Constants;
import com.droid.googleplay.manager.DownloadInfo;
import com.droid.googleplay.manager.DownloadManager;
import com.droid.googleplay.manager.DownloadManager.DownLoadObserver;
import com.droid.googleplay.utils.BitmapHelp;
import com.droid.googleplay.utils.CommonUtils;
import com.droid.googleplay.utils.PrintDownLoadInfo;
import com.droid.googleplay.utils.StringUtils;
import com.droid.googleplay.utils.UIUtils;
import com.droid.googleplay.view.CircleProgressView;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

public class AppItemHolder extends BaseHolder<AppInfoBean> implements OnClickListener,DownLoadObserver
{

	@ViewInject(R.id.item_appinfo_iv_icon)
	ImageView mIvIcon;
	@ViewInject(R.id.item_appinfo_tv_title)
	TextView mTvTitle;
	@ViewInject(R.id.item_appinfo_rb_stars)
	RatingBar mRbStars;
	@ViewInject(R.id.item_appinfo_tv_size)
	TextView mTvSize;
	@ViewInject(R.id.item_appinfo_tv_des)
	TextView mTvDes;
	@ViewInject(R.id.item_appinfo_circleprogressview)
	CircleProgressView mCircleProgressView;
	private AppInfoBean mData;

	public View initHolderView()
	{
		View view = View.inflate(UIUtils.getContext(), R.layout.item_app_infor, null);

		ViewUtils.inject(this, view);

		mCircleProgressView.setOnClickListener(this);

		return view;
	}

	public void refreshHolderView(AppInfoBean data)
	{
		// 清除复用convertView之后的progress效果
		mCircleProgressView.setProgress(0);
		
		mData = data;
		
		mTvTitle.setText(data.name);
		mTvSize.setText(StringUtils.formatFileSize(data.size));
		mTvDes.setText(data.des);

		mIvIcon.setImageResource(R.drawable.ic_default);
		// BitmapUtils bitmapUtils = new BitmapUtils(UIUtils.getContext());
		// bitmapUtils.display(mIvIcon, uri);
		String uri = Constants.URLS.IMAGEBASEURL + data.iconUrl;
		BitmapHelp.display(mIvIcon, uri);

		mRbStars.setRating(data.stars);

		/* =============== 根据不同的状态给用户提示 =============== */
		DownloadInfo info = DownloadManager.getInstance().getDownLoadInfo(data);
		refreshCircleProgressViewUI(info);
	}

	public void refreshCircleProgressViewUI(DownloadInfo info)
	{
		switch (info.state)
		{
			/**
			 * 状态(编程记录) | 给用户的提示(ui展现) ----------------|----------------------
			 * 未下载 |下载 下载中 |显示进度条 暂停下载 |继续下载 等待下载 |等待中... 下载失败 |重试 下载完成 |安装 已安装
			 * |打开
			 */
			case DownloadManager.STATE_UNDOWNLOAD:// 未下载
				mCircleProgressView.setNote("下载");
				mCircleProgressView.setIcon(R.drawable.ic_download);
				break;
			case DownloadManager.STATE_DOWNLOADING:// 下载中
				mCircleProgressView.setProgressEnable(true);
				mCircleProgressView.setMax(info.max);
				mCircleProgressView.setProgress(info.curProgress);
				int progress = (int) (info.curProgress * 100.f / info.max + .5f);
				mCircleProgressView.setNote(progress + "%");
				mCircleProgressView.setIcon(R.drawable.ic_pause);
				break;
			case DownloadManager.STATE_PAUSEDOWNLOAD:// 暂停下载
				mCircleProgressView.setNote("继续下载");
				mCircleProgressView.setIcon(R.drawable.ic_resume);
				break;
			case DownloadManager.STATE_WAITINGDOWNLOAD:// 等待下载
				mCircleProgressView.setNote("等待中...");
				mCircleProgressView.setIcon(R.drawable.ic_pause);
				break;
			case DownloadManager.STATE_DOWNLOADFAILED:// 下载失败
				mCircleProgressView.setNote("重试");
				mCircleProgressView.setIcon(R.drawable.ic_redownload);
				break;
			case DownloadManager.STATE_DOWNLOADED:// 下载完成
				mCircleProgressView.setProgressEnable(false);
				mCircleProgressView.setNote("安装");
				mCircleProgressView.setIcon(R.drawable.ic_install);
				break;
			case DownloadManager.STATE_INSTALLED:// 已安装
				mCircleProgressView.setNote("打开");
				mCircleProgressView.setIcon(R.drawable.ic_install);
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
			case R.id.item_appinfo_circleprogressview:

				DownloadInfo info = DownloadManager.getInstance().getDownLoadInfo(mData);

				switch (info.state)
				{
					/**
					 * 状态(编程记录) | 用户行为(触发操作) ----------------| -----------------
					 * 未下载 | 去下载 下载中 | 暂停下载 暂停下载 | 断点继续下载 等待下载 | 取消下载 下载失败 |
					 * 重试下载 下载完成 | 安装应用 已安装 | 打开应用
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

	/*=============== 收到数据改变,更新ui ===============*/
	@Override
	public void onDownLoadInfoChange(final DownloadInfo info) {
		// 过滤DownLoadInfo
		if (!info.packageName.equals(mData.packageName)) {
			return;
		}
		PrintDownLoadInfo.printDownLoadInfo(info);
		UIUtils.postTaskSafely(new Runnable() {
			@Override
			public void run() {
				refreshCircleProgressViewUI(info);
			}
		});
	}
}
