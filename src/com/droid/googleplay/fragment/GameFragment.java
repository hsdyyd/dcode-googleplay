package com.droid.googleplay.fragment;

import java.util.List;

import com.droid.googleplay.adapter.AppItemAdapter;
import com.droid.googleplay.base.BaseFragment;
import com.droid.googleplay.base.BaseHolder;
import com.droid.googleplay.base.LoadingPager.LoadResult;
import com.droid.googleplay.bean.AppInfoBean;
import com.droid.googleplay.factory.ListViewFactory;
import com.droid.googleplay.holder.AppItemHolder;
import com.droid.googleplay.manager.DownloadManager;
import com.droid.googleplay.protocol.GameProtocol;

import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;

/**
* @author yidong
* @date 2016年9月16日 上午11:04:30
* @version 1.0
* @Description 
*/
public class GameFragment extends BaseFragment
{

	private List<AppInfoBean> mData;
	private GameProtocol mGemeProtocol;
	private GameAdapter mGameAdapter;

	@Override
	public LoadResult initData()
	{
		mGemeProtocol = new GameProtocol();
		
		try
		{
			mData = mGemeProtocol.loadData(0);
			
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
		ListView listView = ListViewFactory.createListView();
		
		mGameAdapter = new GameAdapter(listView,mData);
		listView.setAdapter(mGameAdapter);
		
		return listView;
	}
	
	class GameAdapter extends AppItemAdapter{

		public GameAdapter(AbsListView absListView, List<AppInfoBean> dataSource)
		{
			super(absListView, dataSource);
		}
		
		@Override
		public List<AppInfoBean> onLoadMore() throws Exception
		{
			return mGemeProtocol.loadData(mData.size());
		}
		
	}
	
	@Override
	public void onResume()
	{
		if(mGameAdapter!=null)
		{
			List<AppItemHolder> appItemHolders = mGameAdapter.getAppItemHolders();
			for (AppItemHolder appItemHolder : appItemHolders) {
				DownloadManager.getInstance().addObserver(appItemHolder);//删除
			}
			// 手动刷新-->重新获取状态,然后更新ui
			mGameAdapter.notifyDataSetChanged();
		}
		super.onResume();
	}
	
	@Override
	public void onPause()
	{
		if(mGameAdapter!=null)
		{
			List<AppItemHolder> appItemHolders = mGameAdapter.getAppItemHolders();
			for (AppItemHolder appItemHolder : appItemHolders) {
				DownloadManager.getInstance().deleteObserver(appItemHolder);//删除
			}
		}
		super.onPause();
	}
}
