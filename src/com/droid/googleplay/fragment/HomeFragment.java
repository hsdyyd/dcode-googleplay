package com.droid.googleplay.fragment;

import java.util.Random;

import com.droid.googleplay.base.BaseFragment;
import com.droid.googleplay.base.LoadingPager.LoadResult;
import com.droid.googleplay.utils.UIUtils;

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
public class HomeFragment extends BaseFragment
{

	@Override
	public LoadResult initData()
	{
		SystemClock.sleep(2000);
		
		LoadResult[] res = {LoadResult.EMPTY,LoadResult.ERROR,LoadResult.LOADING,LoadResult.SUCCESS};
		Random random = new Random();
		int index = random.nextInt(res.length);
		return res[index];
	}

	@Override
	public View initSuccessView()
	{
		TextView tv = new TextView(UIUtils.getContext());
		tv.setText(this.getClass().getSimpleName());
		return tv;
	}
}
