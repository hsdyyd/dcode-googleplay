package com.droid.googleplay.fragment;

import java.util.List;

import com.droid.googleplay.adapter.AppItemAdapter;
import com.droid.googleplay.base.BaseFragment;
import com.droid.googleplay.base.BaseHolder;
import com.droid.googleplay.base.LoadingPager.LoadResult;
import com.droid.googleplay.base.SuperBaseAdapter;
import com.droid.googleplay.bean.AppInfoBean;
import com.droid.googleplay.factory.ListViewFactory;
import com.droid.googleplay.holder.AppItemHolder;
import com.droid.googleplay.manager.DownloadManager;
import com.droid.googleplay.protocol.AppProtocol;
import com.droid.googleplay.utils.UIUtils;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.TextView;

/**
* @author yidong
* @date 2016年9月16日 上午11:04:30
* @version 1.0
* @Description 
*/
public class AppFragment extends BaseFragment
{

	private List<AppInfoBean> mDatas;
	private AppProtocol mAppProtocol;
	private AppAdapter mAppAdapter;

	@Override
	public LoadResult initData()
	{
		mAppProtocol = new AppProtocol();
		
		try
		{
			mDatas = mAppProtocol.loadData(0);
			
			return checkState(mDatas);
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
		ListView listView = ListViewFactory.createListView();
		
		mAppAdapter = new AppAdapter(listView,mDatas);
		listView.setAdapter(mAppAdapter);
		
		return listView;
	}
	
	class AppAdapter extends AppItemAdapter
	{

		public AppAdapter(AbsListView absListView, List<AppInfoBean> dataSource)
		{
			super(absListView, dataSource);
		}

		@Override
		public List<AppInfoBean> onLoadMore() throws Exception
		{
			return mAppProtocol.loadData(mDatas.size());
		}
	}
	
	@Override
	public void onResume()
	{
		if(mAppAdapter!=null)
		{
			List<AppItemHolder> appItemHolders = mAppAdapter.getAppItemHolders();
			for (AppItemHolder appItemHolder : appItemHolders) {
				DownloadManager.getInstance().addObserver(appItemHolder);//删除
			}
			// 手动刷新-->重新获取状态,然后更新ui
			mAppAdapter.notifyDataSetChanged();
		}
		super.onResume();
	}
	
	@Override
	public void onPause()
	{
		if(mAppAdapter!=null)
		{
			List<AppItemHolder> appItemHolders = mAppAdapter.getAppItemHolders();
			for (AppItemHolder appItemHolder : appItemHolders) {
				DownloadManager.getInstance().deleteObserver(appItemHolder);//删除
			}
		}
		super.onPause();
	}
}
