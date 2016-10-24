package com.droid.googleplay.fragment;

import java.util.List;

import com.droid.googleplay.base.BaseFragment;
import com.droid.googleplay.base.BaseHolder;
import com.droid.googleplay.base.SuperBaseAdapter;
import com.droid.googleplay.base.LoadingPager.LoadResult;
import com.droid.googleplay.bean.CategoryInfoBean;
import com.droid.googleplay.holder.ItemCategoryHolder;
import com.droid.googleplay.holder.TitleCategoryHolder;
import com.droid.googleplay.protocol.CategoryProtocol;
import com.droid.googleplay.utils.UIUtils;

import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;

/**
* @author yidong
* @date 2016年9月16日 上午11:04:30
* @version 1.0
* @Description 
*/
public class CategoryFragment extends BaseFragment
{

	private List<CategoryInfoBean> mData;

	@Override
	public LoadResult initData()
	{
		CategoryProtocol protocol = new CategoryProtocol();
		
		try
		{
			mData = protocol.loadData(0);
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
		ListView lv = new ListView(UIUtils.getContext());
		lv.setAdapter(new CategoryAdapter(lv,mData));

		return lv;
	}
	
	class CategoryAdapter extends SuperBaseAdapter<CategoryInfoBean>
	{

		public CategoryAdapter(AbsListView absListView, List<CategoryInfoBean> dataSource)
		{
			super(absListView, dataSource);
		}

		@Override
		public BaseHolder<CategoryInfoBean> getSpecialHolder(int position)
		{
			CategoryInfoBean infoBean = mData.get(position);
			if(infoBean.isTitle)
			{
				return new TitleCategoryHolder();
			}
			else
			{
				return new ItemCategoryHolder();
			}
		}
		
		@Override
		public int getViewTypeCount()
		{
			return super.getViewTypeCount()+1;
		}
		
		// 解决listview中存在多种view复用的问题
		@Override
		public int getNormalViewType(int position)
		{
			CategoryInfoBean infoBean = mData.get(position);
			if(infoBean.isTitle)
			{
				return super.getNormalViewType(position)+1;
			}
			else
			{
				return super.getNormalViewType(position);	
			}
			
		}
		
	}
}
