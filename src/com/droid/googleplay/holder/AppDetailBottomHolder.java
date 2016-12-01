package com.droid.googleplay.holder;

import java.io.File;

import com.droid.googleplay.R;
import com.droid.googleplay.base.BaseHolder;
import com.droid.googleplay.bean.AppInfoBean;
import com.droid.googleplay.constant.Constants;
import com.droid.googleplay.factory.ThreadPoolFactory;
import com.droid.googleplay.manager.DownloadInfo;
import com.droid.googleplay.manager.DownloadManager;
import com.droid.googleplay.utils.FileUtils;
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
public class AppDetailBottomHolder extends BaseHolder<AppInfoBean> implements OnClickListener
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
		
	}

	@Override
	public void onClick(View v)
	{
		switch (v.getId())
		{
			case R.id.app_detail_download_btn_download:
				doDownLoad();
				break;
			case R.id.app_detail_download_btn_share:
				
				break;
			case R.id.app_detail_download_btn_favo:
				
				break;
			default:
				break;
		}
	}

	private void doDownLoad()
	{
		String dir = FileUtils.getDir("download");
		File file = new File(dir,mData.packageName+".apk");
		String savePath = file.getAbsolutePath();
		DownloadInfo info = new DownloadInfo();
		info.downloadUrl = mData.downloadUrl;
		info.savePath = savePath;
		
		DownloadManager.getInstance().download(info);
		
	}

}
