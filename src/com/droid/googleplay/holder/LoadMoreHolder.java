/**
 * @auth yidong
 * @date 2016年9月19日下午8:49:54
 * @version v1.0
 * @desc
 *
 */

package com.droid.googleplay.holder;

import com.droid.googleplay.R;
import com.droid.googleplay.base.BaseHolder;
import com.droid.googleplay.utils.UIUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import android.view.View;
import android.widget.LinearLayout;

public class LoadMoreHolder extends BaseHolder<Integer>
{
	@ViewInject(R.id.item_loadmore_container_loading)
	LinearLayout ll_container_loading;

	@ViewInject(R.id.item_loadmore_container_retry)
	LinearLayout ll_container_retry;

	public static final int STATE_LOADING = 0;
	public static final int STATE_RETRY = 1;
	public static final int STATE_NONE = 2;

	@Override
	public View initHolderView()
	{
		View view = View.inflate(UIUtils.getContext(), R.layout.item_load_more, null);

		ViewUtils.inject(this, view);
		return view;
	}

	@Override
	public void refreshHolderView(Integer state)
	{
		ll_container_loading.setVisibility(8);
		ll_container_retry.setVisibility(8);
		switch (state)
		{
			case STATE_LOADING:
				ll_container_loading.setVisibility(0);
				break;
			case STATE_RETRY:
				ll_container_retry.setVisibility(0);
				break;
			case STATE_NONE:

				break;
		}
	}

}
