package com.droid.googleplay.adapter;

import java.util.LinkedList;
import java.util.List;

import com.droid.googleplay.activity.DetailActivity;
import com.droid.googleplay.base.BaseHolder;
import com.droid.googleplay.base.SuperBaseAdapter;
import com.droid.googleplay.bean.AppInfoBean;
import com.droid.googleplay.holder.AppItemHolder;
import com.droid.googleplay.manager.DownloadManager;
import com.droid.googleplay.utils.UIUtils;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;

/**
* @author yidong
* @date 2016年11月22日 上午10:08:23
* @version 1.0
* @Description 
*/
public class AppItemAdapter extends SuperBaseAdapter<AppInfoBean>
{
	private List<AppItemHolder> mAppItemHolders	= new LinkedList<AppItemHolder>();
	
	public List<AppItemHolder> getAppItemHolders()
	{
		return mAppItemHolders;
	}

	public AppItemAdapter(AbsListView absListView, List<AppInfoBean> dataSource)
	{
		super(absListView, dataSource);
	}

	@Override
	public BaseHolder getSpecialHolder(int position)
	{
		AppItemHolder appItemHolder = new AppItemHolder();
		mAppItemHolders.add(appItemHolder);
		DownloadManager.getInstance().addObserver(appItemHolder);
		return appItemHolder;
	}

	@Override
	public void onNormalItemClick(AdapterView<?> parent, View view, int position,
			long id)
	{
		super.onNormalItemClick(parent, view, position, id);
		goToDetailActivity(position);
	}

	private void goToDetailActivity(int position)
	{
		Intent intent = new Intent(UIUtils.getContext(),DetailActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		
		Log.i("LOG", "================>"+mDataSource.get(position).packageName);
		
		intent.putExtra("packageName", mDataSource.get(position).packageName);
		
		UIUtils.getContext().startActivity(intent);
		
	}
}
