package com.droid.googleplay.holder;

import com.droid.googleplay.R;
import com.droid.googleplay.base.BaseHolder;
import com.droid.googleplay.bean.SubjectInfoBean;
import com.droid.googleplay.constant.Constants;
import com.droid.googleplay.utils.BitmapHelp;
import com.droid.googleplay.utils.UIUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
* @author yidong
* @date 2016年10月20日 下午12:30:48
* @version 1.0
* @Description 
*/
public class SubjectHolder extends BaseHolder<SubjectInfoBean>
{
	@ViewInject(R.id.item_subject_iv_icon)
	ImageView iv;
	@ViewInject(R.id.item_subject_tv_title)
	TextView tv;
	
	@Override
	public View initHolderView()
	{
		View view = View.inflate(UIUtils.getContext(), R.layout.item_subject,null);
		ViewUtils.inject(this, view);
		return view;
	}

	@Override
	public void refreshHolderView(SubjectInfoBean data)
	{
		tv.setText(data.des);
		iv.setImageResource(R.drawable.ic_default);
		BitmapHelp.display(iv, Constants.URLS.IMAGEBASEURL+data.url);
	}
}
