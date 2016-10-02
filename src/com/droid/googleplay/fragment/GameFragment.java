package com.droid.googleplay.fragment;

import java.util.List;

import com.droid.googleplay.base.BaseFragment;
import com.droid.googleplay.base.BaseHolder;
import com.droid.googleplay.base.LoadingPager.LoadResult;
import com.droid.googleplay.base.SuperBaseAdapter;
import com.droid.googleplay.bean.AppInfoBean;
import com.droid.googleplay.factory.ListViewFactory;
import com.droid.googleplay.holder.AppItemHolder;
import com.droid.googleplay.protocol.GameProtocol;
import com.droid.googleplay.utils.UIUtils;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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
		
		listView.setAdapter(new GameAdapter(listView,mData));
		
		return listView;
	}
	
	class GameAdapter extends SuperBaseAdapter<AppInfoBean>{

		public GameAdapter(AbsListView absListView, List<AppInfoBean> dataSource)
		{
			super(absListView, dataSource);
		}

		@Override
		public BaseHolder getSpecialHolder()
		{
			return new AppItemHolder();
		}
		
		@Override
		public List<AppInfoBean> onLoadMore() throws Exception
		{
			return mGemeProtocol.loadData(mData.size());
		}
		
	}
}
