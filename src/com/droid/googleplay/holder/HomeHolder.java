/**
 * @auth yidong
 * @date 2016年9月17日下午8:49:45
 * @version v1.0
 * @desc
 *
 */

package com.droid.googleplay.holder;

import com.droid.googleplay.R;
import com.droid.googleplay.base.BaseHolder;
import com.droid.googleplay.bean.AppInfoBean;
import com.droid.googleplay.constant.Constants;
import com.droid.googleplay.utils.BitmapHelp;
import com.droid.googleplay.utils.StringUtils;
import com.droid.googleplay.utils.UIUtils;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

public class HomeHolder extends BaseHolder<AppInfoBean>
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
	
	public View initHolderView()
	{
		View view = View.inflate(UIUtils.getContext(), R.layout.item_app_infor, null);

		ViewUtils.inject(this, view);
		
		return view;
	}
	
	public void refreshHolderView(AppInfoBean data)
	{
		mIvIcon.setImageResource(R.drawable.ic_default);
//		BitmapUtils bitmapUtils = new BitmapUtils(UIUtils.getContext());
		String uri = Constants.URLS.IMAGEBASEURL+data.iconUrl;
//		bitmapUtils.display(mIvIcon, uri);
		BitmapHelp.display(mIvIcon, uri);
		
		mTvTitle.setText(data.name);
		mRbStars.setRating(data.stars);
		mTvSize.setText(StringUtils.formatFileSize(data.size));
		mTvDes.setText(data.des);
	}
}
