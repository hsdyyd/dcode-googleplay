package com.droid.googleplay.holder;

import com.droid.googleplay.R;
import com.droid.googleplay.base.BaseHolder;
import com.droid.googleplay.bean.AppInfoBean;
import com.droid.googleplay.constant.Constants;
import com.droid.googleplay.utils.BitmapHelp;
import com.droid.googleplay.utils.StringUtils;
import com.droid.googleplay.utils.UIUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

/**
* @author yidong
* @date 2016年11月23日 下午2:52:23
* @version 1.0
* @Description 
*/
public class AppDetailInfoHolder extends BaseHolder<AppInfoBean>
{
	@ViewInject(R.id.app_detail_info_iv_icon)
	ImageView	mIvIcon;

	@ViewInject(R.id.app_detail_info_rb_star)
	RatingBar	mRbStar;

	@ViewInject(R.id.app_detail_info_tv_downloadnum)
	TextView	mTvDownLoadNum;

	@ViewInject(R.id.app_detail_info_tv_name)
	TextView	mTvName;

	@ViewInject(R.id.app_detail_info_tv_time)
	TextView	mTvTime;

	@ViewInject(R.id.app_detail_info_tv_version)
	TextView	mTvVersion;

	@ViewInject(R.id.app_detail_info_tv_size)
	TextView	mTvSize;
	
	@Override
	public View initHolderView()
	{
		View view = View.inflate(UIUtils.getContext(),R.layout.item_app_detail_info, null);
		ViewUtils.inject(this,view);
		return view;
	}

	@Override
	public void refreshHolderView(AppInfoBean data)
	{
		String date = UIUtils.getString(R.string.detail_date, data.date);
		String downloadNum = UIUtils.getString(R.string.detail_downloadnum, data.downloadNum);
		String size = UIUtils.getString(R.string.detail_size, StringUtils.formatFileSize(data.size));
		String version = UIUtils.getString(R.string.detail_version, data.version);

		mTvName.setText(data.name);
		mTvDownLoadNum.setText(downloadNum);
		mTvTime.setText(date);
		mTvVersion.setText(version);
		mTvSize.setText(size);
		
		mIvIcon.setImageResource(R.drawable.ic_default);
		BitmapHelp.display(mIvIcon, Constants.URLS.IMAGEBASEURL+data.iconUrl);
		
		mRbStar.setRating(data.stars);
		
	}

}
