package com.droid.googleplay.holder;

import com.droid.googleplay.R;
import com.droid.googleplay.base.BaseHolder;
import com.droid.googleplay.bean.AppInfoBean;
import com.droid.googleplay.constant.Constants;
import com.droid.googleplay.utils.BitmapHelp;
import com.droid.googleplay.utils.UIUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewParent;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

/**
* @author yidong
* @date 2016年11月23日 下午2:52:23
* @version 1.0
* @Description 
*/
public class AppDetailDesHolder extends BaseHolder<AppInfoBean> implements OnClickListener
{
	@ViewInject(R.id.app_detail_des_tv_author)
	TextView			mTvAuthor;

	@ViewInject(R.id.app_detail_des_iv_arrow)
	ImageView			mIvArrow;

	@ViewInject(R.id.app_detail_des_tv_des)
	TextView			mTvDes;
	
	private boolean isOpen = true;
	private int mMeasuredHeight;

	private AppInfoBean mData;
	@Override
	public View initHolderView()
	{
		View view = View.inflate(UIUtils.getContext(), R.layout.item_app_detail_des, null);
		ViewUtils.inject(this, view);
		
		view.setOnClickListener(this);
		
		return view;
	}

	@Override
	public void refreshHolderView(AppInfoBean data)
	{
		mData = data;
		
		mTvAuthor.setText(data.author);
		
		mTvDes.setText(data.des);
		
		mTvDes.getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener()
		{

			@Override
			public void onGlobalLayout()
			{
				mMeasuredHeight = mTvDes.getMeasuredHeight();
				toggle(false);
				mTvDes.getViewTreeObserver().removeGlobalOnLayoutListener(this);
			}
		});
		
	}

	@Override
	public void onClick(View v)
	{
		toggle(true);
	}

	private void toggle(boolean isAnimation)
	{
		if(isOpen) // 折叠
		{
			int start = mMeasuredHeight;
			int end = getShortHeight(7, mData);
			
			if(isAnimation)
			{
				doAnimation(start, end);
			}
			else
			{
				mTvDes.setHeight(end);
			}
		}
		else
		{
			int start = getShortHeight(7, mData);
			int end = mMeasuredHeight;
			
			if(isAnimation)
			{
				doAnimation(start, end);
			}
			else
			{
				mTvDes.setHeight(end);
			}
		}
		
		if(isAnimation)
		{
			if(isOpen)
			{
				ObjectAnimator.ofFloat(mIvArrow, "rotation", 180,0).start();
			}
			else
			{
				ObjectAnimator.ofFloat(mIvArrow, "rotation", 0,180).start();
			}
		}
		
		isOpen = !isOpen;
	}

	private void doAnimation(int start, int end)
	{
		ObjectAnimator animator = ObjectAnimator.ofInt(mTvDes, "height", start,end);
		animator.start();
		animator.addListener(new AnimatorListener()
		{
			
			@Override
			public void onAnimationStart(Animator animation)
			{
			}
			
			@Override
			public void onAnimationRepeat(Animator animation)
			{
			}
			
			@Override
			public void onAnimationEnd(Animator animation)
			{
				ViewParent parent = mTvDes.getParent();
				while(true)
				{
					parent = parent.getParent();
					if(parent==null)
					{
						break;
					}
					
					if(parent instanceof ScrollView)
					{
						((ScrollView)parent).fullScroll(View.FOCUS_DOWN);
						break;
					}
				}
				
			}
			
			@Override
			public void onAnimationCancel(Animator animation)
			{
			}
		});
	}

	private int getShortHeight(int i, AppInfoBean data)
	{
		TextView tv = new TextView(UIUtils.getContext());
		tv.setLines(7);
		tv.setText(data.des);
		
		tv.measure(0, 0);
		return tv.getMeasuredHeight();
	}

}
