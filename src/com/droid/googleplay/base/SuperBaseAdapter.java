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

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public abstract class SuperBaseAdapter<T> extends BaseAdapter
{
	public List<T> mDataSource = new ArrayList<T>();
	
	public SuperBaseAdapter(List<T> dataSource)
	{
		super();
		mDataSource = dataSource;
	}

	public int getCount()
	{
		if(mDataSource!=null){
			return mDataSource.size();
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
			holder = getSpecialHolder();
		}
		else
		{
			holder = (BaseHolder) convertView.getTag();
		}
		holder.setDataAndRefreshHolderView(mDataSource.get(position));
		
		return holder.mViewHolder;
	}

	public abstract BaseHolder getSpecialHolder();

}
