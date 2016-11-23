package com.droid.googleplay.activity;

import com.droid.googleplay.R;
import com.droid.googleplay.base.LoadingPager;
import com.droid.googleplay.base.LoadingPager.LoadResult;
import com.droid.googleplay.bean.AppInfoBean;
import com.droid.googleplay.holder.AppDetailBottomHolder;
import com.droid.googleplay.holder.AppDetailDesHolder;
import com.droid.googleplay.holder.AppDetailInfoHolder;
import com.droid.googleplay.holder.AppDetailPicHolder;
import com.droid.googleplay.holder.AppDetailSafeHolder;
import com.droid.googleplay.protocol.DetailProtocol;
import com.droid.googleplay.utils.UIUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
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
	
	@ViewInject(R.id.app_detail_bottom)
	FrameLayout mContainerBottom;
	
	@ViewInject(R.id.app_detail_des)
	FrameLayout mContainerDes;
	
	@ViewInject(R.id.app_detail_info)
	FrameLayout mContainerInfo;
	
	@ViewInject(R.id.app_detail_pic)
	FrameLayout mContainerPic;
	
	@ViewInject(R.id.app_detail_safe)
	FrameLayout mContainerSafe;

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
		Log.i("LOG", "------------------------>"+mPackageName);
	}
	
	private LoadResult onInitData()
	{
//		SystemClock.sleep(2000);
		DetailProtocol protocol = new DetailProtocol(mPackageName);
		try
		{
			mData = protocol.loadData(0);
			Log.i("LOG", "onInitData------------------------>"+mPackageName);
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
		Log.i("LOG", "onInitSuccessView------------------------>"+mPackageName);
		TextView tv = new TextView(UIUtils.getContext());
		tv.setText(mData.toString());
		
		View view = View.inflate(UIUtils.getContext(),R.layout.activity_detail, null);
		ViewUtils.inject(this,view);
		
		AppDetailBottomHolder appDetailBottomHolder = new AppDetailBottomHolder();
		mContainerBottom.addView(appDetailBottomHolder.getViewHolder());
		appDetailBottomHolder.setDataAndRefreshHolderView(mData);
		
		AppDetailDesHolder appDetailDesHolder = new AppDetailDesHolder();
		mContainerDes.addView(appDetailDesHolder.getViewHolder());
		appDetailDesHolder.setDataAndRefreshHolderView(mData);
		
		AppDetailInfoHolder appDetailInfoHolder = new AppDetailInfoHolder();
		mContainerInfo.addView(appDetailInfoHolder.getViewHolder());
		appDetailInfoHolder.setDataAndRefreshHolderView(mData);
		
		AppDetailPicHolder appDetailPicHolder = new AppDetailPicHolder();
		mContainerPic.addView(appDetailPicHolder.getViewHolder());
		appDetailPicHolder.setDataAndRefreshHolderView(mData);
		
		AppDetailSafeHolder appDetailSafeHolder = new AppDetailSafeHolder();
		mContainerSafe.addView(appDetailSafeHolder.getViewHolder());
		appDetailSafeHolder.setDataAndRefreshHolderView(mData);
		
		return view;
	}
}
