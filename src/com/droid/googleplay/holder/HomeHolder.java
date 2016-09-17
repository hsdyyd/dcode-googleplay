/**
 * @auth yidong
 * @date 2016年9月17日下午8:49:45
 * @version v1.0
 * @desc
 *
 */

package com.droid.googleplay.holder;

import com.droid.googleplay.R;
import com.droid.googleplay.base.BaseHolder;
import com.droid.googleplay.utils.UIUtils;

import android.view.View;
import android.widget.TextView;

public class HomeHolder extends BaseHolder<String>
{
	private TextView mTvTmp1;
	private TextView mTvTmp2;

	public View initHolderView()
	{
		View view = View.inflate(UIUtils.getContext(), R.layout.item_tmp, null);
		mTvTmp1 = (TextView) view.findViewById(R.id.tmp_tv_1);
		mTvTmp2 = (TextView) view.findViewById(R.id.tmp_tv_2);
		
		return view;
	}
	
	public void refreshHolderView(String data)
	{
		mTvTmp1.setText("我是头: "+data);
		mTvTmp2.setText("我是尾: "+data);
	}
}
