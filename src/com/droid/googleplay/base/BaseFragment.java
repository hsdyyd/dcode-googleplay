/**
 * @auth yidong
 * @date 2016年9月16日下午8:44:59
 * @version v1.0
 * @desc
 *
 */

package com.droid.googleplay.base;

import com.droid.googleplay.base.LoadingPager.LoadResult;
import com.droid.googleplay.utils.UIUtils;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public abstract class BaseFragment extends Fragment
{
	private LoadingPager mLoadingPager;

	public LoadingPager getLoadingPager()
	{
		return mLoadingPager;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState)
	{
		if (mLoadingPager == null)
		{
			mLoadingPager = new LoadingPager(UIUtils.getContext())
			{
				@Override
				public LoadResult initData()
				{
					return BaseFragment.this.initData();
				}

				@Override
				public View initSuccessView()
				{
					return BaseFragment.this.initSuccessView();
				}
			};
		}
		else
		{
			((ViewGroup)mLoadingPager.getParent()).removeView(mLoadingPager);
		}
		return mLoadingPager;
	}

	public abstract LoadResult initData();

	public abstract View initSuccessView();
}
