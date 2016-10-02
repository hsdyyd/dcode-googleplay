/**
 * @auth yidong
 * @date 2016年10月2日下午9:35:21
 * @version v1.0
 * @desc
 *
 */

package com.droid.googleplay.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class InnerViewPager extends ViewPager
{

	private float mDownX;
	private float mDownY;
	private float mMoveX;
	private float mMoveY;

	public InnerViewPager(Context context)
	{
		super(context);
	}
	
	public InnerViewPager(Context context, AttributeSet attrs)
	{
		super(context, attrs);
	}

	@Override
	public boolean onTouchEvent(MotionEvent ev)
	{
		switch (ev.getAction())
		{
			case MotionEvent.ACTION_DOWN:
				mDownX = ev.getRawX();
				mDownY = ev.getRawY();
				
				break;
			case MotionEvent.ACTION_MOVE:
				mMoveX = ev.getRawX();
				mMoveY = ev.getRawY();
				
				int diffX = (int)(mDownX-mMoveX);
				int diffY = (int)(mDownY-mMoveY);
				if(Math.abs(diffX)>Math.abs(diffY))
				{
					getParent().requestDisallowInterceptTouchEvent(true);
				}
				else
				{
					getParent().requestDisallowInterceptTouchEvent(false);
				}
				
				break;
			case MotionEvent.ACTION_UP:
				
				break;
			default:
				break;
		}
		
		return super.onTouchEvent(ev);
	}

}
