package com.droid.googleplay.factory;

import com.droid.googleplay.base.BaseFragment;
import com.droid.googleplay.fragment.AppFragment;
import com.droid.googleplay.fragment.CategoryFragment;
import com.droid.googleplay.fragment.GameFragment;
import com.droid.googleplay.fragment.HomeFragment;
import com.droid.googleplay.fragment.HotFragment;
import com.droid.googleplay.fragment.RecommendFragment;
import com.droid.googleplay.fragment.SubjectFragment;

import android.support.v4.app.Fragment;
import android.support.v4.util.SparseArrayCompat;

/**
 * @author yidong
 * @date 2016年9月16日 上午10:55:53
 * @version 1.0
 * @Description
 */
public class FragmentFactory
{
	/**
	 * <string-array name="main_title"> <item>首页</item> <item>应用</item>
	 * <item>游戏</item> <item>专题</item> <item>推荐</item> <item>分类</item>
	 * <item>排行</item> </string-array>
	 * 
	 * @param position
	 * @return
	 */
	private static final int	FRAGMENT_HOME		= 0;
	private static final int	FRAGMENT_APP		= 1;
	private static final int	FRAGMENT_GAME		= 2;
	private static final int	FRAGMENT_SUBJECT	= 3;
	private static final int	FRAGMENT_RECOMMEND	= 4;
	private static final int	FRAGMENT_CATEGORY	= 5;
	private static final int	FRAGMENT_HOT		= 6;
	private static SparseArrayCompat<BaseFragment> cacheFragment = new SparseArrayCompat<BaseFragment>();

	public static BaseFragment getFragment(int position)
	{
		BaseFragment fragment = null;
		
		BaseFragment tempFragment = cacheFragment.get(position);
		if(tempFragment!=null)
		{
			fragment = tempFragment;
			return fragment;
		}
		
		switch (position)
		{
			case FRAGMENT_HOME:
				fragment = new HomeFragment();
				break;
			case FRAGMENT_APP:
				fragment = new AppFragment();
				break;
			case FRAGMENT_GAME:
				fragment = new GameFragment();
				break;
			case FRAGMENT_SUBJECT:
				fragment = new SubjectFragment();
				break;
			case FRAGMENT_RECOMMEND:
				fragment = new RecommendFragment();
				break;
			case FRAGMENT_CATEGORY:
				fragment = new CategoryFragment();
				break;
			case FRAGMENT_HOT:
				fragment = new HotFragment();
				break;
		}
		cacheFragment.put(position, fragment);		

		return fragment;
	}
}
