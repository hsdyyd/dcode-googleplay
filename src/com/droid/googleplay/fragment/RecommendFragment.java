package com.droid.googleplay.fragment;

import java.util.List;
import java.util.Random;

import com.droid.googleplay.base.BaseFragment;
import com.droid.googleplay.base.LoadingPager.LoadResult;
import com.droid.googleplay.protocol.RecommendProtocol;
import com.droid.googleplay.utils.UIUtils;
import com.droid.googleplay.view.flyinout.StellarMap;

import android.graphics.Color;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
* @author yidong
* @date 2016年9月16日 上午11:04:30
* @version 1.0
* @Description 
*/
public class RecommendFragment extends BaseFragment
{

	private List<String> mLoadData;

	@Override
	public LoadResult initData()
	{
		RecommendProtocol protocol = new RecommendProtocol();
		
		try
		{
			mLoadData = protocol.loadData(0);
			return checkState(mLoadData);
		} catch (Exception e)
		{
			e.printStackTrace();
			return LoadResult.ERROR;
		}
	}

	@Override
	public View initSuccessView()
	{
		StellarMap stellar = new StellarMap(UIUtils.getContext());
		stellar.setAdapter(new RecommendAdapter());
		
		// 设置第一页可见 
		stellar.setGroup(0, true);
		// 设置屏幕分为多少格子
		stellar.setRegularity(15, 20);
		int padding = UIUtils.dip2Px(5);
		stellar.setPadding(padding, padding, padding, padding);
		return stellar;
	}
	
	class RecommendAdapter implements StellarMap.Adapter
	{
		private final int PAGE_SIZE = 15;
		@Override
		public int getGroupCount()
		{
			int groupCount = mLoadData.size()/PAGE_SIZE;
			if(mLoadData.size()%PAGE_SIZE>0)
			{
				groupCount++;
			}
			return groupCount;	
		}

		@Override
		public int getCount(int group)
		{
			if(group==getGroupCount()-1)
			{
				if(mLoadData.size()%PAGE_SIZE>0)
				{
					return mLoadData.size()%PAGE_SIZE;
				}
			}
			return PAGE_SIZE;
		}

		@Override
		public View getView(int group, int position, View convertView)
		{
			TextView tv = new TextView(UIUtils.getContext());
			
			int index = group*PAGE_SIZE+position;
			
			tv.setText(mLoadData.get(index));
			
			Random random = new Random();
			
			int size = random.nextInt(6)+15; // 15~21
			
			tv.setTextSize(size);
			
			int alpha = 255; // 30~210
			int red = random.nextInt(180)+30;
			int green = random.nextInt(180)+30;
			int blue = random.nextInt(180)+30;
			int argb = Color.argb(alpha,red,green,blue);
			
			tv.setTextColor(argb);
			
			return tv;
		}

		@Override
		public int getNextGroupOnPan(int group, float degree)
		{
			return 0;
		}

		@Override
		public int getNextGroupOnZoom(int group, boolean isZoomIn)
		{
			return 0;
		}
		
	}
}
