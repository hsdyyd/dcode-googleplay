package com.droid.googleplay.holder;

import java.util.List;

import com.droid.googleplay.R;
import com.droid.googleplay.base.BaseHolder;
import com.droid.googleplay.bean.AppInfoBean;
import com.droid.googleplay.bean.AppInfoBean.DetailSafe;
import com.droid.googleplay.constant.Constants;
import com.droid.googleplay.utils.BitmapHelp;
import com.droid.googleplay.utils.UIUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.nineoldandroids.animation.ObjectAnimator;
import com.nineoldandroids.animation.ValueAnimator;
import com.nineoldandroids.animation.ValueAnimator.AnimatorUpdateListener;

import android.support.v4.animation.AnimatorUpdateListenerCompat;
import android.support.v4.animation.ValueAnimatorCompat;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
* @author yidong
* @date 2016年11月23日 下午2:52:23
* @version 1.0
* @Description 
*/
public class AppDetailSafeHolder extends BaseHolder<AppInfoBean> implements OnClickListener
{
	@ViewInject(R.id.app_detail_safe_pic_container)
	LinearLayout	mContainerPic;

	@ViewInject(R.id.app_detail_safe_des_container)
	LinearLayout	mContainerDes;

	@ViewInject(R.id.app_detail_safe_iv_arrow)
	ImageView		mIvArrow;
	
	private boolean isOpen = true;
	
	@Override
	public View initHolderView()
	{
		View view = View.inflate(UIUtils.getContext(),R.layout.item_app_detail_safe, null);
		ViewUtils.inject(this,view);
		
		view.setOnClickListener(this);
		
		return view;
	}

	@Override
	public void refreshHolderView(AppInfoBean data)
	{
		List<DetailSafe> safeBeans = data.safe;
		
		for (DetailSafe safeBean : safeBeans)
		{
			ImageView ivIcon = new ImageView(UIUtils.getContext());
			BitmapHelp.display(ivIcon, Constants.URLS.IMAGEBASEURL+safeBean.safeUrl);
			mContainerPic.addView(ivIcon);
			
			LinearLayout ll = new LinearLayout(UIUtils.getContext());
			ImageView ivDes = new ImageView(UIUtils.getContext());
			BitmapHelp.display(ivDes, Constants.URLS.IMAGEBASEURL+safeBean.safeDesUrl);
			TextView tvDes = new TextView(UIUtils.getContext());
			tvDes.setText(safeBean.safeDes);
			ll.addView(ivDes);
			ll.addView(tvDes);
			
			int padding = UIUtils.dip2Px(5);
			ll.setPadding(padding , padding, padding, padding);
			
			mContainerDes.addView(ll);
		}
		
		toggle(false);
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
			mContainerDes.measure(0, 0);
			int measuredHeight = mContainerDes.getMeasuredHeight();
			int start = measuredHeight;
			int end = 0;
			if(isAnimation){
				toAnimator(start, end);
			}else{
				LayoutParams params = mContainerDes.getLayoutParams();
				params.height = end;
				mContainerDes.setLayoutParams(params );
			}
		}
		else  // 展开
		{
			mContainerDes.measure(0, 0);
			int measuredHeight = mContainerDes.getMeasuredHeight();
			int start = 0;
			int end = measuredHeight;
			
			if(isAnimation){
				toAnimator(start, end);
			}else{
				LayoutParams params = mContainerDes.getLayoutParams();
				params.height = end;
				mContainerDes.setLayoutParams(params );
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

	private void toAnimator(int start, int end)
	{
		ValueAnimator animator = ValueAnimator.ofInt(start,end);
		animator.start();
		
		animator.addUpdateListener(new AnimatorUpdateListener()
		{
			
			@Override
			public void onAnimationUpdate(ValueAnimator animator)
			{
				int height = (Integer)animator.getAnimatedValue();
				
				LayoutParams params = mContainerDes.getLayoutParams();
				params.height = height;
				mContainerDes.setLayoutParams(params );
			}
		});
	}

}
