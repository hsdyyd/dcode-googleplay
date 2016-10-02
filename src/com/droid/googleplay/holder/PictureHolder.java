/**
 * @auth yidong
 * @date 2016年10月2日下午8:55:28
 * @version v1.0
 * @desc
 *
 */

package com.droid.googleplay.holder;

import java.util.List;

import com.droid.googleplay.R;
import com.droid.googleplay.base.BaseHolder;
import com.droid.googleplay.constant.Constants;
import com.droid.googleplay.utils.BitmapHelp;
import com.droid.googleplay.utils.UIUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;

public class PictureHolder extends BaseHolder<List<String>>
{
	@ViewInject(R.id.item_home_picture_pager)
	ViewPager mViewPager;
	@ViewInject(R.id.item_home_picture_container_indicator)
	LinearLayout mContainer;
	private List<String> mData;
	
	@Override
	public View initHolderView()
	{
		View view = View.inflate(UIUtils.getContext(), R.layout.item_home_picture, null);
		
		ViewUtils.inject(this, view);
		return view;
	}

	@Override
	public void refreshHolderView(List<String> data)
	{
		mData = data;
		mViewPager.setAdapter(new PictureAdapter());
		
		for(int i=0;i<mData.size();i++)
		{
			View view = new View(UIUtils.getContext());
			
			view.setBackgroundResource(R.drawable.indicator_normal);
			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(UIUtils.dip2Px(5), UIUtils.dip2Px(5));
			
			
			mContainer.addView(view);
		}
	}
	
	class PictureAdapter extends PagerAdapter
	{

		@Override
		public int getCount()
		{
			return mData.size();
		}

		@Override
		public boolean isViewFromObject(View view, Object object)
		{
			return view==object;
		}
		
		@Override
		public Object instantiateItem(ViewGroup container, int position)
		{
			ImageView iv = new ImageView(UIUtils.getContext());
			
			iv.setScaleType(ScaleType.FIT_XY);
			iv.setImageResource(R.drawable.ic_default);
			
			BitmapHelp.display(iv, Constants.URLS.IMAGEBASEURL+mData.get(position));
			
			container.addView(iv);
			
			return iv;
		}
		
		@Override
		public void destroyItem(ViewGroup container, int position, Object object)
		{
			container.removeView((View) object);
		}
		
	}

}
