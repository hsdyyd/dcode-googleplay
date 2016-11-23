package com.droid.googleplay.holder;

import com.droid.googleplay.base.BaseHolder;
import com.droid.googleplay.bean.AppInfoBean;
import com.droid.googleplay.utils.UIUtils;

import android.R;
import android.view.View;
import android.widget.TextView;

/**
* @author yidong
* @date 2016年11月23日 下午2:52:23
* @version 1.0
* @Description 
*/
public class AppDetailDesHolder extends BaseHolder<AppInfoBean>
{

	@Override
	public View initHolderView()
	{
		TextView tv = new TextView(UIUtils.getContext());
		tv.setText(this.getClass().getSimpleName());
//		View.inflate(UIUtils.getContext(), R.layout.item_app_detail, root)
		
		return tv;
	}

	@Override
	public void refreshHolderView(AppInfoBean data)
	{
	}

}
