/**
 * @auth yidong
 * @date 2016年12月2日下午8:54:08
 * @version v1.0
 * @desc
 *
 */

package com.droid.googleplay.holder;

import com.droid.googleplay.R;
import com.droid.googleplay.base.BaseHolder;
import com.droid.googleplay.utils.UIUtils;

import android.view.View;

public class MenuHolder extends BaseHolder<Object>
{

	@Override
	public View initHolderView()
	{
		View view = View.inflate(UIUtils.getContext(), R.layout.menu_view, null);
		return view;
	}

	@Override
	public void refreshHolderView(Object data)
	{
	}

}
