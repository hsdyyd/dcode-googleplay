package com.droid.googleplay.fragment;

import java.util.List;
import java.util.Random;

import com.droid.googleplay.base.BaseFragment;
import com.droid.googleplay.base.LoadingPager.LoadResult;
import com.droid.googleplay.protocol.HotProtocol;
import com.droid.googleplay.utils.UIUtils;
import com.droid.googleplay.view.FlowLayout;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

/**
* @author yidong
* @date 2016年9月16日 上午11:04:30
* @version 1.0
* @Description 
*/
public class HotFragment extends BaseFragment
{

	private List<String> mData;

	@Override
	public LoadResult initData()
	{
		HotProtocol protocol = new HotProtocol();
		try
		{
			mData = protocol.loadData(0);
			return checkState(mData);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return LoadResult.ERROR;
		}
	}

	@Override
	public View initSuccessView()
	{
		ScrollView view = new ScrollView(UIUtils.getContext());
		
		FlowLayout flowLayout = new FlowLayout(UIUtils.getContext());
		
		for (String string : mData)
		{
			TextView tv = new TextView(UIUtils.getContext());
			tv.setText(string);
			tv.setTextColor(Color.WHITE);
			tv.setTextSize(16);
			int padding = UIUtils.dip2Px(5);
			tv.setPadding(padding, padding, padding, padding);
			tv.setGravity(Gravity.CENTER);
			
			GradientDrawable normalDrawable = new GradientDrawable();
			
			Random random = new Random();
			int alpha = 255;
			int red = random.nextInt(190)+30;  // 0~255   30~220
			int green = random.nextInt(190)+30;
			int blue = random.nextInt(190)+30;
			int color = Color.argb(alpha, red, green, blue);
			
			normalDrawable.setColor(color);
			normalDrawable.setCornerRadius(UIUtils.dip2Px(6));
			
			GradientDrawable pressedDrawable = new GradientDrawable();
			pressedDrawable.setColor(Color.DKGRAY);
			pressedDrawable.setCornerRadius(UIUtils.dip2Px(6));
			
			StateListDrawable stateListDrawable = new StateListDrawable();
			stateListDrawable.addState(new int[]{android.R.attr.state_pressed}, pressedDrawable);
			stateListDrawable.addState(new int[]{},normalDrawable);
			
			tv.setBackgroundDrawable(stateListDrawable);
			tv.setClickable(true);
			
			flowLayout.addView(tv);
		}
		view.addView(flowLayout);
		
		return view;
	}
}
