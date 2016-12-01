package com.droid.googleplay.activity;

import com.droid.googleplay.R;
import com.droid.googleplay.base.BaseActivity;
import com.droid.googleplay.base.LoadingPager;
import com.droid.googleplay.base.LoadingPager.LoadResult;
import com.droid.googleplay.bean.AppInfoBean;
import com.droid.googleplay.holder.AppDetailBottomHolder;
import com.droid.googleplay.holder.AppDetailDesHolder;
import com.droid.googleplay.holder.AppDetailInfoHolder;
import com.droid.googleplay.holder.AppDetailPicHolder;
import com.droid.googleplay.holder.AppDetailSafeHolder;
import com.droid.googleplay.manager.DownloadManager;
import com.droid.googleplay.protocol.DetailProtocol;
import com.droid.googleplay.utils.UIUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

/**
* @author yidong
* @date 2016年11月22日 上午10:36:18
* @version 1.0
* @Description 
*/
public class DetailActivity extends BaseActivity
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
	private LoadingPager mLoadingPager;
	private AppDetailBottomHolder mAppDetailBottomHolder;
//	@Override
//	protected void onCreate(Bundle savedInstanceState)
//	{
//		super.onCreate(savedInstanceState);
//		
//		init();
//		initView();
//		initActionBar();
//		initData();
//	}
	
	public void init()
	{
		mPackageName = getIntent().getStringExtra("packageName");
		Log.i("LOG", "------------------------>"+mPackageName);
	}
	

	public void initView()
	{
		mLoadingPager = new LoadingPager(UIUtils.getContext()){

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
		
		setContentView(mLoadingPager);
	}
	
	public void initActionBar()
	{
		ActionBar actionBar = getSupportActionBar();
		actionBar.setTitle("GooglePlay");
		actionBar.setDisplayHomeAsUpEnabled(true);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		switch (item.getItemId())
		{
			case android.R.id.home:
				finish();
				break;
	
			default:
				break;
		}
		return super.onOptionsItemSelected(item);
	}

	public void initData()
	{
		mLoadingPager.loadData();
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
		
		mAppDetailBottomHolder = new AppDetailBottomHolder();
		mContainerBottom.addView(mAppDetailBottomHolder.getViewHolder());
		mAppDetailBottomHolder.setDataAndRefreshHolderView(mData);
		DownloadManager.getInstance().addObserver(mAppDetailBottomHolder);
		
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
	
	@Override
	protected void onPause()
	{
		if(mAppDetailBottomHolder!=null)
		{
			DownloadManager.getInstance().deleteObserver(mAppDetailBottomHolder);
		}
		super.onPause();
	}
	
	@Override
	protected void onResume()
	{
		if(mAppDetailBottomHolder!=null)
		{
			mAppDetailBottomHolder.addObserverAndRefresh();
		}
		super.onResume();
	}
}
