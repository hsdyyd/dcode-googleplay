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

import com.droid.googleplay.constant.Constants;
import com.droid.googleplay.factory.ThreadPoolFactory;
import com.droid.googleplay.holder.AppItemHolder;
import com.droid.googleplay.holder.LoadMoreHolder;
import com.droid.googleplay.utils.UIUtils;
import com.lidroid.xutils.view.annotation.event.OnItemClick;

import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;

public abstract class SuperBaseAdapter<T> extends BaseAdapter
		implements OnItemClickListener
{
	public List<T> mDataSource = new ArrayList<T>();
	private static final int VIEWTYPE_LOADMORE = 0;
	private static final int VIEWTYPE_NORMAL = 1;
	private LoadMoreHolder mLoadMoreHolder;
	private LoadMoreTask mLoadMoreTask;
	private AbsListView mAbsListView;

	public SuperBaseAdapter(AbsListView absListView, List<T> dataSource)
	{
		super();
		absListView.setOnItemClickListener(this);
		mDataSource = dataSource;
		mAbsListView = absListView;
	}

	public int getCount()
	{
		if (mDataSource != null)
		{
			return mDataSource.size() + 1;
		}
		return 0;
	}

	public Object getItem(int position)
	{
		if (mDataSource != null)
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
		BaseHolder holder = null;
		if (convertView == null)
		{
			if (getItemViewType(position) == VIEWTYPE_LOADMORE)
			{
				holder = getLoadMoreViewHolder();
			}
			else
			{
				holder = getSpecialHolder(position);
			}
		}
		else
		{
			holder = (BaseHolder) convertView.getTag();
		}

		if (getItemViewType(position) == VIEWTYPE_LOADMORE)
		{
			// 真正加载更多
			if (hasLoadMore())
			{
				performLoadMore();
			}
			else
			{
				mLoadMoreHolder.setDataAndRefreshHolderView(LoadMoreHolder.STATE_NONE);
			}

			// mLoadMoreHolder.setDataAndRefreshHolderView(LoadMoreHolder.STATE_LOADING);
		}
		else
		{
			holder.setDataAndRefreshHolderView(mDataSource.get(position));
		}

		return holder.mViewHolder;
	}

	public abstract BaseHolder getSpecialHolder(int position);

	@Override
	public int getViewTypeCount()
	{
		return super.getViewTypeCount() + 1;
	}

	@Override
	public int getItemViewType(int position)
	{
		if (position == getCount() - 1)
		{
			return VIEWTYPE_LOADMORE;
		}
		
		return getNormalViewType(position);
	}

	/**
	 * 子类可以重写这个方法，添加更多的类型
	 * @param position
	 * @return
	 */
	public int getNormalViewType(int position)
	{
		return VIEWTYPE_NORMAL;
	}

	private LoadMoreHolder getLoadMoreViewHolder()
	{
		if (mLoadMoreHolder == null)
		{
			mLoadMoreHolder = new LoadMoreHolder();
		}
		return mLoadMoreHolder;
	}

	private void performLoadMore()
	{
		if (mLoadMoreTask == null)
		{
			int state = LoadMoreHolder.STATE_LOADING;
			mLoadMoreHolder.setDataAndRefreshHolderView(state);
			mLoadMoreTask = new LoadMoreTask();
			ThreadPoolFactory.getNormalPool().execute(mLoadMoreTask);
		}
	}

	class LoadMoreTask implements Runnable
	{
		public void run()
		{
			List<T> loadMoreDatas = null;
			int state = LoadMoreHolder.STATE_LOADING;
			try
			{
				loadMoreDatas = onLoadMore();
				if (loadMoreDatas == null)
				{
					state = LoadMoreHolder.STATE_NONE;
				}
				else
				{
					if (loadMoreDatas.size() < Constants.PAGESIZE)
					{
						state = LoadMoreHolder.STATE_NONE;
					}
					else
					{
						state = LoadMoreHolder.STATE_LOADING;
					}
				}
			}
			catch (Exception e)
			{
				e.printStackTrace();
				state = LoadMoreHolder.STATE_RETRY;
			}

			final int tempState = state;
			final List<T> tempLoadMoreDatas = loadMoreDatas;
			UIUtils.postTaskSafely(new Runnable()
			{
				public void run()
				{
					// 刷新loadmore视图
					mLoadMoreHolder.setDataAndRefreshHolderView(tempState);
					// 刷新listview,返回loadmore加载后的数据mDataSource.addAll
					if (tempLoadMoreDatas != null)
					{
						mDataSource.addAll(tempLoadMoreDatas);
						notifyDataSetChanged();
					}
				}
			});

			mLoadMoreTask = null;
		}
	}

	public List<T> onLoadMore() throws Exception
	{
		return null;
	}

	public boolean hasLoadMore()
	{
		return true;
	}

	// 处理listview条目被点击的事件(加载更多及listview普通条目的点击事件)
	public void onItemClick(AdapterView<?> parent, View view, int position, long id)
	{
		if(mAbsListView instanceof ListView)
		{
			position = position - ((ListView)mAbsListView).getHeaderViewsCount();
		}
		
		if (getItemViewType(position) == VIEWTYPE_LOADMORE)
		{
			performLoadMore();
		}
		else
		{
			onNormalItemClick(parent, view, position, id);
		}
	}
	
	public void onNormalItemClick(AdapterView<?> parent, View view, int position, long id)
	{
		
	}
}
