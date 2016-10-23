/**
 * @auth yidong
 * @date 2016年10月23日下午9:15:18
 * @version v1.0
 * @desc
 *
 */

package com.droid.googleplay.holder;

import com.droid.googleplay.base.BaseHolder;
import com.droid.googleplay.bean.CategoryInfoBean;
import com.droid.googleplay.utils.UIUtils;

import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.AbsListView;
import android.widget.TextView;

public class TitleCategoryHolder extends BaseHolder<CategoryInfoBean>
{

	private TextView mTv;

	@Override
	public View initHolderView()
	{
		mTv = new TextView(UIUtils.getContext());
		mTv.setTextSize(15);
		int padding = UIUtils.dip2Px(5);
		mTv.setPadding(padding , padding, padding, padding);
		mTv.setBackgroundColor(Color.WHITE);
		
		AbsListView.LayoutParams params = new AbsListView.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		mTv.setLayoutParams(params);
		return mTv;
	}

	@Override
	public void refreshHolderView(CategoryInfoBean data)
	{
		mTv.setText(data.title);
	}

	

}
