/**
 * @auth yidong
 * @date 2016年9月16日下午8:48:33
 * @version v1.0
 * @desc
 *
 */

package com.droid.googleplay.base;

import com.droid.googleplay.R;
import com.droid.googleplay.utils.UIUtils;

import android.content.Context;
import android.view.View;
import android.widget.FrameLayout;

public abstract class LoadingPager extends FrameLayout
{

	private static final int STATE_NONE = -1;
	private static final int STATE_LOADING = 0;
	private static final int STATE_ERROR = 1;
	private static final int STATE_EMPTY = 2;
	private static final int STATE_SUCCESS = 3;
	
	private int mCurState = STATE_NONE; // 默认当前状态为加载视图
	
	private View mLoadingView;
	private View mErrorView;
	private View mEmptyView;
	private View mSuccessView;
	
	public LoadingPager(Context context)
	{
		super(context);
		initCommonView();
		
	}

	private void initCommonView()
	{
		mLoadingView = View.inflate(UIUtils.getContext(), R.layout.pager_loading, null);
		this.addView(mLoadingView);
		
		mErrorView = View.inflate(UIUtils.getContext(), R.layout.pager_error, null);
		mErrorView.findViewById(R.id.error_btn_retry).setOnClickListener(new OnClickListener()
		{
			
			public void onClick(View v)
			{
				loadData();
			}
		});
		this.addView(mErrorView);
		
		mEmptyView = View.inflate(UIUtils.getContext(), R.layout.pager_empty, null);
		this.addView(mEmptyView);
		
		// 当加载完数据后，根据加载数据的状态来确定界面显示的视图
		refreshUI();
	}

	private void refreshUI()
	{
		mLoadingView.setVisibility(((mCurState==STATE_LOADING)||(mCurState==STATE_NONE))?0:8);
		
		mErrorView.setVisibility((mCurState==STATE_ERROR)?0:8);
		
		mEmptyView.setVisibility((mCurState==STATE_EMPTY)?0:8);
		
		if(mSuccessView==null&&mCurState==STATE_SUCCESS)
		{
			mSuccessView = initSuccessView();
			
			this.addView(mSuccessView);
		}
		
		if(mSuccessView!=null)
		{
			mSuccessView.setVisibility((mCurState==STATE_SUCCESS)?0:8);
		}
	}
	
	public void loadData()
	{
		if(mCurState!=STATE_SUCCESS&&mCurState!=STATE_LOADING){
			int state = STATE_LOADING;
			mCurState = state;
			refreshUI();
			new Thread(new LoadingTask()).start();
		}
	}

	class LoadingTask implements Runnable
	{

		public void run()
		{
			LoadResult tempState = initData();
			
			mCurState = tempState.getState();
			
			UIUtils.postTaskSafely(new Runnable()
			{
				
				public void run()
				{
					refreshUI();
				}
			});
		}
	}
	
	
	public abstract LoadResult initData();
	
	public abstract View initSuccessView();
	
	public enum LoadResult
	{
		ERROR(STATE_ERROR),EMPTY(STATE_EMPTY),SUCCESS(STATE_SUCCESS);
		int state;
		
		public int getState()
		{
			return state;
		}

		LoadResult(int state)
		{
			this.state = state;
		}
	}
}
