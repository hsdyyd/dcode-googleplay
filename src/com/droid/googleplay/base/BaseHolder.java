/**
 * @auth yidong
 * @date 2016年9月17日下午9:12:43
 * @version v1.0
 * @desc
 *
 */

package com.droid.googleplay.base;

import android.view.View;

public abstract class BaseHolder<T>
{
	public View mViewHolder;
	private T mData;
	
	public View getViewHolder()
	{
		return mViewHolder;
	}

	public BaseHolder()
	{
		mViewHolder = initHolderView();
		mViewHolder.setTag(this);
	}

	public void setDataAndRefreshHolderView(T data)
	{
		mData = data;
		refreshHolderView(mData);
	}
	
	public abstract View initHolderView();
	public abstract void refreshHolderView(T data);
}
