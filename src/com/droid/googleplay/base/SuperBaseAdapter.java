/**
 * @auth yidong
 * @date 2016年9月17日下午8:27:14
 * @version v1.0
 * @desc
 *
 */

package com.droid.googleplay.base;

import java.util.ArrayList;
import java.util.List;

import com.droid.googleplay.holder.HomeHolder;
import com.droid.googleplay.holder.LoadMoreHolder;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public abstract class SuperBaseAdapter<T> extends BaseAdapter
{
	public List<T> mDataSource = new ArrayList<T>();
	private static final int VIEWTYPE_LOADMORE = 0;
	private static final int VIEWTYPE_NORMAL = 1;
	private LoadMoreHolder mLoadMoreHolder;
	
	public SuperBaseAdapter(List<T> dataSource)
	{
		super();
		mDataSource = dataSource;
	}

	public int getCount()
	{
		if(mDataSource!=null){
			return mDataSource.size()+1;
		}
		return 0;
	}

	public Object getItem(int position)
	{
		if(mDataSource!=null)
		{
			return mDataSource.get(position);
		}
		return null;
	}

	public long getItemId(int position)
	{
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent)
	{
		BaseHolder holder;
		if(convertView==null)
		{
			if(getItemViewType(position)==VIEWTYPE_LOADMORE)
			{
				holder = getLoadMoreViewHolder();
			}else{
				holder = getSpecialHolder(); 
			}
		}
		else
		{
			holder = (BaseHolder) convertView.getTag();
		}
		
		if(getItemViewType(position)==VIEWTYPE_LOADMORE)
		{
			mLoadMoreHolder.setDataAndRefreshHolderView(LoadMoreHolder.STATE_LOADING);
		}else{
			holder.setDataAndRefreshHolderView(mDataSource.get(position));
		}
		
		
		return holder.mViewHolder;
	}

	public abstract BaseHolder getSpecialHolder();
	
	@Override
	public int getViewTypeCount()
	{
		return super.getViewTypeCount()+1;
	}
	
	@Override
	public int getItemViewType(int position)
	{
		if(position==getCount()-1)
		{
			return VIEWTYPE_LOADMORE;
		}
		
		return VIEWTYPE_NORMAL;
	}

	private LoadMoreHolder getLoadMoreViewHolder()
	{
		if(mLoadMoreHolder==null){
			mLoadMoreHolder = new LoadMoreHolder();
		}
		return mLoadMoreHolder;
	}
}
