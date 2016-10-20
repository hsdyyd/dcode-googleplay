package com.droid.googleplay.fragment;

import java.util.List;
import java.util.Random;

import com.droid.googleplay.base.BaseFragment;
import com.droid.googleplay.base.BaseHolder;
import com.droid.googleplay.base.SuperBaseAdapter;
import com.droid.googleplay.base.LoadingPager.LoadResult;
import com.droid.googleplay.bean.SubjectInfoBean;
import com.droid.googleplay.holder.SubjectHolder;
import com.droid.googleplay.protocol.SubjectProtocol;
import com.droid.googleplay.utils.UIUtils;

import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.TextView;

/**
* @author yidong
* @date 2016年9月16日 上午11:04:30
* @version 1.0
* @Description 
*/
public class SubjectFragment extends BaseFragment
{

	private List<SubjectInfoBean> mData;

	@Override
	public LoadResult initData()
	{
		SubjectProtocol protocol = new SubjectProtocol();
		try
		{
			mData = protocol.loadData(0);
			return checkState(mData);
		} catch (Exception e)
		{
			e.printStackTrace();
			return LoadResult.ERROR;
		}
	}

	@Override
	public View initSuccessView()
	{
		ListView lv = new ListView(UIUtils.getContext());
		lv.setAdapter(new SubjectAdapter(lv,mData));
		
		return lv;
	}
	
	class SubjectAdapter extends SuperBaseAdapter<SubjectInfoBean>
	{

		public SubjectAdapter(AbsListView absListView, List<SubjectInfoBean> dataSource)
		{
			super(absListView, dataSource);
		}

		@Override
		public BaseHolder<SubjectInfoBean> getSpecialHolder()
		{
			return new SubjectHolder();
		}
		
	}
}