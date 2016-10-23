/**
 * @auth yidong
 * @date 2016年10月23日下午9:15:59
 * @version v1.0
 * @desc
 *
 */

package com.droid.googleplay.holder;

import com.droid.googleplay.R;
import com.droid.googleplay.base.BaseHolder;
import com.droid.googleplay.bean.CategoryInfoBean;
import com.droid.googleplay.constant.Constants;
import com.droid.googleplay.utils.BitmapHelp;
import com.droid.googleplay.utils.UIUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ItemCategoryHolder extends BaseHolder<CategoryInfoBean>
{
	@ViewInject(R.id.item_category_item_1)
	LinearLayout mContainerItem1;
	@ViewInject(R.id.item_category_item_2)
	LinearLayout mContainerItem2;
	@ViewInject(R.id.item_category_item_3)
	LinearLayout mContainerItem3;
	
	@ViewInject(R.id.item_category_icon_1)
	ImageView mIvIcon1;
	@ViewInject(R.id.item_category_icon_2)
	ImageView mIvIcon2;
	@ViewInject(R.id.item_category_icon_3)
	ImageView mIvIcon3;
	
	@ViewInject(R.id.item_category_name_1)
	TextView mTvName1;
	@ViewInject(R.id.item_category_name_2)
	TextView mTvName2;
	@ViewInject(R.id.item_category_name_3)
	TextView mTvName3;
	
	@Override
	public View initHolderView()
	{
		View view = View.inflate(UIUtils.getContext(), R.layout.item_category_info, null);
		ViewUtils.inject(this,view);
		return view;
	}

	@Override
	public void refreshHolderView(CategoryInfoBean data)
	{
		mTvName1.setText(data.name1);
		mTvName2.setText(data.name2);
		mTvName3.setText(data.name3);
		
		mIvIcon1.setImageResource(R.drawable.ic_default);
		BitmapHelp.display(mIvIcon1, Constants.URLS.IMAGEBASEURL+data.url1);
		mIvIcon2.setImageResource(R.drawable.ic_default);
		BitmapHelp.display(mIvIcon2, Constants.URLS.IMAGEBASEURL+data.url2);
		mIvIcon3.setImageResource(R.drawable.ic_default);
		BitmapHelp.display(mIvIcon3, Constants.URLS.IMAGEBASEURL+data.url3);
		
	}

}
