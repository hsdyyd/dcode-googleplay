package com.droid.googleplay.activity;

import com.droid.googleplay.base.LoadingPager;
import com.droid.googleplay.base.LoadingPager.LoadResult;
import com.droid.googleplay.bean.AppInfoBean;
import com.droid.googleplay.protocol.DetailProtocol;
import com.droid.googleplay.utils.UIUtils;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

/**
* @author yidong
* @date 2016年11月22日 上午10:36:18
* @version 1.0
* @Description 
*/
public class DetailActivity extends Activity
{
	private String mPackageName;
	private AppInfoBean mData;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		
		init();
		
		LoadingPager loadingPager = new LoadingPager(UIUtils.getContext()){

			@Override
			public LoadResult initData()
			{
				return onInitData();
			}

			@Override
			public View initSuccessView()
			{
				return onInitSuccessView();
			}
			
			
		};
		
		loadingPager.loadData();
		
		
		setContentView(loadingPager);
		
	}

	private void init()
	{
		mPackageName = getIntent().getStringExtra("packageName");
	}
	
	private LoadResult onInitData()
	{
//		SystemClock.sleep(2000);
		DetailProtocol protocol = new DetailProtocol(mPackageName);
		try
		{
			mData = protocol.loadData(0);
			if(mData==null)
			{
				return LoadResult.ERROR;
			}
			
			return LoadResult.SUCCESS;
		} catch (Exception e)
		{
			return LoadResult.ERROR;
		}
		
	}


	private View onInitSuccessView()
	{
		TextView tv = new TextView(UIUtils.getContext());
		tv.setText(mData.toString());
		return tv;
	}
}
