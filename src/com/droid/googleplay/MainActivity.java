package com.droid.googleplay;

import com.astuetz.PagerSlidingTabStripExtends;
import com.droid.googleplay.factory.FragmentFactory;
import com.droid.googleplay.utils.UIUtils;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * @author dongyi 使用ActionBar时Activity继承自ActionBarActivity,需要引入v7包
 *         修改主题:@style/Theme.AppComp.xxx
 */
public class MainActivity extends ActionBarActivity
{
	private ActionBar					mActionBar;
	private PagerSlidingTabStripExtends	mTabs;
	/**
	 * v4包源码关联 路径:android_sdk_home/extras/android/support/v4/src/java
	 * 操作:把libs下的v4包add to build path 注意:build path中的order and export中v4的位置
	 */
	private ViewPager					mViewPager;
	private String[]					mMainTitles;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// mActionBar = getSupportActionBar();
		// mActionBar.setTitle("GooglePlay");
		// mActionBar.setSubtitle("good app");
		// mActionBar.setIcon(R.drawable.ic_launcher);
		// mActionBar.setDisplayShowTitleEnabled(true);
		// mActionBar.setDisplayShowHomeEnabled(true);
		// mActionBar.setDisplayHomeAsUpEnabled(true);

		initView();
		initActionBar();
		initData();
	}

	/** 初始化view **/
	private void initView()
	{
		mTabs = (PagerSlidingTabStripExtends) findViewById(R.id.main_tabs);
		mViewPager = (ViewPager) findViewById(R.id.main_viewpager);
	}

	/** 初始化ActionBar **/
	private void initActionBar()
	{
		mActionBar = getSupportActionBar();
		mActionBar.setTitle("GooglePlay");
		mActionBar.setIcon(R.drawable.ic_launcher);
		mActionBar.setDisplayShowTitleEnabled(true);
		mActionBar.setDisplayShowHomeEnabled(true);
		mActionBar.setDisplayHomeAsUpEnabled(true);
	}

	/** 初始化数据 **/
	private void initData()
	{
		mMainTitles = UIUtils.getStringArray(R.array.main_title);
		
		MainFragmentAdapter adapter = new MainFragmentAdapter(getSupportFragmentManager());
		
		mViewPager.setAdapter(adapter);

		// 绑定tabs到viewpager上
		mTabs.setViewPager(mViewPager);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		int id = item.getItemId();
		if (id == R.id.action_settings)
		{
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	class MainAdapter extends PagerAdapter
	{

		@Override
		public int getCount()
		{
			if (mMainTitles != null)
			{
				return mMainTitles.length;
			}
			return 0;
		}

		@Override
		public boolean isViewFromObject(View view, Object object)
		{
			return view == object;
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position)
		{
			TextView tv = new TextView(UIUtils.getContext());
			tv.setText(mMainTitles[position]);
			container.addView(tv);
			return tv;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object)
		{
			container.removeView((View) object);
		}

		/** 必须重写此方法,否则tabs没有标签文本,会报空指针 **/

		@Override
		public CharSequence getPageTitle(int position)
		{
			return mMainTitles[position];
		}
	}

	class MainFragmentAdapter extends FragmentPagerAdapter
	{

		public MainFragmentAdapter(FragmentManager fm)
		{
			super(fm);
		}

		@Override
		public int getCount()
		{
			if(mMainTitles!=null)
			{
				return mMainTitles.length;
			}
			return 0;
		}

		@Override
		public Fragment getItem(int position)
		{
			return FragmentFactory.getFragment(position);
		}
		
		@Override
		public CharSequence getPageTitle(int position)
		{
			return mMainTitles[position];
		}

	}
}
