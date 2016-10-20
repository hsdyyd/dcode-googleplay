package com.droid.googleplay.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;

/**
 * @author yidong
 * @date 2016年10月20日 下午1:21:28
 * @version 1.0
 * @Description
 */
public class RatioLayout extends FrameLayout
{
	private float mPicRation = 2.43f;  // 图片显示的宽高比
	public RatioLayout(Context context)
	{
		super(context);
	}

	public RatioLayout(Context context, AttributeSet attrs)
	{
		super(context, attrs);
	}

	// onlayout   ondraw
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
	{
		// 获取父容器的宽的模式
		int parentWidthMode = MeasureSpec.getMode(widthMeasureSpec);
		int parentHeightMode = MeasureSpec.getMode(heightMeasureSpec);
		if(parentWidthMode == MeasureSpec.EXACTLY && mPicRation!=0)
		{
			int parentWidth = MeasureSpec.getSize(widthMeasureSpec);
			
			int childWidth = parentWidth - this.getPaddingLeft() - this.getPaddingRight();
			// 控件的宽度/控件的高度 = mPicRation
			
			// 计算孩子的高度
			int childHeight = (int) (childWidth / mPicRation + .5f);
			
			int parentHeight = childHeight + this.getPaddingTop() + this.getPaddingBottom();
			
			// 测绘孩子的高度
			
			int childWidthMeasureSpec = MeasureSpec.makeMeasureSpec(childWidth, MeasureSpec.EXACTLY);
			
			int childHeightMeasureSpec = MeasureSpec.makeMeasureSpec(childHeight, MeasureSpec.EXACTLY);
			measureChildren(childWidthMeasureSpec, childHeightMeasureSpec);
			
			// 设置自己的测绘结果
			setMeasuredDimension(parentWidth, parentHeight);
		}
		else if(parentHeightMode == MeasureSpec.EXACTLY && mPicRation!=0)
		{
			int parentHeight = MeasureSpec.getSize(heightMeasureSpec);
			
			int childHeight = parentHeight - this.getPaddingTop() - this.getPaddingBottom();
			// 控件的宽度/控件的高度 = mPicRation
			
			// 计算孩子的高度
			int childWidth = (int) (childHeight * mPicRation + .5f);
			
			int parentWidth = childHeight + this.getPaddingLeft() + this.getPaddingRight();
			
			// 测绘孩子的高度
			
			int childWidthMeasureSpec = MeasureSpec.makeMeasureSpec(childWidth, MeasureSpec.EXACTLY);
			
			int childHeightMeasureSpec = MeasureSpec.makeMeasureSpec(childHeight, MeasureSpec.EXACTLY);
			measureChildren(childWidthMeasureSpec, childHeightMeasureSpec);
			
			// 设置自己的测绘结果
			setMeasuredDimension(parentWidth, parentHeight);
		}
		else
		{
			super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		}
		
	}
}
