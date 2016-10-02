/**
 * @auth yidong
 * @date 2016年10月2日上午9:23:00
 * @version v1.0
 * @desc
 *
 */

package com.droid.googleplay.factory;

import com.droid.googleplay.utils.UIUtils;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.widget.ListView;

public class ListViewFactory
{
	public static ListView createListView()
	{
		ListView listView = new ListView(UIUtils.getContext());

		listView.setCacheColorHint(Color.TRANSPARENT);
		listView.setSelector(new ColorDrawable(Color.TRANSPARENT));
		listView.setFastScrollEnabled(true);

		return listView;
	}
}
