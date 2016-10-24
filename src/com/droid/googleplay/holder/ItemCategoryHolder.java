/**
 * @auth yidong
 * @date 2016年10月23日下午9:15:59
 * @version v1.0
 * @desc
 *
 */

package com.droid.googleplay.holder;

import java.lang.annotation.Annotation;

import com.droid.googleplay.R;
import com.droid.googleplay.base.BaseHolder;
import com.droid.googleplay.bean.CategoryInfoBean;
import com.droid.googleplay.constant.Constants;
import com.droid.googleplay.utils.BitmapHelp;
import com.droid.googleplay.utils.StringUtils;
import com.droid.googleplay.utils.UIUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
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
		setData(data.name1,data.url1,mTvName1,mIvIcon1);
		setData(data.name2,data.url2,mTvName2,mIvIcon2);
		setData(data.name3,data.url3,mTvName3,mIvIcon3);
	}

	private void setData(String name,String url,TextView tv,ImageView iv)
	{
		if(!StringUtils.isEmpty(name)&&!StringUtils.isEmpty(url))
		{
			tv.setText(name);
			iv.setImageResource(R.drawable.ic_default);
			BitmapHelp.display(iv, Constants.URLS.IMAGEBASEURL+url);
			((ViewGroup)tv.getParent()).setVisibility(View.VISIBLE);
			
			((ViewGroup)tv.getParent()).setOnClickListener(new OnClickListener()
			{
				@Override
				public void onClick(View v)
				{
					
				}
			});
		}
		else
		{
			((ViewGroup)tv.getParent()).setVisibility(View.INVISIBLE);
		}
	}

}
