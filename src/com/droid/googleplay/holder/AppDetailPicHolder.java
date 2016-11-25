package com.droid.googleplay.holder;

import java.util.List;

import com.droid.googleplay.R;
import com.droid.googleplay.base.BaseHolder;
import com.droid.googleplay.bean.AppInfoBean;
import com.droid.googleplay.constant.Constants;
import com.droid.googleplay.utils.BitmapHelp;
import com.droid.googleplay.utils.UIUtils;
import com.droid.googleplay.view.RatioLayout;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

/**
* @author yidong
* @date 2016年11月23日 下午2:52:23
* @version 1.0
* @Description 
*/
public class AppDetailPicHolder extends BaseHolder<AppInfoBean>
{
	@ViewInject(R.id.app_detail_pic_iv_container)
	LinearLayout mContainerPic;
	
	@Override
	public View initHolderView()
	{
		View view = View.inflate(UIUtils.getContext(), R.layout.item_app_detail_pic, null);
		ViewUtils.inject(this, view);
		return view;
	}

	@Override
	public void refreshHolderView(AppInfoBean data)
	{
		List<String> pics = data.screen;
		
		for(int i=0;i<pics.size();i++)
		{
			String picUrl = pics.get(i);
			
			ImageView ivPic = new ImageView(UIUtils.getContext());
			ivPic.setImageResource(R.drawable.ic_default);
			
			BitmapHelp.display(ivPic, Constants.URLS.IMAGEBASEURL+picUrl);
			
			// 每次只显示三张图片
			int widthPixels = UIUtils.getResources().getDisplayMetrics().widthPixels;
			widthPixels = widthPixels - mContainerPic.getPaddingLeft() - mContainerPic.getPaddingRight();
			int childWidth = widthPixels/5;
			
			RatioLayout rl = new RatioLayout(UIUtils.getContext());
			rl.setPicRation(150/250);
			rl.setRelative(RatioLayout.RELATIVE_WIDTH);
			
			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(childWidth, LayoutParams.WRAP_CONTENT);
			rl.addView(ivPic);
			
			if(i!=0){
				params.leftMargin = UIUtils.dip2Px(5);
			}
			
			mContainerPic.addView(rl, params);
			
		}
		
	}

}
